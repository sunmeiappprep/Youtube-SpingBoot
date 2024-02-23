package ca.myapp.service;

import ca.myapp.dto.VideoRequestDto;
import ca.myapp.exception.idNotFoundException;
import ca.myapp.models.User;
import ca.myapp.models.Video;
import ca.myapp.repository.UserRepository;
import ca.myapp.repository.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;


@Service
public class VideoService {
    private final VideoRepository videoRepository;
    private final UserService userService;
    @Autowired // This annotation is optional if there's only one constructor
    public VideoService(VideoRepository videoRepository, UserService userService) {
        this.videoRepository = videoRepository;
        this.userService = userService;
    }
    public Video findById(Long id) throws Exception {
        //return a video obj
        return videoRepository.findById(id)
                .orElseThrow(() -> new Exception("There is no video with that id"));
    }

    public Video createVideo(VideoRequestDto request) {
        //use the DTO to break down the incoming body and you can use override toString.
        // passed the user id to userRep to find the user obj and map that to the uploader
        User currentUser = userService.getAuthenticatedUser();

        System.out.println(currentUser);

        //create a Video obj name video and set the params
        Video video = new Video();
        video.setTitle(request.getTitle());
        video.setUrl(request.getUrl());
        video.setDescription(request.getDescription());
        video.setUploader(currentUser);

        return videoRepository.save(video);
    }

    public List<Video> getVideosByUploaderId(Long uploader) {
        return videoRepository.findByUploaderId(uploader);
    }

    public Video updateVideo(Long videoId, Video videoDetails) {
        Optional<Video> videoOptional = videoRepository.findById(videoId);
        if (videoOptional.isPresent()) {
            Video existingVideo = videoOptional.get();
            // Update the existing video with new details.
            existingVideo.setTitle(videoDetails.getTitle());
            existingVideo.setDescription(videoDetails.getDescription());
            existingVideo.setUrl(videoDetails.getUrl());
            return videoRepository.save(existingVideo); // Save and return the updated video
        } else {
            throw new RuntimeException("Video not found with id " + videoId); // Handle this exception appropriately
        }
    }

    @Transactional
    public void deleteVideo(Long videoId) {
        if (!videoRepository.existsById(videoId)) {
            throw new idNotFoundException("Video not found with id: " + videoId);
        }
        videoRepository.deleteById(videoId);
    }
}
