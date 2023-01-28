package com.thinkmore.forum.service;

import com.thinkmore.forum.dto.comment.CommentGetDto;
import com.thinkmore.forum.dto.post.*;
import com.thinkmore.forum.entity.Category;
import com.thinkmore.forum.entity.Comment;
import com.thinkmore.forum.entity.Post;
import com.thinkmore.forum.entity.Users;
import com.thinkmore.forum.exception.UserNotFoundException;
import com.thinkmore.forum.mapper.CommentMapper;
import com.thinkmore.forum.mapper.PostMapper;
import com.thinkmore.forum.repository.CategoryRepository;
import com.thinkmore.forum.repository.CommentRepository;
import com.thinkmore.forum.repository.PostRepository;
import com.thinkmore.forum.repository.UsersRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final PostMapper postMapper;
    private final UsersRepository usersRepository;
    private final CategoryRepository categoryRepository;
    private final CategoryService categoryService;
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;

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

    public PostCommentGetDto getMaxCountCommentPost(Pageable pageable) {
        List<Post> posts = postRepository.findByOrderByCommentCountDesc(pageable);

        if (posts.size() < 3) {
            return null;
        }

        Random random = new Random();
        int i = random.nextInt(3);

        List<Comment> comments = commentRepository.findByPost_IdOrderByCreateTimestampAsc(posts.get(i).getId());
        PostCommentGetDto postCommentGetDto = new PostCommentGetDto();
        List<CommentGetDto> commentGetDtoList = comments.stream().map(commentMapper::fromEntity).collect(Collectors.toList());

        postCommentGetDto.setComments(commentGetDtoList);
        postCommentGetDto.setPost(postMapper.fromEntity(posts.get(i)));
        return postCommentGetDto;
    }

    @Transactional
    public String postPost
            (UUID
                     userId, PostPostDto
                     postPostDto) {

        Post post = postMapper.toEntity(postPostDto);
        post.setPostUsers(usersRepository.getById(userId));
        post.setCategory(categoryRepository.findByTitle(postPostDto.getCategoryTitle()).get());

        postRepository.save(post);

        Category categoryToUpdate = categoryRepository.findByTitle(post.getCategory().getTitle()).get();
        int newPostCount = (int) postRepository.countByCategory_IdAndVisibilityIsTrue(categoryToUpdate.getId());
        categoryToUpdate.setPostCount(newPostCount);
        categoryRepository.save(categoryToUpdate);

        categoryService.updateParticipant(post.getCategory().getId());

        return post.getId().toString();
    }

    @Transactional
    public boolean editPost (UUID postId, UUID userId, PostMiniPutDto postMiniPutDto) {
        Post currentPost = postRepository.findById(postId).get();
        Users requestUser = usersRepository.findById(userId).get();
        if (!requestUser.getRole().getRoleName().equals("admin")) {
            if (!currentPost.getPostUsers().getId().equals(userId)) {
                return false;
            }
        }

        currentPost.setHeadImgUrl(postMiniPutDto.getHeadImgUrl());
        currentPost.setTitle(postMiniPutDto.getTitle());
        currentPost.setContext(postMiniPutDto.getContext());
        postRepository.save(currentPost);

        Category categoryToUpdate = categoryRepository.findById(currentPost.getCategory().getId()).get();
        int newPostCount = (int) postRepository.countByCategory_IdAndVisibilityIsTrue(categoryToUpdate.getId());
        categoryToUpdate.setPostCount(newPostCount);
        categoryRepository.save(categoryToUpdate);

        return true;
    }

    public List<PostMiniGetDto> getAllPostsCoreInfo
            () {
        return postRepository.findAll().stream()
                             .map(postMapper::entityToMiniDto)
                             .collect(Collectors.toList());
    }

    public List<PostGetDto> getPostByTitleContainingString
            (String
                     string) {
        return postRepository.findByTitleContainingIgnoreCase(string).stream()
                             .map(postMapper::fromEntity)
                             .collect(Collectors.toList());
    }

    public List<PostGetDto> getPostsByPostUsersName
            (String
                     username) {
        Users user = usersRepository.findByUsername(username)
                                    .orElseThrow(() -> new UserNotFoundException("Invalid UserName"));
        return postRepository.findByPostUsersId(user.getId()).stream()
                             .map(postMapper::fromEntity)
                             .collect(Collectors.toList());
    }

    public long getCountOfVisiblePostsByCategoryId
            (UUID
                     categoryId) {
        return postRepository.countByCategory_IdAndVisibilityIsTrue(categoryId);
    }

    public List<PostGetDto> getVisiblePostsByCategoryId
            (UUID
                     categoryId, Pageable
                     pageable) {
        return postRepository.findByCategory_IdAndVisibilityIsTrue(categoryId, pageable).stream()
                             .map(postMapper::fromEntity)
                             .collect(Collectors.toList());
    }

    @Transactional
    public Boolean changePostVisibility
            (UUID
                     postId, UUID
                     userId) {
        Post oldPost = postRepository.findById(postId).get();
        Users requestInitiator = usersRepository.findById(userId).get();
        if (!requestInitiator.getRole().getRoleName().equals("admin")) {
            if (!oldPost.getPostUsers().getId().equals(userId)) {
                return false;
            }
        }

        oldPost.setVisibility(!oldPost.getVisibility());
        postRepository.save(oldPost);

        Category categoryToUpdate = categoryRepository.findById(oldPost.getCategory().getId()).get();
        int newPostCount = (int) postRepository.countByCategory_IdAndVisibilityIsTrue(categoryToUpdate.getId());
        categoryToUpdate.setPostCount(newPostCount);
        categoryRepository.save(categoryToUpdate);

        return true;
    }

    @Transactional
    public void updateViewCount
            (UUID
                     postId) {
        Post post = postRepository.findById(postId).get();
        post.setViewCount(post.getViewCount() + 1);
        postRepository.save(post);

        Category category = categoryRepository.findById(post.getCategory().getId()).get();
        List<Post> posts = postRepository.findByCategory_IdAndVisibilityIsTrue(category.getId());
        category.setViewCount(posts.stream().mapToInt(Post::getViewCount).sum());
        categoryRepository.save(category);
    }
}
