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
        tempHashValue += addLeftSide(parentNode, leftBucket);
        tempHashValue += addRightSide(parentNode, rightBucket);

        long nodeHashValue = HashUtils.getHash(String.valueOf(tempHashValue));
        parentNode.setNodeHash(nodeHashValue);

        return nodeHashValue;
    }

    private long addLeftSide(Node parentNode, List<Bucket> leftBucket) {
        if (leftBucket.size() > 0) {
            Node leftNode = new Node();
            parentNode.setLeft(leftNode);
            return addNode(leftNode, leftBucket);
        }
        return 0L;
    }

    private long addRightSide(Node parentNode, List<Bucket> rightBucket) {
        if (rightBucket.size() > 0) {
            Node rightNode = new Node();
            parentNode.setRight(rightNode);
            return addNode(rightNode, rightBucket);
        }
        return 0;
    }

}
