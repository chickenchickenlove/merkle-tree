package com.me.merkletree;

public class HashUtils {

    private static final MD5Hash hash = new MD5Hash();
    public static final long getHash(String key) {
        return hash.hash(key);
    }



}
