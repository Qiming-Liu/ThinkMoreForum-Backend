package com.thinkmore.forum.service;

import com.thinkmore.forum.dto.post.PostGetDto;
import com.thinkmore.forum.dto.postComment.PostCommentGetDto;
import com.thinkmore.forum.dto.postComment.PostCommentPostDto;
import com.thinkmore.forum.dto.users.UsersMiniGetDto;
import com.thinkmore.forum.entity.Post;
import com.thinkmore.forum.entity.PostComment;
import com.thinkmore.forum.mapper.NotificationMapper;
import com.thinkmore.forum.mapper.PostCommentMapper;
import com.thinkmore.forum.mapper.UsersMapper;
import com.thinkmore.forum.repository.NotificationRepository;
import com.thinkmore.forum.repository.PostCommentRepository;
import com.thinkmore.forum.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostCommentService {

    private final PostCommentRepository postCommentRepo;

    private final PostCommentMapper postCommentMapper;
    private final UsersMapper usersMapper;
    private final UsersRepository usersRepo;

    public PostCommentGetDto getPostCommentById(UUID postCommentId) throws Exception {
        Optional<PostComment> targetComment = postCommentRepo.findById(postCommentId);
        PostCommentGetDto targetCommentGetDto;
        if (targetComment.isPresent()) {
            targetCommentGetDto = postCommentMapper.fromEntity(targetComment.get());
        } else {
            throw new Exception("Couldn't find the comment with provided ID");
        }
        return targetCommentGetDto;
    }

    public List<PostCommentGetDto> getAllByPost(UUID postId) {
        return postCommentRepo.findByPost_IdOrderByCreateTimestampAsc(postId).stream()
                .map(postCommentMapper::fromEntity)
                .collect(Collectors.toList());
    }

    public String userPostComment(UUID userId, PostCommentPostDto commentPostDto) {

        UsersMiniGetDto usersMiniGetDto = usersMapper.entityToMiniDto(usersRepo.findById(userId).get());

        commentPostDto.setPostUsers(usersMiniGetDto);
        commentPostDto.setVisibility(true);
        commentPostDto.setCreateTimestamp(OffsetDateTime.now());

        PostComment postComment = postCommentMapper.toEntity(commentPostDto);
        postCommentRepo.save(postComment);

        return String.format("You've successfully replied the post!");
    }

    @Transactional
    public void deleteCommentById(UUID commentId) {
        postCommentRepo.deleteById(commentId);
    }
}
