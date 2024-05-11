

package ca.myapp.controllers;
import ca.myapp.models.Video;
import ca.myapp.service.SearchService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/search")
public class SearchController {
    private final SearchService searchService;


    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping("/{SearchTerm}")
    public ResponseEntity<?> searchVideosByTitle(@PathVariable("SearchTerm") String SearchTerm) throws Exception {
        List<Video> videos = searchService.findByTitle(SearchTerm);
        if (!videos.isEmpty()) {
            return ResponseEntity.ok(videos);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
//GET /search/videos for searching videos by title, description, or tags.
//GET /search/users for searching users/channels.
//GET /search/comments for searching within comments.