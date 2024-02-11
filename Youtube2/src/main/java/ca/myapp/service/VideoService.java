package ca.myapp.service;

import ca.myapp.models.Video;
import ca.myapp.repository.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class VideoService {
    @Autowired
    private VideoRepository videoRepository;

    public Video findById(Long id) throws Exception {
        return videoRepository.findById(id)
                .orElseThrow(() -> new Exception("There is no video with that id"));
    }

    public Video postVideo(Video video) {
        // Additional validations or business logic here
        return videoRepository.save(video);
    }

}
