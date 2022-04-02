package com.malexj.repository;

import com.malexj.entity.ModelEventEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ModelEventRepository extends MongoRepository<ModelEventEntity, String> {
}
