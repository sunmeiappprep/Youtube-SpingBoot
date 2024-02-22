package ca.myapp.dto;

public class CommentDto {
    private Long videoId;
    private Long userId; // Have to change to some Token based later
    private String text;


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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "CommentDto{" +
                "userId=" + userId +
                ", videoId='" + videoId + '\'' +
                ", liked='" + text + '\'' +
                '}';
    }
}
