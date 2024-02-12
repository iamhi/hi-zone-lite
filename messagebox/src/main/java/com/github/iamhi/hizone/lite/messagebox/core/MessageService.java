package com.github.iamhi.hizone.lite.messagebox.core;

import com.github.iamhi.hizone.lite.messagebox.core.dtos.FullMessageDto;
import com.github.iamhi.hizone.lite.messagebox.core.dtos.LiteMessageDto;

import java.util.List;
import java.util.Optional;

public interface MessageService {

    Optional<FullMessageDto> messageDetails(String uuid);

    Optional<FullMessageDto> readMessage(String uuid);

    FullMessageDto sendMessage(String box, String message);

    List<LiteMessageDto> getUnreadMessages();

    List<LiteMessageDto> getHistoricMessages();
}
