package com.github.iamhi.hizone.lite.messagebox.core;

import com.github.iamhi.hizone.lite.messagebox.core.dtos.MessageBoxDto;

import java.util.Optional;

public interface BoxService {

    MessageBoxDto getOrCreateCurrentUserBox();

    Optional<MessageBoxDto> getMessageBox(String uuid);
}
