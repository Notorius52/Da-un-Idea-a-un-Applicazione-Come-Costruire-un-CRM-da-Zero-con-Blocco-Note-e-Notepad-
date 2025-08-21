package com.tuocrm.core.util;

import java.util.UUID;

public final class IdGenerator {

    public static String generateUniqueId() {
        return UUID.randomUUID().toString();
    }
}