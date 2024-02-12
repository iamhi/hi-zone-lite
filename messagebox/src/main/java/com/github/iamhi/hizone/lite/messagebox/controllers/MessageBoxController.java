package com.github.iamhi.hizone.lite.messagebox.controllers;

import com.github.iamhi.hizone.lite.messagebox.controllers.requests.SendMessageRequest;
import com.github.iamhi.hizone.lite.messagebox.controllers.responses.LiteMessageResponse;
import com.github.iamhi.hizone.lite.messagebox.controllers.responses.MessageResponse;
import com.github.iamhi.hizone.lite.messagebox.core.MessageService;
import com.github.iamhi.hizone.lite.messagebox.core.dtos.FullMessageDto;
import com.github.iamhi.hizone.lite.messagebox.core.dtos.LiteMessageDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/messagebox")
public record MessageBoxController(
    MessageService messageService
) {

    @GetMapping("/details/{uuid}")
    public Optional<MessageResponse> fetchMessageDetails(@PathVariable String uuid) {
        return messageService.messageDetails(uuid).map(this::responseFromFullMessage);
    }

    @GetMapping("/read/{uuid}")
    public Optional<MessageResponse> readMessage(@PathVariable String uuid) {
        return messageService().readMessage(uuid).map(this::responseFromFullMessage);
    }

    @GetMapping("/historic")
    public List<LiteMessageResponse> fetchHistoricMessages() {
        return messageService.getHistoricMessages().stream().map(this::responseFromLiteMessage)
            .toList();
    }

    @GetMapping("/unread")
    public List<LiteMessageResponse> fetchUnreadMessages() {
        return messageService.getUnreadMessages().stream().map(this::responseFromLiteMessage)
            .toList();
    }

    @PostMapping
    public MessageResponse sendMessage(SendMessageRequest request) {
        return responseFromFullMessage(messageService.sendMessage(request.box(), request.message()));
    }

    private MessageResponse responseFromFullMessage(FullMessageDto fullMessageDto) {
        return new MessageResponse(
            fullMessageDto.uuid(),
            fullMessageDto.box(),
            fullMessageDto.content(),
            fullMessageDto.createdBy(),
            fullMessageDto.readAt(),
            fullMessageDto.createdAt()
        );
    }

    private LiteMessageResponse responseFromLiteMessage(LiteMessageDto liteMessageDto) {
        return new LiteMessageResponse(
            liteMessageDto.uuid(),
            liteMessageDto.box(),
            liteMessageDto.content(),
            liteMessageDto.readAt(),
            liteMessageDto.createdAt()
        );
    }
}
