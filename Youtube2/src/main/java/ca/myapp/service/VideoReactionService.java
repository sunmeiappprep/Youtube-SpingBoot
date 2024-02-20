package ca.myapp.service;
import ca.myapp.models.User;
import ca.myapp.models.Video;
import ca.myapp.models.VideoReaction;
import ca.myapp.repository.UserRepository;
import ca.myapp.repository.VideoReactionRepository;
import ca.myapp.repository.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class VideoReactionService {
    //create a different Model,Controller,Service,Rep because of separation of concerns
    private final VideoReactionRepository videoReactionRepository;
    private final VideoRepository videoRepository;
    private final UserRepository userRepository;

    @Autowired
    public VideoReactionService(VideoReactionRepository videoReactionRepository, VideoRepository videoRepository, UserRepository userRepository) {
        this.videoReactionRepository = videoReactionRepository;
        this.videoRepository = videoRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public void addReaction(Long videoId, Long userId, boolean liked) {
        //Find Video and User obj
        Video video = videoRepository.findById(videoId)
                .orElseThrow(() -> new RuntimeException("Video not found with id: " + videoId));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        //Find if reaction exist by matching video and user id
        Optional<VideoReaction> existingReaction = videoReactionRepository.findByVideoIdAndUserId(videoId, userId);
        if (existingReaction.isPresent()) {
            //If it exists and it the same one then delete it
            //If it exists but not the same as incoming liked then swap it
            VideoReaction reaction = existingReaction.get();
            if (reaction.getLiked().equals(liked)) {
                videoReactionRepository.delete(reaction);
            }
            else if (reaction.getLiked() != liked) {
                reaction.setLiked(liked);
                videoReactionRepository.save(reaction);
            }
        } else {
            // if it dont exist then make a new one
            VideoReaction newReaction = new VideoReaction();
            //bound to video obj
            newReaction.setVideo(video);
            //bound to user obj
            newReaction.setUser(user);
            // set to true or false
            newReaction.setLiked(liked);
            videoReactionRepository.save(newReaction);
        }
    }

    public int countLikes(Long videoId) {
        return videoReactionRepository.countByVideoIdAndLikedTrue(videoId);
    }

    public int countDislikes(Long videoId) {
        return videoReactionRepository.countByVideoIdAndLikedFalse(videoId);
    }

}