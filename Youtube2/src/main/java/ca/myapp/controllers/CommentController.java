package ca.myapp.controllers;


import ca.myapp.dto.CommentDto;
import ca.myapp.dto.CommentReactionDTO;
import ca.myapp.models.Comment;
import ca.myapp.models.CommentReaction;
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
    public ResponseEntity<?> addComment(@RequestBody CommentDto request) throws Exception {
        Comment comment = commentService.createComment(request);
        return ResponseEntity.ok(comment);
    }

    @PostMapping("/seed")
    public ResponseEntity<?> seedComment(@RequestBody CommentDto request) throws Exception {
        Comment comment = commentService.seedComment(request);
        return ResponseEntity.ok(comment);
    }



    @GetMapping("/get/video/{videoId}")
    public List<CommentDto> getComments (@PathVariable("videoId") Long videoId) {
        return commentService.getCommentsByVideoId(videoId);
    }


    @DeleteMapping("/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable Long commentId) {
            commentService.deleteComment(commentId);
            return ResponseEntity.ok("Successful Comment Delete");
    }

    @PutMapping("/edit/{commentId}")
    public ResponseEntity<?> updateComment(@PathVariable("commentId") Long commentId,@RequestBody CommentDto request){
        System.out.println("edit comment");
        System.out.println(request.getText());
        commentService.updateComment(commentId, request.getText());
        return ResponseEntity.ok("Successful Updated Comment");
    }

    @DeleteMapping("/deleteAll")
    public void deleteAllComments() {
        commentService.deleteAllComments();
    }

    @GetMapping("/videos/{videoId}/comments/reactions")
    public List<CommentReactionDTO> getCommentReactionsByVideoId(@PathVariable Long videoId) {
        return commentService.getCommentReactionsByVideoId(videoId);
    }

}
//POST /comments/{videoId} to add a comment to a video.
//        GET /comments/{videoId} to list comments for a video.
//PUT /comments/{commentId} to update a comment.
//DELETE /comments/{commentId} to delete a comment.