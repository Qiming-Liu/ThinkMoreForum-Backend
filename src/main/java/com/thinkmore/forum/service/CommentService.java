package com.thinkmore.forum.service;

import com.thinkmore.forum.dto.comment.CommentGetDto;
import com.thinkmore.forum.dto.comment.CommentPostDto;
import com.thinkmore.forum.entity.Comment;
import com.thinkmore.forum.entity.Post;
import com.thinkmore.forum.entity.Users;
import com.thinkmore.forum.mapper.CommentMapper;
import com.thinkmore.forum.repository.CommentRepository;
import com.thinkmore.forum.repository.PostRepository;
import com.thinkmore.forum.repository.UsersRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final UsersRepository usersRepository;
    private final PostRepository postRepository;

    private final CommentMapper commentMapper;
    private final NotificationService notificationService;
    private final CategoryService categoryService;

    public List<CommentGetDto> getAllByPost(UUID postId) {
        return commentRepository.findByPost_IdOrderByCreateTimestampAsc(postId).stream()
                                .map(commentMapper::fromEntity)
                                .collect(Collectors.toList());
    }

    @Transactional
    public String postComment(UUID userId, CommentPostDto commentPostDto) {
        Users users = usersRepository.findById(userId).get();
        Post post = postRepository.getById(commentPostDto.getPost().getId());
        Comment comment = commentMapper.toEntity(commentPostDto);
        comment.setCommentUsers(users);
        comment.setPost(post);
        if (commentPostDto.getParentComment() != null) {
            Comment parentComment = commentRepository.getById(commentPostDto.getParentComment().getId());
            comment.setParentComment(parentComment);
        }
        commentRepository.save(comment);

        String context;
        Users notifyUser;
        if (comment.getMentionUsers() != null) {
            notifyUser = comment.getMentionUsers();
            context = comment.getCommentUsers().getUsername() + " mentioned you in a comment";
        } else if (comment.getParentComment() == null) {
            notifyUser = comment.getPost().getPostUsers();
            context = " replied your post.";
        } else {
            notifyUser = comment.getParentComment().getCommentUsers();
            context = " replied your comment.";
        }

        notificationService.postNotification(users, notifyUser, context);

        Post postToUpdate = postRepository.findById(commentPostDto.getPost().getId()).get();
        int newCommentCount = (int) commentRepository.countByPost_IdAndVisibilityIsTrue(postToUpdate.getId());
        postToUpdate.setCommentCount(newCommentCount);
        postRepository.save(postToUpdate);

        categoryService.updateParticipant(post.getCategory().getId());

        return "You've successfully replied the post!";
    }
}
