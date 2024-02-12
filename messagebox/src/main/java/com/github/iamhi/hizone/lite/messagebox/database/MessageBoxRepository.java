package com.github.iamhi.hizone.lite.messagebox.database;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MessageBoxRepository extends CrudRepository<MessageBoxEntity, Integer> {

    Optional<MessageBoxEntity> findByOwnerUuid(String ownerUuid);

    Optional<MessageBoxEntity> findByUuid(String uuid);
}
