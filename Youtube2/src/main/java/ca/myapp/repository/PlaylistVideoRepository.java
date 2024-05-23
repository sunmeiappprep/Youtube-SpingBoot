package ca.myapp.repository;

import ca.myapp.models.PlaylistTitle;
import ca.myapp.models.PlaylistVideo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PlaylistVideoRepository extends JpaRepository<PlaylistVideo, Long> {
    List<PlaylistVideo> findByPlaylistTitleId(Long playlistTitleId);


    boolean existsByPlaylistTitleIdAndVideoId(Long playlistTitleId, Long videoId);
    Optional<PlaylistVideo> findByPlaylistTitleIdAndVideoId(Long playlistTitleId, Long videoId);

    @Query("SELECT DISTINCT pv.playlistTitle FROM PlaylistVideo pv " +
            "JOIN pv.playlistTitle pt " +
            "WHERE pt.createdBy.id = :userId AND pv.video.id = :videoId")
    List<PlaylistTitle> findPlaylistsByUserAndVideo(@Param("userId") Long userId, @Param("videoId") Long videoId);
}