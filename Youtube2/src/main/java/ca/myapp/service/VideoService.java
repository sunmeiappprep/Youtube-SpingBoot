package ca.myapp.service;

import ca.myapp.dto.VideoRequestDto;
import ca.myapp.models.User;
import ca.myapp.models.Video;
import ca.myapp.repository.UserRepository;
import ca.myapp.repository.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


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
        System.out.println(request.getUserId());
        System.out.println(request.getTitle());
        System.out.println(request.getUrl());
        User uploader = userRepository.findById(request.getUserId())
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "User not found with id: " + request.getUserId()
                        )
                );

        Video video = new Video();
        video.setTitle(request.getTitle());
        video.setUrl(request.getUrl());
        video.setDescription(request.getDescription());
        video.setUploader(uploader);

        return videoRepository.save(video);
    }

}
