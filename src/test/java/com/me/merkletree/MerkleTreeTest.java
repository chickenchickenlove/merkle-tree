package com.me.merkletree;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class MerkleTreeTest {

    @Test
    @DisplayName("in case of even.")
    void test1() {

        // Given:
        final int bucketSize = 3;
        final int numberOfBucket = 4;
        List<Bucket> buckets = IntStream.range(0, numberOfBucket)
                .mapToObj(index -> createBucket(bucketSize, index))
                .toList();

        long leftNodeHash = HashUtils.getHash(String.valueOf(
                buckets.get(0).bucketHash() + buckets.get(1).bucketHash()));
        long rightNodeHash = HashUtils.getHash(String.valueOf(
                buckets.get(2).bucketHash() + buckets.get(3).bucketHash()));

        long rootNodeHash = HashUtils.getHash(String.valueOf(
                leftNodeHash + rightNodeHash));

        // When:
        MerkleTree merkleTree = new MerkleTree(buckets);

        // Then
        assertThat(merkleTree.getRoot().getNodeHash()).isEqualTo(rootNodeHash);

    }

    @Test
    @DisplayName("in case of odd.")
    void test2() {

        // Given:
        final int bucketSize = 3;
        final int numberOfBucket = 3;
        List<Bucket> buckets = IntStream.range(0, numberOfBucket)
                .mapToObj(index -> createBucket(bucketSize, index))
                .toList();

        long leftNodeHash = HashUtils.getHash(String.valueOf(
                buckets.get(0).bucketHash() + buckets.get(1).bucketHash()));
        long rightNodeHash = buckets.get(2).bucketHash();

        long rootNodeHash = HashUtils.getHash(String.valueOf(leftNodeHash + rightNodeHash));

        // When:
        MerkleTree merkleTree = new MerkleTree(buckets);

        // Then
        assertThat(merkleTree.getRoot().getNodeHash()).isEqualTo(rootNodeHash);

    }

    Bucket createBucket(int bucketSize, int bucketIndex) {
        final Bucket bucket = new Bucket(bucketSize, bucketIndex);
        final List<String> values = List.of("A", "B", "C");
        IntStream.range(0, values.size())
                .forEach(index -> bucket.put(index, values.get(index)));
        return bucket;
    }
}