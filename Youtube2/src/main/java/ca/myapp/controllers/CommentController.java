package ca.myapp.controllers;


import ca.myapp.dto.CommentDto;
import ca.myapp.models.Comment;
import ca.myapp.models.User;
import ca.myapp.repository.CommentRepository;
import ca.myapp.service.CommentService;
import ca.myapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@CrossOrigin
@RestController
@RequestMapping("/api/comment")
public class CommentController {

    private final CommentService commentService;
    private final UserService userService;


    @Autowired
    public CommentController(CommentService commentService, UserService userService) {
        this.commentService = commentService;
        this.userService = userService;
    }


    @PostMapping("/add")
    public ResponseEntity<?> addComment (@RequestBody CommentDto request) {
            Comment comment = commentService.createComment( request.getUserId(), request.getVideoId(),request.getText());
            return ResponseEntity.ok(comment);
    }

    @GetMapping("/get/video/{videoId}")
    public ResponseEntity<?> getComments (@PathVariable("videoId") Long videoId) {
        List<Comment> listOfComment = commentService.getComments(videoId);
        return ResponseEntity.ok(listOfComment);
    }


    @DeleteMapping("/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable Long commentId) {
            User currentUser = userService.getAuthenticatedUser();
            System.out.println(currentUser);
            commentService.deleteComment(commentId);
            return ResponseEntity.ok("Successful Comment Delete");
    }

}
//POST /comments/{videoId} to add a comment to a video.
//        GET /comments/{videoId} to list comments for a video.
//PUT /comments/{commentId} to update a comment.
//DELETE /comments/{commentId} to delete a comment.