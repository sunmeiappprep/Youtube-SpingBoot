package ca.myapp.controllers;
import ca.myapp.dto.ReactionRequest;
import ca.myapp.service.VideoReactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;


@CrossOrigin
@RestController
@RequestMapping("/api/videoReactions")
public class VideoReactionController {

    private final VideoReactionService videoReactionService;

    @Autowired
    public VideoReactionController(VideoReactionService videoReactionService) {
        this.videoReactionService = videoReactionService;
    }


    @PostMapping("/add")
    public ResponseEntity<?> addReaction(@RequestBody ReactionRequest request) {
        System.out.println(request);
        try {
            videoReactionService.addReaction(request.getVideoId(), request.getUserId(), request.isLiked());
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error Adding Reaction");
        }
    }

    @GetMapping("/results/{video}")
    public ResponseEntity<?> getAllLiked(@PathVariable("video") Long id) {
        //getting liked and dislike count and returning it as an Array
        try {
            int liked = videoReactionService.countLikes(id);
            int disliked = videoReactionService.countDislikes(id);

            int[] arr = {liked, disliked};
            return ResponseEntity.ok(arr);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error in getting Like or Dislike");
        }
    }

}
