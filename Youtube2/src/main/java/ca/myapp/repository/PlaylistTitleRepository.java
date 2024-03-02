package ca.myapp.repository;

import ca.myapp.models.PlaylistTitle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaylistTitleRepository extends JpaRepository<PlaylistTitle, Long> {

}
