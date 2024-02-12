package com.github.iamhi.hizone.lite.messagebox.database;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReadMessageRepository extends CrudRepository<ReadMessageEntity, Integer> {

    Optional<ReadMessageEntity> findByUuid(String uuid);
}
