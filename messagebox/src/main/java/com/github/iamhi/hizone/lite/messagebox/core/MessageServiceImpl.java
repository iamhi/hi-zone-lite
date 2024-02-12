package com.github.iamhi.hizone.lite.messagebox.core;

import com.github.iamhi.hizone.lite.authentication.domain.MemberCache;
import com.github.iamhi.hizone.lite.messagebox.core.dtos.FullMessageDto;
import com.github.iamhi.hizone.lite.messagebox.core.dtos.LiteMessageDto;
import com.github.iamhi.hizone.lite.messagebox.core.dtos.MessageBoxDto;
import com.github.iamhi.hizone.lite.messagebox.database.ReadLiteMessageEntity;
import com.github.iamhi.hizone.lite.messagebox.database.ReadLiteMessageRepository;
import com.github.iamhi.hizone.lite.messagebox.database.ReadMessageEntity;
import com.github.iamhi.hizone.lite.messagebox.database.ReadMessageRepository;
import com.github.iamhi.hizone.lite.messagebox.database.UnreadLiteMessageEntity;
import com.github.iamhi.hizone.lite.messagebox.database.UnreadLiteMessageRepository;
import com.github.iamhi.hizone.lite.messagebox.database.UnreadMessageEntity;
import com.github.iamhi.hizone.lite.messagebox.database.UnreadMessageRepository;
import io.micrometer.common.util.StringUtils;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
record MessageServiceImpl(
    ReadLiteMessageRepository readLiteMessageRepository,
    UnreadLiteMessageRepository unreadLiteMessageRepository,
    ReadMessageRepository readMessageRepository,
    UnreadMessageRepository unreadMessageRepository,
    MemberCache memberCache,
    BoxService boxService
) implements MessageService {

    @Override
    public Optional<FullMessageDto> messageDetails(String uuid) {
        return readMessageRepository.findByUuid(uuid)
            .map(this::fromReadMessageEntity)
            .or(() -> unreadMessageRepository.findByUuid(uuid)
                .map(this::fromUnreadMessageEntity));
    }

    @Override
    public Optional<FullMessageDto> readMessage(String uuid) {
        Optional<ReadMessageEntity> existingReadMessage = readMessageRepository.findByUuid(uuid);

        return existingReadMessage.map(this::fromReadMessageEntity)
            .or(() -> unreadMessageRepository.findByUuid(uuid)
                .map(unreadMessageEntity -> {
                    ReadMessageEntity readMessageEntity = generateReadEntity(unreadMessageEntity);

                    ReadMessageEntity savedReadMessageEntity = readMessageRepository.save(readMessageEntity);

                    unreadMessageRepository.delete(unreadMessageEntity);

                    return fromReadMessageEntity(savedReadMessageEntity);
                }));
    }

    @Override
    public FullMessageDto sendMessage(String box, String message) {
        MessageBoxDto currentBox = boxService.getOrCreateCurrentUserBox();
        String destinationBox = StringUtils.isBlank(box) ? currentBox.uuid() : box;

        if (!destinationBox.equals(currentBox.uuid())) {
            memberCache.validateRole(MemberCache.SERVICE_ROLE);

            Optional<MessageBoxDto> optionalDestinationBox = boxService.getMessageBox(destinationBox);

            if (optionalDestinationBox.isEmpty()) {
                throw new InvalidDestinationBoxException(destinationBox);
            }
        }

        UnreadMessageEntity unreadMessageEntity = generateUnreadMessage();

        unreadMessageEntity.setContent(message);
        unreadMessageEntity.setBox(destinationBox);
        unreadMessageEntity.setCreatedBy(currentBox.uuid());

        UnreadMessageEntity savedUnreadMessageEntity = unreadMessageRepository.save(unreadMessageEntity);

        return fromUnreadMessageEntity(savedUnreadMessageEntity);
    }

    @Override
    public List<LiteMessageDto> getUnreadMessages() {
        MessageBoxDto currentUserBox = boxService.getOrCreateCurrentUserBox();

        return unreadLiteMessageRepository
            .findByBox(currentUserBox.uuid()).stream().map(this::fromUnreadLiteMessageEntity)
            .toList();
    }

    @Override
    public List<LiteMessageDto> getHistoricMessages() {
        MessageBoxDto currentUserBox = boxService.getOrCreateCurrentUserBox();

        return readLiteMessageRepository.findByBox(currentUserBox.uuid())
            .stream().map(this::fromReadLiteMessageEntity).toList();
    }

    private FullMessageDto fromUnreadMessageEntity(UnreadMessageEntity entity) {
        return new FullMessageDto(
            entity.getUuid(),
            entity.getBox(),
            entity.getContent(),
            entity.getCreatedBy(),
            null,
            entity.getCreatedAt()
        );
    }

    private ReadMessageEntity generateReadEntity(UnreadMessageEntity entity) {
        ReadMessageEntity readMessageEntity = new ReadMessageEntity();

        readMessageEntity.setUuid(entity.getUuid());
        readMessageEntity.setBox(entity.getBox());
        readMessageEntity.setContent(entity.getContent());
        readMessageEntity.setCreatedBy(entity.getCreatedBy());
        readMessageEntity.setReadAt(Instant.now());
        readMessageEntity.setCreatedAt(entity.getCreatedAt());

        return readMessageEntity;
    }

    private FullMessageDto fromReadMessageEntity(ReadMessageEntity entity) {
        return new FullMessageDto(
            entity.getUuid(),
            entity.getBox(),
            entity.getContent(),
            entity.getCreatedBy(),
            entity.getReadAt(),
            entity.getCreatedAt()
        );
    }

    private LiteMessageDto fromUnreadLiteMessageEntity(UnreadLiteMessageEntity entity) {
        return new LiteMessageDto(
            entity.getUuid(),
            entity.getBox(),
            entity.getContent(),
            null,
            entity.getCreatedAt()
        );
    }

    private LiteMessageDto fromReadLiteMessageEntity(ReadLiteMessageEntity entity) {
        return new LiteMessageDto(
            entity.getUuid(),
            entity.getBox(),
            entity.getContent(),
            entity.getReadAt(),
            entity.getCreatedAt()
        );
    }

    private UnreadMessageEntity generateUnreadMessage() {
        UnreadMessageEntity unreadMessageEntity = new UnreadMessageEntity();

        unreadMessageEntity.setUuid(UUID.randomUUID().toString());
        unreadMessageEntity.setCreatedAt(Instant.now());

        return unreadMessageEntity;
    }
}
