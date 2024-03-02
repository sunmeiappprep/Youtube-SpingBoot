package ca.myapp.repository;

import ca.myapp.models.PlaylistVideo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaylistVideoRepository extends JpaRepository<PlaylistVideo, Long> {

}