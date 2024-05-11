package ca.myapp.service;
import ca.myapp.models.Video;
import ca.myapp.repository.VideoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchService {

    private final VideoRepository videoRepository;

    public SearchService(VideoRepository videoRepository) {
        this.videoRepository = videoRepository;
    }

    public List<Video> findByTitle(String title) throws Exception {
        return videoRepository.findByTitleContainingIgnoreCase(title);
    }
}
