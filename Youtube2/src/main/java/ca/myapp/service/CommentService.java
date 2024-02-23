package ca.myapp.service;

import ca.myapp.exception.ErrorToFrontEnd;
import ca.myapp.exception.idNotFoundException;
import ca.myapp.models.Comment;
import ca.myapp.models.User;
import ca.myapp.models.Video;
import ca.myapp.repository.CommentRepository;
import ca.myapp.repository.UserRepository;
import ca.myapp.repository.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VideoRepository videoRepository;

    @Autowired
    private UserService userService;

    public Comment createComment(Long userId,long videoId,String text) {
        User currentUser = userService.getAuthenticatedUser();

        Video video = videoRepository.findById(videoId)
                .orElseThrow(() -> new idNotFoundException("There is no video id"+ videoId));

        Comment comment = new Comment();
        comment.setUser(currentUser);
        comment.setVideo(video);
        comment.setText(text);
        System.out.println(currentUser);
        System.out.println(video);
        return commentRepository.save(comment);
    }

    public List<Comment> getComments(long videoId){
        return commentRepository.findByVideoId(videoId);
    }

    public void updateComment (Long commentId, String newComment){
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new idNotFoundException("Comment not found with id: " + commentId));
        User currentUser = userService.getAuthenticatedUser();
        if (!currentUser.getId().equals(comment.getUser().getId())){
            throw new ErrorToFrontEnd("User does not have permission to delete this comment");
        }
        System.out.println(newComment);
        comment.setText(newComment);
        commentRepository.save(comment);
    }
    @Transactional
    public void deleteComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new idNotFoundException("Comment not found with id: " + commentId));
        User currentUser = userService.getAuthenticatedUser();
        System.out.println(comment.getUser().getId()+(currentUser.getId()));
        if (!comment.getUser().getId().equals(currentUser.getId())) {
            throw new ErrorToFrontEnd("User does not have permission to delete this comment");
        }
        commentRepository.deleteById(commentId);
    }

}
