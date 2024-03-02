package ca.myapp.dto;

public class AddVideoToPlaylistRequest {
    private Long playlistTitleId;
    private Long videoId;

    // Getters and Setters
    public Long getPlaylistTitleId() {
        return playlistTitleId;
    }

    public void setPlaylistTitleId(Long playlistTitleId) {
        this.playlistTitleId = playlistTitleId;
    }

    public Long getVideoId() {
        return videoId;
    }

    public void setVideoId(Long videoId) {
        this.videoId = videoId;
    }

    @Override
    public String toString() {
        return "playlistdtop{" +
                "playlistTitleId=" + playlistTitleId +
                ", videoId='" + videoId + '\'' +
                '}';
    }
}
