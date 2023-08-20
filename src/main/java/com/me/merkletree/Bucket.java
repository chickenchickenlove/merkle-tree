package com.me.merkletree;

import java.util.stream.IntStream;

public class Bucket {

    private final String[] store;
    private final int bucketSize;
    private final int bucketIndex;
    private final MD5Hash hash;

    public Bucket(int bucketSize, int bucketIndex, MD5Hash hash) {
        this.store = new String[bucketSize];
        this.bucketSize = bucketSize;
        this.bucketIndex = bucketIndex;
        this.hash = hash;
    }

    public void put(int index, String value) {
        this.store[index] = value;
    }

    public String get(int index) {
        return this.store[index];
    }

    public Long bucketHash() {
        final int baseIndex = bucketSize * bucketIndex;
        final long hashSum = IntStream.range(0, store.length)
                .filter(index -> store[index] != null)
                .mapToLong(index -> hash.hash(String.valueOf(baseIndex + index)))
                .sum();
        final long bucketHash = hash.hash(String.valueOf(hashSum));
        return bucketHash;
    }

}