package com.me.merkletree;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Hash {

    private final MessageDigest instance;

    public MD5Hash() {
        MessageDigest instance;
        try {
            instance = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            instance = null;
        }
        this.instance = instance;
    }

    public long hash(String key) {
        instance.reset();
        instance.update(key.getBytes());
        byte[] digest = instance.digest();
        long h = 0;
        for (int i = 0; i < 4; i++) {
            h <<= 8;
            h |= (digest[i]) & 0xFF;
        }
        return h;
    }
}