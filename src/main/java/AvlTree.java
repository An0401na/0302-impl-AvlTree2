import exceptions.CustomDuplicatedElementException;
import exceptions.CustomNoSuchElementException;

public class AvlTree implements iAvlTree{
    private Node root;
    private int size;

    @Override
    public void insert(int value) {
        if(root == null) {
            root = new Node(value);
            size++;
        }else{
            insertNode(root, value);
        }
    }

    private void insertNode(Node node, int value) {
        //삽입과정
        if(value < node.value){
            if(node.left == null){
                node.left = new Node(value);
                node.left.parent = node;
                size++;
            }else{
                insertNode(node.left, value);
            }
        }else if(value > node.value){
            if(node.right == null){
                node.right = new Node(value);
                node.right.parent = node;
                size++;
            }else{
                insertNode(node.left, value);
            }
        }else{
            throw new CustomDuplicatedElementException();
        }

        //높이 갱신
        node.height = updateHeight(node);
        //밸런스 확인하고 맞추기
        int balance = getBalanceFactor(node);

        //왼쪽 - 오른쪽 => 결과가 양수 이면 왼쪽 서브트리의 높이가 더 크다 즉, 왼쪽으로 편향되어 있다.
        //LL인 경우 => Right Rotation을 진행해주어야 한다.
        if(balance > 1 && value < node.left.value){
            rotateRight(node);
        }

        //왼쪽 - 오른쪽 => 결과가 음수이면 오른쪽 서브트리의 높이가 더 크다 즉, 오른쪽으로 편향되어 있다.
        //RR인 경우 =>Left Rotation을 진행해주어야 한다.
        //leftRight =>
        if(balance < -1 && value > node.right.value ) {
            rotateLeft(node);
        }

        // 자식의 값보다 더 크다면 (넣은노드(오른쪽)-왼쪽자식-노드)일테니까
        //    /
        //    \
        // LR인 경우  => 아래에 있던 LR이 위로 올라옴
        /*     node             node			 LR
             L        =>      LR       =>      L    node
         *    LR    (Left)  L    b 	  (Right)   a  b
         *   a  b            a
         */
        if(balance > 1 && value > node.left.value) {
            rotateLeft(node.left);
            rotateRight(node);
        }

        // 자식의 값보다 더 작다면 (넣은노드(왼쪽)-오른쪽자식-노드)일테니까
        //    \
        //    /
        // RL인 경우 => 아래에 있던 LR이 위로 올라옴
        /*  node             node			      RL
	            R        =>      RL    =>    node    R
	    *     RL      (Right)  	a  R  (Left)    a   b
	    *    a  b                 b
	    */
        if(balance < -1 && value < node.right.value) {
            rotateRight(node.right);
            rotateLeft(node);
        }
    }

    private int updateHeight(Node node) {
        int height=0;
        if(node.left==null && node.right!=null){
            height = node.right.height+1;
        }else if(node.left != null && node.right == null){
           height = node.left.height+1;
        }else if(node.left == null && node.right==null){
            height =1;
        }else {
           height = Math.max(node.left.height, node.right.height) + 1;
        }
        return height;

    }

    //root를 기준으로 왼쪽 서브트리와 오른쪽 서브트리의 차이를 구하는 함수
    private int getBalanceFactor(Node node) {
        if(node == null) return 0;
        else return node.left.height - node.right.height;
    }


    @Override
    public boolean contains(int value) {
        return containsNode(root, value);
    }

    private boolean containsNode(Node node, int value) {
        if(node == null) {
            return false;
        }

        if(node.value == value) {
            return true;
        }

        if(value < node.value) {
            return containsNode(node.left, value);
        }else {
            return containsNode(node.right, value);
        }
    }

