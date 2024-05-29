package ca.myapp.controllers;
import ca.myapp.dto.VideoRequestDto;
import ca.myapp.dto.VideoWithUserDTO;
import ca.myapp.models.User;
import ca.myapp.repository.UserRepository;
import ca.myapp.service.UserService;
import ca.myapp.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ca.myapp.models.Video;
import org.springframework.http.ResponseEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/api/video")
public class VideoController {
    private static final Logger logger = LoggerFactory.getLogger(VideoController.class);
    @Autowired
    private final VideoService videoService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    public VideoController(VideoService videoService) {
        this.videoService = videoService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getVideoById(@PathVariable("id") Long id) throws Exception {
        Video video = videoService.findById(id);
        if (video != null) {
            VideoWithUserDTO videoWithUserDTO = new VideoWithUserDTO(video, video.getUploader());
            return ResponseEntity.ok(videoWithUserDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/")
    public ResponseEntity<Video> createVideo(@RequestBody VideoRequestDto videoRequestDto) {
        logger.info("Received video creation request: {}", videoRequestDto.toString());

        Video video = videoService.createVideo(videoRequestDto);
        return new ResponseEntity<>(video, HttpStatus.CREATED);
    }

    @PutMapping("/{videoId}")
    public ResponseEntity<Video> updateVideo(@PathVariable Long videoId, @RequestBody Video videoDetails) {
        Video updatedVideo = videoService.updateVideo(videoId, videoDetails);
        return ResponseEntity.ok(updatedVideo);
    }

    @GetMapping("/videos")
    public ResponseEntity<List<VideoWithUserDTO>> getVideos(@RequestParam String seed, @RequestParam int page) {
        // Use the seed and page to fetch videos in a consistent pseudo-random order
        List<Video> videos = videoService.getVideos(seed, page, 40);

        // Map videos to VideoWithUserDTO
        List<VideoWithUserDTO> videoWithUserDTOs = videos.stream()
                .map(video -> new VideoWithUserDTO(video, video.getUploader()))
                .collect(Collectors.toList());

        return ResponseEntity.ok(videoWithUserDTOs);
    }

    @GetMapping("/subscribed")
    public List<Video> getSubscribedVideos(@RequestParam Long subscriberId) {
        return videoService.getSubscribedVideos(subscriberId);
    }


    @DeleteMapping("/{videoId}")
    public ResponseEntity<?> deleteVideo(@PathVariable Long videoId) {
        videoService.deleteVideo(videoId);
        return ResponseEntity.ok().build();
    }

}
//POST /videos for uploading a new video.
//GET /videos to list videos.
//GET /videos/{id} to get a specific video's details.
//PUT /videos/{id} to update video details.
//DELETE /videos/{id} to delete a video.
//GET /videos/user/{userId} to list videos uploaded by a specific user.