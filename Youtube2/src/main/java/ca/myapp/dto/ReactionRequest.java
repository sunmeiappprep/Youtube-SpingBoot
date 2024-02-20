package ca.myapp.dto;

public class ReactionRequest {
    private Long videoId;
    private Long userId; // Have to change to some Token based later
    private boolean liked;


    public Long getVideoId() {
        return videoId;
    }

    public void setVideoId(Long videoId) {
        this.videoId = videoId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public boolean isLiked() {
        return liked;
    }

    public void setLiked(boolean liked) {
        this.liked = liked;
    }

    @Override
    public String toString() {
        return "ReactionRequest{" +
                "userId=" + userId +
                ", videoId='" + videoId + '\'' +
                ", liked='" + liked + '\'' +
                '}';
    }
}
