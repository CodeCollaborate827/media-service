package com.chat.media_service.service;

import com.chat.media_service.document.MediaResource;
import com.chat.media_service.exception.ApplicationException;
import com.chat.media_service.exception.ErrorCode;
import com.chat.media_service.utils.CloudinaryUtils;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import java.io.IOException;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class CloudinaryService {

  private final Cloudinary cloudinary;

  private final Map cloudinaryParams =
      ObjectUtils.asMap(
          "use_filename", true,
          "unique_filename", false,
          "overwrite", true);

  public Mono<MediaResource> uploadResource(Object object) {
    return Mono.defer(
        () -> {
          try {
            log.info("Uploading file to Cloudinary");
            Map uploadResponse = cloudinary.uploader().upload(object, cloudinaryParams);
            return Mono.just(CloudinaryUtils.convertCloudinaryUploadResponse(uploadResponse));
          } catch (IOException e) {
            log.error("Error when upload file to Cloudinary");
            throw new ApplicationException(ErrorCode.MEDIA_ERROR2);
          }
        });
  }
}
