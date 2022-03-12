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

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    private final CommentMapper commentMapper;
    private final UsersMapper usersMapper;
    private final UsersRepository usersRepository;
    private final NotificationService notificationService;
    private final PostRepository postRepository;

    public List<CommentGetDto> getAllByPost(UUID postId) {
        return commentRepository.findByPost_IdOrderByCreateTimestampAsc(postId).stream()
                .map(commentMapper::fromEntity)
                .collect(Collectors.toList());
    }

    public String postComment(UUID userId, CommentPostDto commentPostDto) {
        Users users = usersRepository.findById(userId).get();
        UsersMiniGetDto usersMiniGetDto = usersMapper.entityToMiniDto(users);

        commentPostDto.setCommentUsers(usersMiniGetDto);
        commentPostDto.setVisibility(true);
        Comment comment = commentMapper.toEntity(commentPostDto);
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

    public long getCommentCountByPostId(UUID postId) {
        return commentRepository.countByPost_IdAndVisibilityIsTrue(postId);
    }
}
