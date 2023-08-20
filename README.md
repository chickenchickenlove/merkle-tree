### Merkle Tree
- 이 자료구조는 `가상 면접 사례로 배우는 대규모 시스템 설계 기초` 6장 분산 키-값 저장소를 공부하며 작성했습니다.

### Usage Case
- 머클 트리는 각 분산 노드의 영구적 장애 복구에 효율적으로 사용될 수 있습니다.
- 다음과 같이 사용됩니다.
  - `1번 노드 RootNode HashValue == 2번 노드 RootNode HashValue` 
    - 이 경우 복구해야 할 데이터는 없습니다. 
  - `1번 노드 RootNode HashValue != 2번 노드 RootNode HashValue`
    - 이 경우, 서로의 State가 다르므로 복구할 데이터가 필요합니다. 

### 복구 시, 방법.
- 루트 노드의 값이 다른 경우, `Left` / `Right` 노드의 `HashValue`를 비교하며 복구해야 할 부분을 빠르게 찾아낼 수 있습니다.


### 어떤 Bucket이 다른지 찾는 테스트 코드
- https://github.com/chickenchickenlove/merkle-tree/blob/1740569c95b23faef55fc1dc1195f6bc22e9f6eb/src/test/java/com/me/merkletree/MerkleTreeTest.java#L71-L155
- 값이 다른 버켓의 인덱스를 찾아서, 복구를 돕습니다. 

