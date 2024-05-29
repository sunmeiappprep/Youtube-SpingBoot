package ca.myapp.repository;
import ca.myapp.models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByVideoId(Long VideoId);
    List<Comment> findByVideoIdOrderByCreatedAtDesc(long videoId);

    @Modifying
    @Transactional
    @Query("DELETE FROM Comment")
    void deleteAllComments();

}
