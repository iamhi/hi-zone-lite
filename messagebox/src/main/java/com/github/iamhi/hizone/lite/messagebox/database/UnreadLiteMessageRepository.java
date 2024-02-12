package com.github.iamhi.hizone.lite.messagebox.database;

import org.springframework.stereotype.Repository;

@Repository
public interface UnreadLiteMessageRepository extends LiteMessageRepository<UnreadLiteMessageEntity, Integer> {
}
