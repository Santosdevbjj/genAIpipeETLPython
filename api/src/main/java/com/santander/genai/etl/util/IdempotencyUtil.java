package com.santander.genai.etl.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public final class IdempotencyUtil {

    private IdempotencyUtil() {}

    public static String fingerprint(Long clienteId, String texto, String canal) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            String base = clienteId + "|" + canal + "|" + texto;
            byte[] hash = md.digest(base.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte b : hash) sb.append(String.format("%02x", b));
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-256 not available", e);
        }
    }
}
