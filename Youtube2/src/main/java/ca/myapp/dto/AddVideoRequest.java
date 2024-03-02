package ca.myapp.dto;

public class AddVideoRequest {
    private Long playlistId;
    private Long videoId;

    // Getters and setters
    public Long getPlaylistId() {
        return playlistId;
    }

    public void setPlaylistId(Long playlistId) {
        this.playlistId = playlistId;
    }

    public Long getVideoId() {
        return videoId;
    }

    public void setVideoId(Long videoId) {
        this.videoId = videoId;
    }
    @Override
    public String toString() {
        return "Add to video{" +
                "playlistid=" + playlistId +
                ", videoId='" + videoId + '\'' +
                '}';
    }
}
