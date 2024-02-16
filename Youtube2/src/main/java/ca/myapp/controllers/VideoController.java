package ca.myapp.controllers;
import ca.myapp.dto.VideoRequestDto;
import ca.myapp.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ca.myapp.models.Video;
import ca.myapp.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

@CrossOrigin
@RestController
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

//    @Override
//    public String toString() {
//        return "Video{" +
//                "id=" + id +
//                ", title='" + title + '\'' +
//                ", description='" + description + '\'' +
//                ", url='" + url + '\'' +
//                ", uploader=" + (uploader != null ? uploader.getId() : "null") + // Assuming 'uploader' has an 'id' field
//                '}';
//    }



    @PostMapping("/")
    public ResponseEntity<Video> createVideo(@RequestBody VideoRequestDto videoRequestDto) {
        Video video = videoService.createVideo(videoRequestDto);
        return new ResponseEntity<>(video, HttpStatus.CREATED);
    }
}
//POST /videos for uploading a new video.
//GET /videos to list videos.
//GET /videos/{id} to get a specific video's details.
//PUT /videos/{id} to update video details.
//DELETE /videos/{id} to delete a video.
//GET /videos/user/{userId} to list videos uploaded by a specific user.