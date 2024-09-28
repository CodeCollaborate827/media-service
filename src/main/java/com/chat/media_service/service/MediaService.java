package com.chat.media_service.service;

import com.chat.media_service.dto.response.CommonResponse;
import com.chat.media_service.repository.MediaResourceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class MediaService {

  private final MediaResourceRepository mediaResourceRepository;
  private final CloudinaryService cloudinaryService;

  public Mono<ResponseEntity<CommonResponse>> uploadImage(
      String operation, String requestId, Mono<FilePart> filePart) {

    return readBytesFromFile(filePart)
        .flatMap(cloudinaryService::uploadResource)
        .doOnNext(mediaResource -> mediaResource.setOperation(operation))
        .flatMap(mediaResourceRepository::save)
        .map(
            uploadResult -> {
              log.info("upload result: {}", uploadResult);
              CommonResponse imageUploadedSuccessfully =
                  CommonResponse.builder()
                      .message("Image uploaded successfully")
                      .requestId(requestId)
                      .data(uploadResult)
                      .build();
              return ResponseEntity.ok().body(imageUploadedSuccessfully);
            });
  }


  private Mono<byte[]> readBytesFromFile(Mono<FilePart> filePartMono) {
    return filePartMono.flatMap(
        filePart -> {
          Mono<DataBuffer> reduce = filePart.content().reduce(DataBuffer::write);
          return reduce.map(
              dataBuffer -> {
                byte[] bytes = new byte[dataBuffer.readableByteCount()];
                dataBuffer.read(bytes);
                // Release the DataBuffer to prevent memory leaks
                DataBufferUtils.release(dataBuffer);
                return bytes;
              });
        });
  }
}
