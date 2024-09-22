package com.chat.media_service.utils;

import com.chat.media_service.constrants.CloudinaryConstants;
import com.chat.media_service.document.MediaResource;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

public class CloudinaryUtils {

    private static final DateTimeFormatter DATETIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");

    public static MediaResource convertCloudinaryUploadResponse(Map responseMap) {


        return MediaResource.builder()
                .id((String) responseMap.get(CloudinaryConstants.PUBLIC_ID))
                .fileName((String) responseMap.get(CloudinaryConstants.ORIGINAL_FILENAME))
                .bytes((int) responseMap.get(CloudinaryConstants.BYTES))
                .format((String) responseMap.get(CloudinaryConstants.FORMAT))
                .resourceType((String) responseMap.get(CloudinaryConstants.RESOURCE_TYPE))
                .url((String) responseMap.get(CloudinaryConstants.URL))
                .secureUrl((String) responseMap.get(CloudinaryConstants.SECURE_URL))
                .createdAt(parseDateTime((String) responseMap.get(CloudinaryConstants.CREATED_AT)))
                .version((int) responseMap.get(CloudinaryConstants.VERSION))
                .accessMode((String) responseMap.get(CloudinaryConstants.ACCESS_MODE))
                .build();
    }

    private static LocalDateTime parseDateTime(String dateTime) {
        return LocalDateTime.parse(dateTime, DATETIME_FORMATTER);
    }
}
