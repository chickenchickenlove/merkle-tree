package com.me.merkletree;

import java.util.stream.IntStream;

public class Bucket {

    private final String[] store;
    private final int bucketSize;
    private final int bucketIndex;

    public Bucket(int bucketSize, int bucketIndex) {
        this.store = new String[bucketSize];
        this.bucketSize = bucketSize;
        this.bucketIndex = bucketIndex;
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
                .mapToLong(index -> HashUtils.getHash(String.valueOf(baseIndex + index)))
                .sum();
        final long bucketHash = HashUtils.getHash(String.valueOf(hashSum));
        return bucketHash;
    }

}