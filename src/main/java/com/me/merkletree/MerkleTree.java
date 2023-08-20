package com.me.merkletree;

import lombok.Getter;

import java.util.List;

@Getter
public class MerkleTree {

    private final Node root;

    public MerkleTree(List<Bucket> buckets) {
        this.root = new Node();
        addNode(root, buckets);
    }

    public long addNode(Node parentNode, List<Bucket> buckets) {

        if (buckets.size() == 1) {
            Bucket bucket = buckets.get(0);
            parentNode.setBucket(bucket);

            Long bucketHash = bucket.bucketHash();
            parentNode.setNodeHash(bucketHash);

            return bucketHash;
        }

        int halfSize = (buckets.size() % 2) == 0 ?
                buckets.size() / 2 :
                buckets.size() / 2 + 1;
        List<Bucket> leftBucket = buckets.subList(0, halfSize);
        List<Bucket> rightBucket = buckets.subList(halfSize, buckets.size());

        long tempHashValue = 0L;

        if (leftBucket.size() > 0) {
            Node leftNode = new Node();
            parentNode.setLeft(leftNode);
            tempHashValue += addNode(leftNode, leftBucket);
        }

        if (rightBucket.size() > 0) {
            Node rightNode = new Node();
            parentNode.setRight(rightNode);
            tempHashValue += addNode(rightNode, rightBucket);
        }

        long nodeHashValue = HashUtils.getHash(String.valueOf(tempHashValue));
        parentNode.setNodeHash(nodeHashValue);

        return nodeHashValue;
    }

}
