package ca.myapp.repository;
import ca.myapp.models.VideoReaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VideoReactionRepository extends JpaRepository<VideoReaction, Long> {
    // Find a reaction by video and user to see if there a prev reaction
    Optional<VideoReaction> findByVideoIdAndUserId(Long videoId, Long userId);

    // search for the videoId and count the liked that is true
    int countByVideoIdAndLikedTrue(Long videoId);

    // search for the videoId and count the liked that is false
    int countByVideoIdAndLikedFalse(Long videoId);

}