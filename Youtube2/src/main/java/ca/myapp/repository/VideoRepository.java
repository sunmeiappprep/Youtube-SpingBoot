package ca.myapp.repository;
import ca.myapp.models.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VideoRepository extends JpaRepository<Video, Long> {

    //returns a List of Video obj that belongs to the uploaderId
    List<Video> findByUploaderId(Long uploaderId);

    List<Video> findByTitle(String title);

    List<Video> findByTitleContainingIgnoreCase(String title);

    @Query("SELECT v FROM Video v WHERE v.uploader.id IN :uploaderIds")
    List<Video> findByUploaderIds(@Param("uploaderIds") List<Long> uploaderIds);
}