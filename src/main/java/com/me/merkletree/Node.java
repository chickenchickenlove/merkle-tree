package com.me.merkletree;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class Node {
    private long nodeHash;
    private Node left;
    private Node right;
    private Bucket bucket;
}
