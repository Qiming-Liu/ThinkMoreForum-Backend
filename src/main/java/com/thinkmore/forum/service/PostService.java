package com.thinkmore.forum.service;

import com.thinkmore.forum.dto.post.PostGetDto;
import com.thinkmore.forum.dto.post.PostMiniGetDto;
import com.thinkmore.forum.dto.post.PostPostDto;
import com.thinkmore.forum.dto.post.PostPutDto;
import com.thinkmore.forum.dto.users.UsersMiniGetDto;
import com.thinkmore.forum.entity.Post;
import com.thinkmore.forum.mapper.CategoryMapper;
import com.thinkmore.forum.mapper.PostMapper;
import com.thinkmore.forum.mapper.UsersMapper;
import com.thinkmore.forum.repository.CategoryRepository;
import com.thinkmore.forum.repository.PostRepository;
import com.thinkmore.forum.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    private final PostMapper postMapper;

    private final UsersMapper usersMapper;
    private final UsersRepository usersRepository;

    private final CategoryMapper categoryMapper;
    private final CategoryRepository categoryRepo;

    public List<PostGetDto> getAllPosts() {

        return postRepository.findAll().stream()
                .map(postMapper::fromEntity)
                .collect(Collectors.toList());
    }

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
    public void deletePostById(UUID postId) {
        postRepository.deleteById(postId);
    }

    public String userPostPost(UUID userId, PostPostDto postPostDto) {

        UsersMiniGetDto usersMiniGetDto = usersMapper.entityToMiniDto(usersRepository.findById(userId).get());

        postPostDto.setViewCount(0);
        postPostDto.setFollowCount(0);
        postPostDto.setCommentCount(0);
        postPostDto.setVisibility(true);
        postPostDto.setCreateTimestamp(OffsetDateTime.now());
        postPostDto.setPostUsers(usersMiniGetDto);

        Post post = postMapper.toEntity(postPostDto);
        postRepository.save(post);

        return post.getId().toString();
    }

    public String userEditPost(PostPutDto postPutDto) {

        Post oldPost = postRepository.findById(postPutDto.getId()).get();
        postMapper.copy(postPutDto, oldPost);

        return String.format("You've successfully edited the post with title %s", postPutDto.getTitle());
    }

    public List<PostGetDto> getPostsByCategoryTitle(String category_title, Pageable pageable) {
        return postRepository.findByCategory_Title(category_title, pageable).stream()
                .map(postMapper::fromEntity)
                .collect(Collectors.toList());
    }

    public long getCountOfPostsByCategoryTitle(String category_title) {
        return postRepository.countByCategory_Title(category_title);
    }

    public List<PostMiniGetDto> getAllPostsCoreInfo() {

        return postRepository.findAll().stream()
                .map(postMapper::entityToMiniDto)
                .collect(Collectors.toList());
    }
}
