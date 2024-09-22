package com.chat.media_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@SpringBootApplication
@EnableReactiveMongoRepositories
public class MediaServiceApplication {

  public static void main(String[] args) {
    SpringApplication.run(MediaServiceApplication.class, args);
  }
}
