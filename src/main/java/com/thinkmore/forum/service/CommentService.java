package com.thinkmore.forum.service;

import com.thinkmore.forum.dto.comment.CommentGetDto;
import com.thinkmore.forum.dto.comment.CommentPostDto;
import com.thinkmore.forum.dto.users.UsersMiniGetDto;
import com.thinkmore.forum.entity.Comment;
import com.thinkmore.forum.entity.Post;
import com.thinkmore.forum.entity.Users;
import com.thinkmore.forum.mapper.CommentMapper;
import com.thinkmore.forum.mapper.UsersMapper;
import com.thinkmore.forum.repository.CommentRepository;
import com.thinkmore.forum.repository.PostRepository;
import com.thinkmore.forum.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    private final CommentMapper commentMapper;
    private final UsersRepository usersRepository;
    private final NotificationService notificationService;
    private final PostRepository postRepository;

    @Transactional
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
        if (comment.getParentComment() == null) {
            notifyUser = comment.getPost().getPostUsers();
            context = " replied your post.";
        } else {
            notifyUser = comment.getParentComment().getCommentUsers();
            context = " replied your comment.";
        }

        notificationService.postNotification(notifyUser, users, context);

        Post postToUpdate = postRepository.findById(commentPostDto.getPost().getId()).get();
        int newCommentCount = (int) commentRepository.countByPost_IdAndVisibilityIsTrue(postToUpdate.getId());
        postToUpdate.setCommentCount(newCommentCount);
        postRepository.save(postToUpdate);

        return "You've successfully replied the post!";
    }
}
