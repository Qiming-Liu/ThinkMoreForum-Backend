package com.thinkmore.forum.service;

import com.thinkmore.forum.dto.Comment.CommentGetDto;
import com.thinkmore.forum.dto.Comment.CommentPostDto;
import com.thinkmore.forum.dto.Comment.CommentPutDto;
import com.thinkmore.forum.dto.users.UsersMiniGetDto;
import com.thinkmore.forum.entity.Comment;
import com.thinkmore.forum.mapper.CommentMapper;
import com.thinkmore.forum.mapper.UsersMapper;
import com.thinkmore.forum.repository.CommentRepository;
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
public class CommentService {

    private final CommentRepository commentRepository;

    private final CommentMapper commentMapper;
    private final UsersMapper usersMapper;
    private final UsersRepository usersRepo;

    public CommentGetDto getPostCommentById(UUID postCommentId) throws Exception {
        Optional<Comment> targetComment = commentRepository.findById(postCommentId);
        CommentGetDto targetCommentGetDto;
        if (targetComment.isPresent()) {
            targetCommentGetDto = commentMapper.fromEntity(targetComment.get());
        } else {
            throw new Exception("Couldn't find the comment with provided ID");
        }
        return targetCommentGetDto;
    }

    public List<CommentGetDto> getAllByPost(UUID postId) {
        return commentRepository.findByPost_IdOrderByCreateTimestampAsc(postId).stream()
                .map(commentMapper::fromEntity)
                .collect(Collectors.toList());
    }

    public String userPostComment(UUID userId, CommentPostDto commentPostDto) {

        UsersMiniGetDto usersMiniGetDto = usersMapper.entityToMiniDto(usersRepo.findById(userId).orElseThrow());

        commentPostDto.setPostUsers(usersMiniGetDto);
        commentPostDto.setVisibility(true);
        commentPostDto.setCreateTimestamp(OffsetDateTime.now());

        Comment comment = commentMapper.toEntity(commentPostDto);
        commentRepository.save(comment);

        return "You've successfully replied the post!";
    }

    @Transactional
    public void deleteCommentById(UUID commentId) {
        commentRepository.deleteById(commentId);
    }

    public String userEditComment(CommentPutDto commentPutDto) {

        Comment oldComment = commentRepository.findById(commentPutDto.getId()).get();
        commentMapper.copy(commentPutDto, oldComment);

        return "You've successfully edited the comment!";
    }
}
