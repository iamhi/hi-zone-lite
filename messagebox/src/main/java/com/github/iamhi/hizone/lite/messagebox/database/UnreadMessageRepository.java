package com.github.iamhi.hizone.lite.messagebox.database;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UnreadMessageRepository extends CrudRepository<UnreadMessageEntity, Integer> {

    Optional<UnreadMessageEntity> findByUuid(String uuid);
}
