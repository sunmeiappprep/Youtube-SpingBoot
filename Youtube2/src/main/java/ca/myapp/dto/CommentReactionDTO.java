package ca.myapp.dto;

public class CommentReactionDTO {
    private Long commentId;
    private int likes;
    private int dislikes;
    private int diff;

    public CommentReactionDTO(Long commentId, int likes, int dislikes, int diff) {
        this.commentId = commentId;
        this.likes = likes;
        this.dislikes = dislikes;
        this.diff = diff;
    }

    // Getters and Setters
    public Long getCommentId() {
        return commentId;
    }

    public void setCommentId(Long commentId) {
        this.commentId = commentId;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getDislikes() {
        return dislikes;
    }

    public void setDislikes(int dislikes) {
        this.dislikes = dislikes;
    }

    public int getDiff() {
        return diff;
    }

    public void setDiff(int diff) {
        this.diff = diff;
    }
}
