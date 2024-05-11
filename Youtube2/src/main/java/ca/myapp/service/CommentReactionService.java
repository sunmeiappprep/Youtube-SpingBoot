package ca.myapp.service;
import ca.myapp.models.Comment;
import ca.myapp.models.CommentReaction;
import ca.myapp.models.User;
import ca.myapp.repository.CommentReactionRepository;
import ca.myapp.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class CommentReactionService {
    private final CommentReactionRepository commentReactionRepository;
    private final CommentRepository commentRepository;
    private final UserService userService;

    @Autowired
    public CommentReactionService(CommentReactionRepository commentReactionRepository, CommentRepository commentRepository, UserService userService) {
        this.commentReactionRepository = commentReactionRepository;
        this.commentRepository = commentRepository;
        this.userService = userService;
    }

    @Transactional
    public void addReaction(Long commentId, boolean liked) {
        User currentUser = userService.getAuthenticatedUser();

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found with id: " + commentId));

        Optional<CommentReaction> existingReaction = commentReactionRepository.findByCommentIdAndUserId(commentId, currentUser.getId());
        if (existingReaction.isPresent()) {
            CommentReaction reaction = existingReaction.get();
            if (reaction.getLiked().equals(liked)) {
                commentReactionRepository.delete(reaction);
            } else if (reaction.getLiked() != liked) {
                reaction.setLiked(liked);
                commentReactionRepository.save(reaction);
            }
        } else {
            CommentReaction newReaction = new CommentReaction();
            newReaction.setComment(comment);
            newReaction.setUser(currentUser);
            newReaction.setLiked(liked);
            commentReactionRepository.save(newReaction);
        }
    }

    public int countLikes(Long commentId) {
        return commentReactionRepository.countByCommentIdAndLikedTrue(commentId);
    }

    public int countDislikes(Long commentId) {
        return commentReactionRepository.countByCommentIdAndLikedFalse(commentId);
    }
}
