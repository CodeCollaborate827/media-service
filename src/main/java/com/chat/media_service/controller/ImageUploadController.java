package com.chat.media_service.controller;

import com.chat.media_service.dto.response.CommonResponse;
import com.chat.media_service.service.MediaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/media")
@Slf4j
@RequiredArgsConstructor
public class ImageUploadController {

  private final MediaService mediaService;

  @PostMapping("/image")
  public Mono<ResponseEntity<CommonResponse>> uploadImage(
      @RequestHeader(required = false) String operation,
      @RequestHeader(required = false) String requestId,
      @RequestPart("image") Mono<FilePart> filePartMono) {

    return mediaService.uploadImage(operation, requestId, filePartMono);
  }
}
