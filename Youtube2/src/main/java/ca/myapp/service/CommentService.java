package ca.myapp.service;

import ca.myapp.dto.CommentDto;
import ca.myapp.dto.CommentReactionDTO;
import ca.myapp.exception.ErrorToFrontEnd;
import ca.myapp.exception.idNotFoundException;
import ca.myapp.models.Comment;
import ca.myapp.models.CommentReaction;
import ca.myapp.models.User;
import ca.myapp.models.Video;
import ca.myapp.repository.CommentReactionRepository;
import ca.myapp.repository.CommentRepository;
import ca.myapp.repository.UserRepository;
import ca.myapp.repository.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

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

    @Autowired
    private VideoService videoService;

    @Autowired
    private CommentReactionRepository commentReactionRepository;

    @Autowired
    private CommentReactionService commentReactionService;

    public Comment createComment(CommentDto request) {
        //seeding
        //first seed with 1st
//        User currentUser = userService.getRandomUser();//1st and 2nd
//        Video video = videoService.getRandomVideo();//turn on for first layer of comment, 1st
//        Comment randomParentComment = getRandomComment();//2nd
//        Video video = videoRepository.findById(randomParentComment.getVideo().getId())//2nd
//                .orElseThrow(() -> new idNotFoundException("There is no video id" + request.getVideoId()));
////
////

         User currentUser = userService.getAuthenticatedUser();//Turn on when done seeding

        Video video = videoRepository.findById(request.getVideoId())//Turn on when done seeding
                .orElseThrow(() -> new idNotFoundException("There is no video id" + request.getVideoId()));

        Comment comment = new Comment();
        comment.setUser(currentUser);
        comment.setVideo(video);
        comment.setText(request.getText());

        if (request.getParentId() != null) {//Turn on when done seeding
            Optional<Comment> parentComment = commentRepository.findById(request.getParentId());
            if (parentComment.isPresent()) {
                comment.setParent(parentComment.get());
            } else {
                throw new IllegalArgumentException("Invalid parent comment ID: " + request.getParentId());
            }
        }

//        System.out.println(randomParentComment);
//        comment.setParent(randomParentComment);




        return commentRepository.save(comment);
    }

//    public List<Comment> getComments(long videoId){
//        return commentRepository.findByVideoIdOrderByCreatedAtDesc(videoId);
//    }

    public List<CommentDto> getCommentsByVideoId(Long videoId) {
        List<Comment> comments = commentRepository.findByVideoId(videoId);
        Map<Long, CommentDto> commentDtoMap = new HashMap<>();

        // Convert comments to DTOs and put them in the map
        for (Comment comment : comments) {
            CommentDto commentDto = convertToDto(comment);
            commentDtoMap.put(comment.getId(), commentDto);
        }

        List<CommentDto> topLevelComments = new ArrayList<>();

        // Organize hierarchy
        for (CommentDto commentDto : commentDtoMap.values()) {
            if (commentDto.getParentId() != null) {
                CommentDto parentComment = commentDtoMap.get(commentDto.getParentId());
                if (parentComment != null) {
                    parentComment.getReplies().add(commentDto);
                }
            } else {
                topLevelComments.add(commentDto);
            }
        }

        return topLevelComments;
    }

    private CommentDto convertToDto(Comment comment) {
        CommentDto dto = new CommentDto();
        dto.setId(comment.getId());
        dto.setText(comment.getText());
        dto.setCreatedAt(comment.getCreatedAt());
        dto.setUser((comment.getUser()));
        dto.setVideo((comment.getVideo()));
        dto.setParentId(comment.getParent() != null ? comment.getParent().getId() : null);
        dto.setReplies(new ArrayList<>());
        return dto;
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

    public Comment getRandomComment() {
        List<Comment> comments = commentRepository.findAll();
        if (comments.isEmpty()) {
            throw new IllegalStateException("No comments found");
        }
        int randomIndex = ThreadLocalRandom.current().nextInt(comments.size());
        return comments.get(randomIndex);
    }

    @Transactional
    public void deleteAllComments() {
        commentRepository.deleteAllComments();
    }

    public List<CommentReactionDTO> getCommentReactionsByVideoId(Long videoId) {

        List<Comment> comments = commentRepository.findByVideoId(videoId);
        System.out.println("Comments retrieved: " + comments);


        List<Long> commentIds = comments.stream().map(Comment::getId).collect(Collectors.toList());
        System.out.println("Comment IDs: " + commentIds);


        List<CommentReactionDTO> commentReactions = new ArrayList<>();

        for (Long id : commentIds) {
            try {
                int liked = commentReactionService.countLikes(id);
                int disliked = commentReactionService.countDislikes(id);
                int diff = liked - disliked;


                CommentReactionDTO reactionDTO = new CommentReactionDTO(id, liked, disliked, diff);
                commentReactions.add(reactionDTO);

            } catch (Exception e) {
                System.err.println("Error in getting comment Like or Dislike for comment ID: " + id);
            }
        }

        return commentReactions;
    }

}
