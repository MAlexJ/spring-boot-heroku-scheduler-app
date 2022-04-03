package com.malexj.repository;

import com.malexj.entity.ModelEventEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ModelEventRepository extends MongoRepository<ModelEventEntity, String> {

    List<ModelEventEntity> findTop20ByOrderByCreatedAsc();
}
