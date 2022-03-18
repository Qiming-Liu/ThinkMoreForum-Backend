package com.thinkmore.forum.service;

import com.thinkmore.forum.dto.post.PostGetDto;
import com.thinkmore.forum.dto.post.PostMiniGetDto;
import com.thinkmore.forum.dto.post.PostPostDto;
import com.thinkmore.forum.entity.Category;
import com.thinkmore.forum.entity.Post;
import com.thinkmore.forum.entity.Users;
import com.thinkmore.forum.exception.UserNotFoundException;
import com.thinkmore.forum.mapper.PostMapper;
import com.thinkmore.forum.repository.CategoryRepository;
import com.thinkmore.forum.repository.PostRepository;
import com.thinkmore.forum.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final PostMapper postMapper;
    private final UsersRepository usersRepository;
    private final CategoryRepository categoryRepository;

    @Transactional
    public PostGetDto getPostById(UUID postId) throws Exception {
        Optional<Post> targetPost = postRepository.findById(postId);
        PostGetDto targetPostGetDto;
        if (targetPost.isPresent()) {
            targetPostGetDto = postMapper.fromEntity(targetPost.get());
        } else {
            throw new Exception("Couldn't find the post with provided ID");
        }
        return targetPostGetDto;
    }

    @Transactional
    public String postPost(UUID userId, PostPostDto postPostDto) {

        Post post = postMapper.toEntity(postPostDto);
        post.setPostUsers(usersRepository.getById(userId));
        post.setCategory(categoryRepository.findByTitle(postPostDto.getCategoryTitle()).get());

        postRepository.save(post);

        Category categoryToUpdate = categoryRepository.findByTitle(post.getCategory().getTitle()).get();
        int newPostCount = (int) postRepository.countByCategory_IdAndVisibilityIsTrue(categoryToUpdate.getId());
        categoryToUpdate.setPostCount(newPostCount);
        categoryRepository.save(categoryToUpdate);

        return post.getId().toString();
    }

    @Transactional
    public List<PostGetDto> getPostsByCategoryTitle(String category_title, Pageable pageable) {
        return postRepository.findByCategory_Title(category_title, pageable).stream()
                .map(postMapper::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional
    public long getCountOfPostsByCategoryTitle(String category_title) {
        return postRepository.countByCategory_Title(category_title);
    }

    @Transactional
    public List<PostMiniGetDto> getAllPostsCoreInfo() {
        return postRepository.findAll().stream()
                .map(postMapper::entityToMiniDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public List<PostGetDto> getPostsByPostUsersName(String username) {
        Users user = usersRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("Invalid UserName"));
        return postRepository.findByPostUsersId(user.getId()).stream()
                .map(postMapper::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional
    public long getCountOfVisiblePostsByCategoryId(UUID categoryId) {
        return postRepository.countByCategory_IdAndVisibilityIsTrue(categoryId);
    }

    @Transactional
    public List<PostGetDto> getVisiblePostsByCategoryId(UUID categoryId, Pageable pageable) {
        return postRepository.findByCategory_IdAndVisibilityIsTrue(categoryId, pageable).stream()
                .map(postMapper::fromEntity)
                .collect(Collectors.toList());
    }

    public Boolean changePostVisibility(UUID postId, UUID userId) {
        Post oldPost = postRepository.findById(postId).get();
        if (oldPost.getPostUsers().getId().equals(userId)) {
            return false;
        }
        oldPost.setVisibility(!oldPost.getVisibility());
        postRepository.save(oldPost);

        Category categoryToUpdate = categoryRepository.findById(oldPost.getCategory().getId()).get();
        int newPostCount = (int) postRepository.countByCategory_IdAndVisibilityIsTrue(categoryToUpdate.getId());
        categoryToUpdate.setPostCount(newPostCount);
        categoryRepository.save(categoryToUpdate);

        return true;
    }

    public void updateViewCount(UUID postId) {
        Post oldPost = postRepository.findById(postId).get();
        int newViewCount = oldPost.getViewCount()+1;
        oldPost.setViewCount(newViewCount);
        postRepository.save(oldPost);
    }
}
