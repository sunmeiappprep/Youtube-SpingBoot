package ca.myapp.models;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

@Entity
@Table(name = "video")
public class Video {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = true)
    private String description;

    @Column(nullable = true)
    private Long view;

    @Column(nullable = false)
    private String url;

    @Column(name = "generated_date", nullable = true)
    private LocalDate generatedDate;


    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User uploader;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public Long getView() {
        return view;
    }

    public void setView(Long view) {
        this.view = view;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setUploader(User uploader) {
        this.uploader = uploader;
    }

    public Long getUserId() {
        return uploader != null ? uploader.getId() : null;
    }

    public LocalDate getGeneratedDate() {
        return generatedDate;
    }

    public void setGeneratedDate(LocalDate generatedDate) {
        this.generatedDate = generatedDate;
    }


    public Video() {
        // Generate a random number of views each time a Video object is created
        // For example, generating a random number between 0 and 10000
        this.view = ThreadLocalRandom.current().nextLong(0, 10001);
        long minDay = LocalDate.now().minusYears(2).toEpochDay();
        long maxDay = LocalDate.now().minusDays(1).toEpochDay();
        long randomDay = ThreadLocalRandom.current().nextLong(minDay, maxDay);
        this.generatedDate = LocalDate.ofEpochDay(randomDay);
    }


    @Override
    public String toString() {
        return "Video{" +
                "uploader=" + uploader +
                ", title='" + title + '\'' +
                ", url='" + url + '\'' +
                ", view='" + view + '\'' +
                ", description='" + description + '\'' +

                '}';
    }

}
