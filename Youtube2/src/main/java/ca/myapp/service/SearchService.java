package ca.myapp.service;
import ca.myapp.dto.VideoRequestDto;
import ca.myapp.models.Video;
import ca.myapp.repository.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SearchService {

    private final VideoRepository videoRepository;

    @Autowired
    public SearchService(VideoRepository videoRepository) {
        this.videoRepository = videoRepository;
    }

    public List<VideoRequestDto> findByTitle(String title) throws Exception {
        List<Video> videos = videoRepository.findByTitleContainingIgnoreCase(title);
        return videos.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private VideoRequestDto convertToDto(Video video) {
        VideoRequestDto dto = new VideoRequestDto();

        dto.setUserId(video.getUploader().getId());
        dto.setVideoId(video.getId());
        dto.setTitle(video.getTitle());
        dto.setUrl(video.getUrl());
        dto.setUsername(video.getUploader().getUsername());
        dto.setDescription(video.getDescription());
        dto.setView(video.getView());
        dto.setGeneratedDate(video.getGeneratedDate());
        return dto;
    }
}
