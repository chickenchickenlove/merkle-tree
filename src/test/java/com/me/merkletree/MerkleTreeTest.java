package com.me.merkletree;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class MerkleTreeTest {

    final List<String> values1 = List.of("A", "B", "C");
    final List<String> values2 = List.of("A", "B");


    @Test
    @DisplayName("in case of even.")
    void test1() {

        // Given:
        final int bucketSize = 3;
        final int numberOfBucket = 4;
        List<Bucket> buckets = IntStream.range(0, numberOfBucket)
                .mapToObj(index -> createBucket(bucketSize, index, values1))
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
                .mapToObj(index -> createBucket(bucketSize, index, values1))
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

    @Test
    @DisplayName("compare Test.")
    void test3() {

        // Given:
        final int bucketSize = 3;
        final int numberOfBucket = 3;
        List<Bucket> buckets = IntStream.range(0, numberOfBucket)
                .mapToObj(index -> createBucket(bucketSize, index, values1))
                .toList();

        MerkleTree merkleTree1 = new MerkleTree(buckets);
        MerkleTree merkleTree2 = new MerkleTree(buckets);

        // When:
        List<Integer> result = merkleTree1.compare(merkleTree2);


        // Then
        assertThat(result.size()).isEqualTo(0);
    }

    @Test
    @DisplayName("compare Test.")
    void test4() {
        // Given:
        final int bucketSize = 3;

        List<Bucket> buckets1 = List.of(
                createBucket(bucketSize, 0, values1),
                createBucket(bucketSize, 1, values1),
                createBucket(bucketSize, 2, values1));

        List<Bucket> buckets2 = List.of(
                createBucket(bucketSize, 0, values1),
                createBucket(bucketSize, 1, values1),
                createBucket(bucketSize, 2, values2));

        MerkleTree merkleTree1 = new MerkleTree(buckets1);
        MerkleTree merkleTree2 = new MerkleTree(buckets2);

        // When:
        List<Integer> result = merkleTree1.compare(merkleTree2);


        // Then
        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0)).isEqualTo(2);
    }

    @Test
    @DisplayName("compare Test.")
    void test5() {
        // Given:
        final int bucketSize = 3;

        List<Bucket> buckets1 = List.of(
                createBucket(bucketSize, 0, values1),
                createBucket(bucketSize, 1, values2),
                createBucket(bucketSize, 2, values1));

        List<Bucket> buckets2 = List.of(
                createBucket(bucketSize, 0, values1),
                createBucket(bucketSize, 1, values1),
                createBucket(bucketSize, 2, values2));

        MerkleTree merkleTree1 = new MerkleTree(buckets1);
        MerkleTree merkleTree2 = new MerkleTree(buckets2);

        // When:
        List<Integer> result = merkleTree1.compare(merkleTree2);


        // Then
        assertThat(result.size()).isEqualTo(2);
        assertThat(result).containsExactly(1, 2);
    }


    Bucket createBucket(int bucketSize, int bucketIndex, List<String> values) {
        final Bucket bucket = new Bucket(bucketSize, bucketIndex);
        IntStream.range(0, values.size())
                .forEach(index -> bucket.put(index, values.get(index)));
        return bucket;
    }
}