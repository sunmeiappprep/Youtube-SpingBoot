package ca.myapp.repository;
import ca.myapp.models.CommentReaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentReactionRepository extends JpaRepository<CommentReaction, Long> {
    // Find a reaction by comment and user to see if there a previous reaction
    Optional<CommentReaction> findByCommentIdAndUserId(Long commentId, Long userId);

    // Search for the commentId and count the liked that is true
    int countByCommentIdAndLikedTrue(Long commentId);

    // Search for the commentId and count the liked that is false
    int countByCommentIdAndLikedFalse(Long commentId);

    List<CommentReaction> findAllByCommentId(Long commentId);
    @Transactional
    void deleteByCommentId(Long commentId);
    List<CommentReaction> findByCommentIdIn(List<Long> commentIds);
}
