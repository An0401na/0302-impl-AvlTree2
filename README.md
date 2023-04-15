# Implementation 정보
## interface - `iAvlTree`
### `void insert(int value)`
`value`를 적절한 곳에 삽입합니다.

### `boolean contains(int value)`
현재 이진탐색트리에 `value`가 있는지 확인해서 `boolean`값으로 반환합니다.

### `void delete(int value)`
이진탐색트리에서 `value`값을 삭제합니다. 

### `void insert(int value)`
`value`를 적절한 곳에 삽입합니다.

### `boolean isEmpty()`
이진탐색트리가 비어있는지를 `boolean` 값으로 반환합니다.

### `int size()`
이진탐색트리에 들어있는 값들의 개수를 반환합니다.

### `void rotateLeft(Node node)`
`node`를 루트로 하는 트리(혹은 서브트리)를 왼쪽으로 회전시킵니다.

### `void rotateRight(Node node)`
`node`를 루트로 하는 트리(혹은 서브트리)를 오른쪽으로 회전시킵니다.

## `Node` 클래스 정보
### 공통 필드
- `int value` : 각 노드에 담긴 값
- `int height` : 해당 노드를 루트로 하는 이진탐색트리 높이
- `Node left` : 왼쪽 자식
- `Node right` : 오른쪽 자식
