package com.thinkmore.forum.controller.v1;

import com.thinkmore.forum.dto.comment.CommentGetDto;
import com.thinkmore.forum.dto.category.CategoryGetDto;
import com.thinkmore.forum.dto.component.ComponentGetDto;
import com.thinkmore.forum.dto.followPost.FollowPostGetDto;
import com.thinkmore.forum.dto.followerUsers.FollowerUsersGetDto;
import com.thinkmore.forum.dto.post.PostCommentGetDto;
import com.thinkmore.forum.dto.post.PostGetDto;
import com.thinkmore.forum.dto.post.PostMiniGetDto;
import com.thinkmore.forum.dto.users.UsersGetDto;
import com.thinkmore.forum.dto.users.UsersPostDto;
import com.thinkmore.forum.service.*;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Metrics;
import io.prometheus.client.Histogram;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/v1/public")
@RequiredArgsConstructor
@Slf4j
public class PublicController {

    private final UsersService usersService;
    private final MessageService messageService;
    private final CategoryService categoryService;
    private final PostService postService;
    private final CommentService commentService;
    private final FollowPostService followPostService;
    private final FollowerUsersService followerUsersService;
    private final ComponentService componentService;
    private static final Counter newUserCounter = Metrics.
            counter("newUser.counter.total", "controller", "public");
    private static final Histogram postRequestLatency = Histogram.build()
            .name("post_requests_latency_seconds").help("Post request latency in seconds.").register();

    // Users
    @PostMapping(path = "/users/register")
    public ResponseEntity<Boolean> register(@RequestBody UsersPostDto usersPostDto) {
        newUserCounter.increment(1D);
        return ResponseEntity.ok(usersService.register(usersPostDto));
    }

    @GetMapping(path = "/users/unique_email/{email}")
    public ResponseEntity<Boolean> uniqueEmail(@PathVariable String email) {
        return ResponseEntity.ok(usersService.uniqueEmail(email));
    }

    @GetMapping(path = "/users/unique_username/{username}")
    public ResponseEntity<Boolean> uniqueUsername(@PathVariable String username) {
        return ResponseEntity.ok(usersService.uniqueUsername(username));
    }

    @GetMapping("/users/reset_password/{email}")
    public ResponseEntity<Boolean> sendResetPasswordEmail(@PathVariable String email) {
        return ResponseEntity.ok(messageService.sendResetPasswordEmail(email));
    }

    @GetMapping(path = "/users/username/{username}")
    public ResponseEntity<UsersGetDto> getUserById(@PathVariable String username) {
        return ResponseEntity.ok(usersService.getUserByUsername(username));
    }

    // Category
    @GetMapping(path = "/category")
    public ResponseEntity<List<CategoryGetDto>> getAllCategories() {
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    @GetMapping(path = "/category/{category_title}")
    public ResponseEntity<CategoryGetDto> getCategoryByTitle(@PathVariable("category_title") String category_title) throws Exception {
        return ResponseEntity.ok(categoryService.getCategoryByCategoryTitle(category_title));
    }

    @GetMapping(path = "/category/{category_id}/visible_post")
    public ResponseEntity<List<PostGetDto>> findVisiblePostsByCategoryId(@PathVariable("category_id") String category_id, @PageableDefault(page = 0, size = 10, sort = {"createTimestamp"}, direction = Sort.Direction.DESC) Pageable pageable) throws Exception {
        UUID categoryId = UUID.fromString(category_id);
        return ResponseEntity.ok(postService.getVisiblePostsByCategoryId(categoryId, pageable));
    }

    @GetMapping(path = "/category/{category_id}/visible_count")
    public ResponseEntity<Long> findNumOfVisiblePostsInCategory(@PathVariable("category_id") String category_id) {
        UUID categoryId = UUID.fromString(category_id);
        return ResponseEntity.ok(postService.getCountOfVisiblePostsByCategoryId(categoryId));
    }

    // Post
    @GetMapping(path = "/post")
    public ResponseEntity<List<PostMiniGetDto>> findAllPosts(){
        Histogram.Timer postRequestTimer = postRequestLatency.startTimer();
        List <PostMiniGetDto> result = postService.getAllPostsCoreInfo();
        postRequestTimer.observeDuration();
        return ResponseEntity.ok(result);
    }

    @GetMapping(path = "/post/{post_id}")
    public ResponseEntity<PostGetDto> getPostById(@PathVariable String post_id) throws Exception {
        UUID postId = UUID.fromString(post_id);
        return ResponseEntity.ok(postService.getPostById(postId));
    }

    @GetMapping(path = "/post/user/{username}")
    public ResponseEntity<List<PostGetDto>> findPostByPostUsersId(@PathVariable String username) {
        return ResponseEntity.ok(postService.getPostsByPostUsersName(username));
    }

    @PutMapping(path = "/post/{post_id}/view_count")
    public void updatePostViewCount(@PathVariable("post_id") String post_id) {
        UUID postId = UUID.fromString(post_id);
        postService.updateViewCount(postId);
    }

    @GetMapping(path = "/post/follows/find_all_by_username/{username}")
    public ResponseEntity<List<FollowPostGetDto>> getFollowPostByUsername(@PathVariable String username) {
        return ResponseEntity.ok(followPostService.getAllFollowPostsByUsername(username));
    }

    @GetMapping(path="/post/max_count_comment")
    public ResponseEntity<PostCommentGetDto> getMaxCountCommentPost(@PageableDefault(page = 0, size = 3) Pageable pageable) {
        return ResponseEntity.ok(postService.getMaxCountCommentPost(pageable));
    }

    // Comment
    @GetMapping(path = "/comment/{post_id}")
    public ResponseEntity<List<CommentGetDto>> getCommentsByPostId(@PathVariable String post_id) {
        UUID postId = UUID.fromString(post_id);
        return ResponseEntity.ok(commentService.getAllByPost(postId));
    }

    // Followers, Following
    @GetMapping(path = "/follower/{username}")
    public ResponseEntity<List<FollowerUsersGetDto>> getFollower(@PathVariable("username") String target_username) {
        return ResponseEntity.ok(followerUsersService.getFollowersByUsername(target_username));
    }

    @GetMapping(path = "/following/{username}")
    public ResponseEntity<List<FollowerUsersGetDto>> getFollowing(@PathVariable("username") String target_username) {
        return ResponseEntity.ok(followerUsersService.getFollowingByUsername(target_username));
    }

    // Component
    @GetMapping(path = "/component/{name}")
    public ResponseEntity<ComponentGetDto> getComponentByName(@PathVariable String name) throws Exception {
        return ResponseEntity.ok(componentService.getComponent(name));
    }
}
