package com.github.iamhi.hizone.lite.messagebox.core;

import com.github.iamhi.hizone.lite.authentication.domain.MemberCache;
import com.github.iamhi.hizone.lite.messagebox.core.dtos.MessageBoxDto;
import com.github.iamhi.hizone.lite.messagebox.database.MessageBoxEntity;
import com.github.iamhi.hizone.lite.messagebox.database.MessageBoxRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public record BoxServiceImpl(
    MessageBoxRepository messageBoxRepository,
    MemberCache memberCache
) implements BoxService {

    @Override
    public MessageBoxDto getOrCreateCurrentUserBox() {
        String ownerUuid = memberCache.getUserUuid();

        return this.fromEntity(messageBoxRepository.findByOwnerUuid(ownerUuid)
            .orElseGet(() -> {
                MessageBoxEntity messageBoxEntity = generateMessageBox();

                messageBoxEntity.setOwnerUuid(ownerUuid);

                return messageBoxRepository.save(messageBoxEntity);
            }));
    }

    @Override
    public Optional<MessageBoxDto> getMessageBox(String uuid) {
        return messageBoxRepository.findByUuid(uuid).map(this::fromEntity);
    }

    private MessageBoxEntity generateMessageBox() {
        MessageBoxEntity messageBoxEntity = new MessageBoxEntity();

        messageBoxEntity.setUuid(UUID.randomUUID().toString());
        messageBoxEntity.setCreatedAt(Instant.now());

        return messageBoxEntity;
    }

    private MessageBoxDto fromEntity(MessageBoxEntity entity) {
        return new MessageBoxDto(
            entity.getUuid(),
            entity.getOwnerUuid(),
            entity.getCreatedAt()
        );
    }
}
