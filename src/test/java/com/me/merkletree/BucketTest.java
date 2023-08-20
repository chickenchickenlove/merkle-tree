package com.me.merkletree;

import org.junit.jupiter.api.Test;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class BucketTest {



    @Test
    void test1() throws NoSuchAlgorithmException {
        // Given:
        final int bucketSize = 3;
        final int bucketIndex = 0;
        final MD5Hash hash = new MD5Hash();
        final Bucket bucket = new Bucket(bucketSize, bucketIndex, hash);
        final List<String> values = List.of("A", "B", "C");


        // When:
        IntStream.range(0, values.size())
                .forEach(index -> bucket.put(index, values.get(index)));

        // Then:
        for (int i = 0; i < bucketSize; i++) {
            assertThat(values.get(0)).isEqualTo(bucket.get(0));
        }
    }

    @Test
    void test2() throws NoSuchAlgorithmException {
        // Given:
        final int bucketSize = 3;
        final int bucketIndex = 0;
        final MD5Hash hash = new MD5Hash();
        final Bucket bucket = new Bucket(bucketSize, bucketIndex, hash);
        final List<String> values = List.of("A", "B", "C");
        final long expectedValue = 252667656L;

        IntStream.range(0, values.size())
                .forEach(index -> bucket.put(index, values.get(index)));

        // When:
        Long result = bucket.bucketHash();

        // Then:
        assertThat(result).isEqualTo(expectedValue);
    }

    @Test
    void test3() throws NoSuchAlgorithmException {
        // Given:
        final int bucketSize = 3;
        final int bucketIndex = 0;
        final MD5Hash hash = new MD5Hash();

        final Bucket bucket1 = new Bucket(bucketSize, bucketIndex, hash);
        final Bucket bucket2 = new Bucket(bucketSize, bucketIndex + 1, hash);
        final List<String> values = List.of("A", "B", "C");

        IntStream.range(0, values.size())
                .forEach(index -> bucket1.put(index, values.get(index)));
        IntStream.range(0, values.size())
                .forEach(index -> bucket2.put(index, values.get(index)));

        // When:
        Long bucket1Hash = bucket1.bucketHash();
        Long bucket2Hash = bucket2.bucketHash();

        // Then:
        assertThat(bucket1Hash).isNotEqualTo(bucket2Hash);
    }

}