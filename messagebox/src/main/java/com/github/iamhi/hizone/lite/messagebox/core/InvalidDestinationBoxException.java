package com.github.iamhi.hizone.lite.messagebox.core;

public class InvalidDestinationBoxException extends RuntimeException {

    InvalidDestinationBoxException(String uuid) {
        super("Destination box not found: " + uuid);
    }
}
