package com.chat.media_service.config;

import com.cloudinary.Cloudinary;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CloudinaryConfig {

  @Value("${CLOUDINARY_URL}")
  private String cloudinaryUrl;

  @Bean
  public Cloudinary cloudinary() {
    Cloudinary cloudinary = new Cloudinary(cloudinaryUrl);
    cloudinary.config.secure = true;

    return cloudinary;
  }
}
