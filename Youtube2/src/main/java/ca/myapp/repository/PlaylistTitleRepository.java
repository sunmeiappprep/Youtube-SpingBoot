package ca.myapp.repository;

import ca.myapp.dto.PlaylistTitleAndVideoDTO;
import ca.myapp.models.PlaylistTitle;
import ca.myapp.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PlaylistTitleRepository extends JpaRepository<PlaylistTitle, Long> {
    List<PlaylistTitle> findByCreatedBy_Id(Long userId);

//    @Query("SELECT pt.title, v.title " +
//            "FROM PlaylistTitle pt " +
//            "JOIN PlaylistVideo pv ON pt.id = pv.playlistTitle.id " +
//            "JOIN Video v ON pv.video.id = v.id " +
//            "WHERE pt.createdBy = :user")
//    List<Object[]> findPlaylistTitlesAndVideoTitlesByUser(@Param("user") User user);

    @Query("SELECT pt, v " +
            "FROM PlaylistTitle pt " +
            "JOIN PlaylistVideo pv ON pt.id = pv.playlistTitle.id " +
            "JOIN Video v ON pv.video.id = v.id " +
            "WHERE pt.createdBy = :user")
    List<Object[]> findPlaylistTitlesAndVideosByUser(@Param("user") User user);


    boolean existsByCreatedBy_IdAndTitle(Long userId, String title);

    Optional<PlaylistTitle> findByCreatedBy_IdAndTitle(Long userId, String title);
}
