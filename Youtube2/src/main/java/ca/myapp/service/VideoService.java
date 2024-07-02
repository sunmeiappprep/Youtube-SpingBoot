package ca.myapp.service;

import ca.myapp.dto.VideoRequestDto;
import ca.myapp.exception.idNotFoundException;
import ca.myapp.models.Comment;
import ca.myapp.models.User;
import ca.myapp.models.Video;
import ca.myapp.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;

import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;


@Service
public class VideoService {

    private final SubscriptionRepository subscriptionRepository;
    private final CommentRepository commentRepository;
    private final CommentReactionRepository commentReactionRepository;
    private final VideoRepository videoRepository;
    private final UserService userService;

    private final PlaylistVideoRepository playlistVideoRepository;

    @Autowired
    public VideoService(SubscriptionRepository subscriptionRepository,
                        CommentRepository commentRepository,
                        CommentReactionRepository commentReactionRepository,
                        VideoRepository videoRepository,
                        UserService userService,
                        PlaylistVideoRepository playlistVideoRepository) {
        this.subscriptionRepository = subscriptionRepository;
        this.commentRepository = commentRepository;
        this.commentReactionRepository = commentReactionRepository;
        this.videoRepository = videoRepository;
        this.userService = userService;
        this.playlistVideoRepository = playlistVideoRepository;
    }
    public Video findById(Long id) throws Exception {
        return videoRepository.findById(id)
                .orElseThrow(() -> new Exception("There is no video with that id"));
    }

    public Video createVideo(VideoRequestDto request) throws Exception {
        // Use the DTO to break down the incoming body and you can use override toString.
        // Passed the user id to userRep to find the user obj and map that to the uploader
        User currentUser = userService.getAuthenticatedUser();
//        User currentUser = userService.findUserByUsername(request.getUsername());

        // Create a Video obj name video and set the params
        Video video = new Video();
        video.setTitle(request.getTitle());
        video.setUrl(request.getUrl());
        video.setDescription(request.getDescription());
        video.setUploader(currentUser);
//        Random random = new Random();
//        long randomViews = 1000L + (long)(random.nextDouble() * (999999L - 1000L));
        video.setView(request.getView());
        // Generate a random date within the past 8 years
        LocalDate now = LocalDate.now();
        LocalDate startDate = now.minusYears(8);
        long daysBetween = ChronoUnit.DAYS.between(startDate, now);
        LocalDate randomDate = startDate.plusDays(new Random().nextInt((int) daysBetween + 1));

        video.setGeneratedDate(randomDate);



        return videoRepository.save(video);
    }

    public Video seedVideo(VideoRequestDto request) throws Exception {
        // Use the DTO to break down the incoming body and you can use override toString.
        // Passed the user id to userRep to find the user obj and map that to the uploader
        // User currentUser = userService.getAuthenticatedUser();
        User currentUser = userService.findUserByUsername(request.getUsername());

        // Create a Video obj name video and set the params
        Video video = new Video();
        video.setTitle(request.getTitle());
        video.setUrl(request.getUrl());
        video.setDescription(request.getDescription());
        video.setUploader(currentUser);

//        Random random = new Random();
//        long randomViews = 1000L + (long)(random.nextDouble() * (999999L - 1000L));
        video.setView(request.getView());
        // Generate a random date within the past 8 years
        LocalDate now = LocalDate.now();
        LocalDate startDate = now.minusYears(8);
        long daysBetween = ChronoUnit.DAYS.between(startDate, now);
        LocalDate randomDate = startDate.plusDays(new Random().nextInt((int) daysBetween + 1));

        video.setGeneratedDate(randomDate);



        return videoRepository.save(video);
    }


    public List<Video> getVideosByUploaderId(Long uploader) {
        return videoRepository.findByUploaderId(uploader);
    }

    public Video updateVideo(Long videoId, Video videoDetails) {
        Optional<Video> videoOptional = videoRepository.findById(videoId);
        if (videoOptional.isPresent()) {
            Video existingVideo = videoOptional.get();
            // Update the existing video with new details.
            existingVideo.setTitle(videoDetails.getTitle());
            existingVideo.setDescription(videoDetails.getDescription());
            existingVideo.setUrl(videoDetails.getUrl());
            return videoRepository.save(existingVideo); // Save and return the updated video
        } else {
            throw new RuntimeException("Video not found with id " + videoId); // Handle this exception appropriately
        }
    }
    public List<Video> getVideos(String seed, int page, int size) {
        List<Video> videos = videoRepository.findAll(); // Fetch all videos (simplified for example)

        // Shuffle videos based on the seed
        long seedLong = Long.parseLong(seed); // Convert seed to long. Ensure this is safe and valid.
        Collections.shuffle(videos, new Random(seedLong));

        // Calculate pagination
        int start = page * size;
        int end = Math.min((page + 1) * size, videos.size());

        if (start > videos.size()) start = videos.size();
        return videos.subList(start, end); // Return a page of videos
    }

    @Transactional
    public void deleteVideo(Long videoId) {
        if (!videoRepository.existsById(videoId)) {
            throw new idNotFoundException("Video not found with id: " + videoId);
        }

        List<Comment> comments = commentRepository.findByVideoId(videoId);

        for (Comment comment : comments) {
            commentReactionRepository.deleteByCommentId(comment.getId());
        }

        commentRepository.deleteByVideoId(videoId);

        playlistVideoRepository.deleteByVideoId(videoId);

        videoRepository.deleteById(videoId);

    }

    public Video getRandomVideo() {
        List<Video> videos = videoRepository.findAll();
        int randomIndex = ThreadLocalRandom.current().nextInt(videos.size());
        return videos.get(randomIndex);
    }

    public List<Video> getSubscribedVideos(Long subscriberId) {
        List<Long> channelIds = subscriptionRepository.findChannelIdsBySubscriberId(subscriberId);
        return videoRepository.findByUploaderIds(channelIds);
    }
}
