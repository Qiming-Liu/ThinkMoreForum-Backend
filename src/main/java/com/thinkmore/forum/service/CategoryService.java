package com.thinkmore.forum.service;

import com.thinkmore.forum.dto.category.CategoryGetDto;
import com.thinkmore.forum.dto.category.CategoryPutDto;
import com.thinkmore.forum.entity.Category;
import com.thinkmore.forum.entity.Comment;
import com.thinkmore.forum.entity.Post;
import com.thinkmore.forum.entity.Users;
import com.thinkmore.forum.mapper.CategoryMapper;
import com.thinkmore.forum.repository.CategoryRepository;
import com.thinkmore.forum.repository.CommentRepository;
import com.thinkmore.forum.repository.PostRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    public List<CategoryGetDto> getAllCategories() {
        return categoryRepository.findByOrderBySortOrderAsc().stream().map(categoryMapper::fromEntity).collect(Collectors.toList());
    }

    public CategoryGetDto getCategoryByCategoryTitle(String category_title) throws Exception {
        Optional<Category> targetCategory = categoryRepository.findByTitle(category_title);
        CategoryGetDto targetCategoryGetDto;
        if (targetCategory.isPresent()) {
            targetCategoryGetDto = categoryMapper.fromEntity(targetCategory.get());
        } else {
            throw new Exception("Couldn't find the category with provided ID");
        }
        return targetCategoryGetDto;
    }

    @Transactional
    public Boolean putCategory(List<CategoryPutDto> categoryPutDtoList) {
        List<Category> categoryNewList = categoryPutDtoList.stream().map(categoryMapper::toEntity).collect(Collectors.toList());
        List<Category> categoryOldList = categoryRepository.findByOrderBySortOrderAsc();
        for (int i = 0; i < categoryNewList.size(); i++) {
            categoryNewList.get(i).setSortOrder(i);
        }

        List<Category> removeList = categoryOldList.stream().filter(category -> {
            for (Category categoryNew : categoryNewList) {
                if (categoryNew.getId().equals(category.getId())) {
                    return false;
                }
            }
            return true;
        }).collect(Collectors.toList());

        List<Category> addList = categoryNewList.stream().filter(category -> {
            if (category.getId() != null) {
                return false;
            }
            category.setLastUpdateTimestamp(OffsetDateTime.now());
            category.setParticipantCount(0);
            return true;
        }).collect(Collectors.toList());

        List<Category> updateList = categoryNewList.stream().filter(category -> {
            if (category.getId() == null) {
                return false;
            }
            category.setLastUpdateTimestamp(OffsetDateTime.now());
            return true;
        }).collect(Collectors.toList());

        removeList.forEach(category ->
                postRepository.findByCategory_Title(category.getTitle())
                              .forEach(post -> post.setCategory(null)));

        categoryRepository.deleteAll(removeList);
        categoryRepository.saveAll(updateList);
        categoryRepository.saveAll(addList);
        return true;
    }

    @Transactional
    public CategoryGetDto putCategoryPinPostById(UUID categoryId, UUID postId) {
        Category category = categoryRepository.findById(categoryId).get();
        category.setPinPost(postRepository.findById(postId).get());
        categoryRepository.save(category);
        return categoryMapper.fromEntity(category);
    }

    @Transactional
    public CategoryGetDto putCategoryPinPostNull(UUID categoryId) {
        Category category = categoryRepository.findById(categoryId).get();
        category.setPinPost(null);
        categoryRepository.save(category);
        return categoryMapper.fromEntity(category);
    }

    @Transactional
    public void updateParticipant(UUID categoryId) {
        Category category = categoryRepository.findById(categoryId).get();
        List<Post> postList = postRepository.findByCategory_Title(category.getTitle());
        List<Comment> commentList = postList.stream().flatMap(post -> commentRepository.findByPost_IdOrderByCreateTimestampAsc(post.getId()).stream()).collect(Collectors.toList());
        List<Users> usersList = commentList.stream().map(Comment::getCommentUsers).collect(Collectors.toList());
        usersList.addAll(postList.stream().map(Post::getPostUsers).collect(Collectors.toList()));
        HashSet<UUID> set = new HashSet<>();
        for (Users user : usersList) {
            set.add(user.getId());
        }
        category.setParticipantCount(set.size());
        category.setLastUpdateTimestamp(OffsetDateTime.now());
        categoryRepository.save(category);
    }
}