    @Override
    public void delete(int value) {
        if(!contains(value)){
            throw new CustomNoSuchElementException();
        }
        Node target = findNodeByValue(root, value);

        //삭제할 노드에 자식이 없는 경우
        //-> 부모에서 타겟과 연결된 쪽의 연결 끊기 
        if(target.left == null && target.right == null) {
            if(target == root) {
                root = null;
                return;
            }

            if(target.parent.left ==target) {
                target.parent.left = null;
            }else {
                target.parent.right = null;
            }
        }else if(target.left != null && target.right == null) {
            //왼쪽 자식만 있는 경우
            Node child = target.left;

            if(target == root) {
                //root를 삭제하므로 루트의 자식을 루트로 올려줌
                root = child;
                return;
            }

            child.parent = target.parent; //자식의 부모를 타겟에서 타겟의 부모로 변경
            //타겟의 부모에서 타겟과 연결된 자식이 왼쪽인지 오른쪽인지 찾아서 타켓의 자식과 연결
            if(target.parent.left == target) {
                target.parent.left = child;
            }else {
                target.parent.right = child;
            }

        }else if(target.left == null && target.right != null) {
            //오른쪽 자식만 있는 경우
            Node child = target.right;

            if(target == root) {
                //root를 삭제하므로 루트의 자식을 루트로 올려줌
                root = child;
                return;
            }

            child.parent = target.parent; //자식의 부모를 타겟에서 타겟의 부모로 변경
            //타겟의 부모에서 타겟과 연결된 자식이 왼쪽인지 오른쪽인지 찾아서 타켓의 자식과 연결
            if(target.parent.left == target) {
                target.parent.left = child;
            }else {
                target.parent.right = child;
            }

        }else {
            //자식이 둘 다 있을 경우
            Node replaceNode = findReplaceNode(target.left);
            //왼쪽으로 왜가지.. ? 오른쪽의 가장 작은 값을 찾아야하는거 아닌가...

            if(target == root) {
                root = replaceNode;
            }else {
                if(target.parent.left == target) {
                    target.parent.left = replaceNode;
                }else {
                    target.parent.right = replaceNode;
                }
            }

            if(replaceNode.left != null) {
                if(replaceNode == target.left) {
                    replaceNode.parent.left = replaceNode.left;
                }else {
                    replaceNode.parent.right = replaceNode.left;

                }
            }else {
                if(replaceNode == target.left) {
                    replaceNode.parent.left = null;
                }else {
                    replaceNode.parent.right = null;
                }
            }

            if(target == root) {
                replaceNode.parent = null;
                replaceNode.parent = target.parent;
            }
            target.right.parent = replaceNode;

            replaceNode.left = target.left;
            replaceNode.right = target.right;
        }
        size++;

        checkBalance(target.parent);
    
    }

    //삭제할 노드와 대체할 노드 찾기
    private Node findReplaceNode(Node node) {
        while (node.right != null) {
            node = node.right;
        }
        return node;
    }


    //삭제한 노드의 부모부터 균형을 확인해가면서 루트까지 올라간다.
    //깨졌으면 회전을 통해 맞추준다.
    private void checkBalance(Node node) {


        node.height = updateHeight(node);

        int balance = getBalanceFactor(node);

        //왼쪽 - 오른쪽 => 결과가 양수 이면 왼쪽 서브트리의 높이가 더 크다 즉, 왼쪽으로 편향되어 있다.
        //LL인 경우 => Right Rotation을 진행해주어야 한다.
        if(balance > 1 && getBalanceFactor(node.left)>=0) { //자식의 값보다 더 작다면 (넣은노드(왼쪽)-왼쪽-노드) 일테니까
            rotateRight(node);
        }

        //왼쪽 - 오른쪽 => 결과가 음수이면 오른쪽 서브트리의 높이가 더 크다 즉, 오른쪽으로 편향되어 있다.
        //RR인 경우 =>Left Rotation을 진행해주어야 한다.
        if(balance < -1 && getBalanceFactor(node.right)<=0 ) {
            rotateLeft(node);
        }


        // 자식의 값보다 더 크다면 (넣은노드(오른쪽)-왼쪽자식-노드)일테니까
        //    /
        //    \
        // LR인 경우  => 아래에 있던 LR이 위로 올라옴
        /*     node             node			 LR
             L        =>      LR       =>      L    node
         *    LR    (Left)  L    b 	  (Right)   a  b
         *   a  b            a
         */
        if(balance > 1 && getBalanceFactor(node.left)<0) {
            rotateLeft(node.left);
            rotateRight(node);
        }

        // 자식의 값보다 더 작다면 (넣은노드(왼쪽)-오른쪽자식-노드)일테니까
        //    \
        //    /
        // RL인 경우 => 아래에 있던 LR이 위로 올라옴
        /*  node             node			      RL
	            R        =>      RL    =>    node    R
	    *     RL      (Right)  	a  R  (Left)    a   b
	    *    a  b                 b
	    */
        if(balance < -1 && getBalanceFactor(node.right)>0) {
            rotateRight(node.right);
            rotateLeft(node);
        }

        if(node == root) {
            return;
        }
        checkBalance(node.parent);
    }


    private Node findNodeByValue(Node node, int value) {
        while (node.right != null) {
            node = node.right;
        }
        return node;
    }


    @Override
    public boolean isEmpty() {
        if(root == null){
            return true;
        }
        return false;
    }
    
    @Override
    public int size() {
        return size;
    }
    
    @Override
    public void rotateLeft(Node node) {
        Node R = node.right;
        Node RL = node.right.left;

        R.parent = node.parent;

        node.parent = R;
        node.right = RL;

        R.left = node;

        RL.parent = node;

        node.height = Math.max(node.left.height, node.right.height)+1;
        R.height = Math.max(R.left.height, R.right.height)+1;
    }
    
    @Override
    public void rotateRight(Node node) {
        Node L = node.left;
        Node LR = node.left.right;

        L.parent = node.parent;

        node.parent = L;
        node.left = LR;

        L.right = node;

        LR.parent = node;

        node.height = Math.max(node.left.height, node.right.height)+1;
        L.height = Math.max(L.left.height, L.right.height)+1;
    }

}
