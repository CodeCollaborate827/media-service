package com.chat.media_service.repository;

import com.chat.media_service.document.MediaResource;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MediaResourceRepository extends ReactiveMongoRepository<MediaResource, String> {
}
