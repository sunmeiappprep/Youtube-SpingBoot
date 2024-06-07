package ca.myapp.controllers;
import ca.myapp.dto.ReactionRequest;
import ca.myapp.service.CommentReactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

@CrossOrigin
@RestController
@RequestMapping("/api/commentReactions")
public class CommentReactionController {

    private final CommentReactionService commentReactionService;

    @Autowired
    public CommentReactionController(CommentReactionService commentReactionService) {
        this.commentReactionService = commentReactionService;
    }

    @PostMapping("/add")
    public ResponseEntity<?> addReaction(@RequestBody ReactionRequest request) {
        System.out.println(request);
        try {
            commentReactionService.addReaction(request.getCommentId(), request.isLiked());
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error Adding Comment Reaction");
        }
    }

    @GetMapping("/results/{comment}")
    public ResponseEntity<?> getAllLiked(@PathVariable("comment") Long id) {
        //getting liked and dislike count and returning it as a number cause there is no negative comments on youtube
        try {
            int liked = commentReactionService.countLikes(id);
            int disliked = commentReactionService.countDislikes(id);
            int diff = liked-disliked;

            if (0 > diff){
                diff = 0;
            }

            return ResponseEntity.ok(diff);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error in getting  comment Like or Dislike");
        }
    }


}
