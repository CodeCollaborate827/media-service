package com.chat.media_service.controller;

import com.chat.media_service.dto.response.CommonResponse;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/media")
@Slf4j
public class ImageUploadController {

    @Value("${CLOUDINARY_URL}")
    private String cloudinaryUrl;


    @PostMapping("/image")
    public Mono<ResponseEntity<CommonResponse>> uploadImage(@RequestHeader String userId,
                                                            @RequestHeader String requestId,
                                                            @RequestPart("image") Mono<FilePart> filePartMono
    ) {
        return filePartMono.flatMap(filePart -> {
            //TODO: clean the code
            //TODO: setup size limit
            log.info("userId :{}", userId);
            log.info("requestId :{}", requestId);
            log.info("file name: {}", filePart.filename());


            return filePart.content().reduce(DataBuffer::write).map(dataBuffer -> {
                        byte[] bytes = new byte[dataBuffer.readableByteCount()];
                        dataBuffer.read(bytes);

                        // Release the DataBuffer to prevent memory leaks
                        DataBufferUtils.release(dataBuffer);
                        return bytes;
                    })
                    .map(bytes -> {
                        log.info("bytes: {}", bytes.length);

                        Map<String, Object> data = Map.of(
                                "fileName", filePart.filename(),
                                "fileSize", bytes.length
                        );
                        Cloudinary cloudinary = new Cloudinary(cloudinaryUrl);
                        cloudinary.config.secure = true;
                        System.out.println(cloudinary.config.cloudName);

                        // Upload the image
                        Map params1 = ObjectUtils.asMap(
                                "use_filename", true,
                                "unique_filename", false,
                                "overwrite", true
                        );
                        CommonResponse response = CommonResponse.builder()
                                .message("Image uploaded successfully")
                                .build();


//                create a file from the bytes
                        String random = UUID.randomUUID().toString();
                        File file = new File(random + filePart.filename());

                        // set the bytes to the file
                        try {
                            Files.write(file.toPath(), bytes);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }

                        return file;

                    })
                    .flatMap(file -> {
                        return Mono.defer(() -> {
                            Cloudinary cloudinary = new Cloudinary(cloudinaryUrl);
                            cloudinary.config.secure = true;

                            // Upload the image
                            Map params1 = ObjectUtils.asMap(
                                    "use_filename", true,
                                    "unique_filename", false,
                                    "overwrite", true
                            );

                            try {
                                Map uploadResult = cloudinary.uploader().upload(file, params1);
                                return Mono.just(uploadResult);

                            } catch (IOException e) {
                                e.printStackTrace();

                                return Mono.error(e);
                            }

                        });

                    })
                    .map(uploadResult -> {
                        log.info("upload result: {}", uploadResult);
                        return ResponseEntity.ok().body(CommonResponse.builder().message("Image uploaded successfully")
                                .data(uploadResult)
                                .build());
                    });



        });


    }

}
