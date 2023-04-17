public class Node {
    int value, height;
    Node parent;
    Node left, right;

    public Node(int value) {
        this.value = value;
        this.height = 1;
        this.parent = null;
        this.left = null;
        this.right = null;
    }

}
