public interface iAvlTree {
    void insert(int value);
    boolean contains(int value);
    void delete(int value);
    boolean isEmpty();
    int size();
    void rotateLeft(Node node);
    void rotateRight(Node node);
}
