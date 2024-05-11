package ca.myapp.repository;

import ca.myapp.models.PlaylistVideo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PlaylistVideoRepository extends JpaRepository<PlaylistVideo, Long> {
    List<PlaylistVideo> findByPlaylistTitleId(Long playlistTitleId);
    boolean existsByPlaylistTitleIdAndVideoId(Long playlistTitleId, Long videoId);

    Optional<PlaylistVideo> findByPlaylistTitleIdAndVideoId(Long playlistTitleId, Long videoId);

}