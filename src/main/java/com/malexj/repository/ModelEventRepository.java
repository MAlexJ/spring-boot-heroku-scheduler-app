package com.malexj.repository;

import com.malexj.model.entity.ModelEventEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ModelEventRepository extends MongoRepository<ModelEventEntity, String> {

    Page<ModelEventEntity> findAll(Pageable pageable);
}
