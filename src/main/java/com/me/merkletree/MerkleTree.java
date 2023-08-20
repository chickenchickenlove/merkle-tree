package com.me.merkletree;

import com.sun.source.tree.Tree;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    public Node getLeftNode(Node node) {
        return node.getLeft();
    }

    public Node getRightNode(Node node) {
        return node.getRight();
    }


    public List<Integer> compare(MerkleTree tree) {
        Node otherRootNode = tree.getRoot();
        return compareRecursive(this.root, otherRootNode);
    }

    private List<Integer> compareRecursive(Node parentNode, Node comparedParentNode) {
        // In case of leafNode.
        if (Objects.nonNull(parentNode.getBucket()) && Objects.nonNull(comparedParentNode.getBucket())) {
            if (parentNode.getNodeHash() != comparedParentNode.getNodeHash()) {
                return List.of(parentNode.getBucket().getBucketIndex());
            }
        }

        // In case of not leafNode.
        ArrayList<Integer> result = new ArrayList<>();

        Node leftNode = parentNode.getLeft();
        Node comparedLeftNode = comparedParentNode.getLeft();

        if (Objects.nonNull(leftNode) && Objects.nonNull(comparedLeftNode)) {
            if (leftNode.getNodeHash() != comparedLeftNode.getNodeHash()) {
                List<Integer> leftNodeResult = compareRecursive(leftNode, comparedLeftNode);
                result.addAll(leftNodeResult);
            }
        }

        Node rightNode = parentNode.getRight();
        Node comparedRightNode = comparedParentNode.getRight();

        if (Objects.nonNull(rightNode) && Objects.nonNull(comparedRightNode)) {
            if (rightNode.getNodeHash() != comparedRightNode.getNodeHash()) {
                List<Integer> rightNodeResult = compareRecursive(rightNode, comparedRightNode);
                result.addAll(rightNodeResult);
            }
        }

        return result;
    }
}