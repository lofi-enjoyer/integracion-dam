package me.lofienjoyer.quiet.media.service;

import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.security.core.Authentication;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ImageService {

    Mono<String> uploadProfileImage(Authentication authentication, Mono<FilePart> multipartFile);

    Flux<DataBuffer> loadProfileImage(String username);

}
