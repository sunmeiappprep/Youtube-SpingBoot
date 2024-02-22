package ca.myapp.service;

import ca.myapp.controllers.CommentController;
import ca.myapp.dto.CommentDto;
import ca.myapp.exception.idNotFoundException;
import ca.myapp.models.Comment;
import ca.myapp.models.User;
import ca.myapp.models.Video;
import ca.myapp.repository.CommentRepository;
import ca.myapp.repository.UserRepository;
import ca.myapp.repository.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.List;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VideoRepository videoRepository;

    public Comment createComment(Long userId,long videoId,String text) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new idNotFoundException("User not found with id: " + userId));

        Video video = videoRepository.findById(videoId)
                .orElseThrow(() -> new idNotFoundException("There is no video id"+ videoId));

        Comment comment = new Comment();
        comment.setUser(user);
        comment.setVideo(video);
        comment.setText(text);
        System.out.println(user);
        System.out.println(video);
        return commentRepository.save(comment);
    }

    public List<Comment> getComments(long videoId){
        return commentRepository.findByVideoId(videoId);
    }

    @Transactional
    public void deleteComment(Long commentId) {
        if (!commentRepository.existsById(commentId)) {
            throw new idNotFoundException("Comment not found with id: " + commentId);
        }
        commentRepository.deleteById(commentId);
    }

}
