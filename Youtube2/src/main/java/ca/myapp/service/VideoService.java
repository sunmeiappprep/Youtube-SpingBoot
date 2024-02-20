package ca.myapp.service;

import ca.myapp.dto.VideoRequestDto;
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
    @Autowired
    private VideoRepository videoRepository;

    @Autowired
    private UserRepository userRepository;



    public Video findById(Long id) throws Exception {
        return videoRepository.findById(id)
                .orElseThrow(() -> new Exception("There is no video with that id"));
    }

    public Video createVideo(VideoRequestDto request) {

        User uploader = userRepository.findById(request.getUserId())
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "User not found with id: " + request.getUserId()
                        )
                );
        System.out.println(uploader);

        Video video = new Video();
        video.setTitle(request.getTitle());
        video.setUrl(request.getUrl());
        video.setDescription(request.getDescription());
        video.setUploader(uploader);
        System.out.println(uploader);

        return videoRepository.save(video);
    }

    public List<Video> getVideosByUploaderId(Long uploader) {
        return videoRepository.findByUploaderId(uploader);
    }

    public Video updateVideo(Long videoId, Video videoDetails) {
        Optional<Video> videoOptional = videoRepository.findById(videoId);
        if (videoOptional.isPresent()) {
            Video existingVideo = videoOptional.get();
            // Update the existing video with new details. Adjust according to your Video class structure
            existingVideo.setTitle(videoDetails.getTitle());
            existingVideo.setDescription(videoDetails.getDescription());
            existingVideo.setUrl(videoDetails.getUrl());
            // Add more fields to update as needed

            return videoRepository.save(existingVideo); // Save and return the updated video
        } else {
            throw new RuntimeException("Video not found with id " + videoId); // Handle this exception appropriately
        }
    }

    @Transactional
    public void deleteVideo(Long videoId) {
        // Optional check to see if the video exists before deletion
        if (!videoRepository.existsById(videoId)) {
            throw new RuntimeException("Video not found with id: " + videoId);
        }
        videoRepository.deleteById(videoId);
    }
}
