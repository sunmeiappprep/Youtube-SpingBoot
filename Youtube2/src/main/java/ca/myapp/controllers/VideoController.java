package ca.myapp.controllers;
import ca.myapp.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ca.myapp.models.Video;
import ca.myapp.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

@Controller
@CrossOrigin
@RequestMapping("/api/video")
public class VideoController {

    @Autowired
    private VideoService videoService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getVideoById(@PathVariable("id") Long id) throws Exception {
        // Use the videoService to get the video by id
        Video video = videoService.findById(id);
        if (video != null) {
            return ResponseEntity.ok(video);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/")
    public ResponseEntity<Video> postVideo(@RequestBody Video video) {
        Video savedVideo = videoService.postVideo(video);
        System.out.println("asdsad");
        return new ResponseEntity<>(savedVideo, HttpStatus.CREATED);
    }
}
//POST /videos for uploading a new video.
//GET /videos to list videos.
//GET /videos/{id} to get a specific video's details.
//PUT /videos/{id} to update video details.
//DELETE /videos/{id} to delete a video.
//GET /videos/user/{userId} to list videos uploaded by a specific user.