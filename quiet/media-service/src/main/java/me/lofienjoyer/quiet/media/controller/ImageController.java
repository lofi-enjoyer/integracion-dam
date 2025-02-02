package me.lofienjoyer.quiet.media.controller;

import lombok.RequiredArgsConstructor;
import me.lofienjoyer.quiet.media.service.ImageService;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Handles image-related requests
 */
@RestController
@RequestMapping("/api/media")
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;

    /**
     * Handles a profile image upload request
     * @param file File uploaded
     * @param authentication User authentication
     * @return Filename of the image uploaded
     */
    @PostMapping(value = "/uploadprofile")
    public Mono<String> uploadProfileImage(@RequestPart("file") Mono<FilePart> file, Authentication authentication) {
        return imageService.uploadProfileImage(authentication, file);
    }

    /**
     * Handles a profile image get request
     * @param username Profile username
     * @return Profile image
     */
    @GetMapping("/profile/{username}")
    public ResponseEntity<Flux<DataBuffer>> getProfileImage(@PathVariable("username") String username) {
        Flux<DataBuffer> data = imageService.loadProfileImage(username);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, "image/png")
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + username + ".png\"")
                .body(data);
    }

}
