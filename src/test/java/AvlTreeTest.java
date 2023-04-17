import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class AvlTreeTest {

    static AvlTree AvlR;

    static AvlTree AvlL;

    @BeforeEach
    void beforeEach() {
        AvlR = new AvlTree();
        AvlR.insert(1);
        AvlR.insert(5);
        AvlR.insert(10);
        AvlR.insert(15);
        AvlR.insert(20);
        AvlR.insert(25);


        AvlL = new AvlTree();
        AvlL.insert(25);
        AvlL.insert(20);
        AvlL.insert(15);
        AvlL.insert(10);
        AvlL.insert(5);
        AvlL.insert(1);
    }

    @DisplayName("size() 테스트")
    @Test
    void sizeTest() {
        assertThat(AvlR.size()).isEqualTo(6);
    }

    @DisplayName("isEmpty() - true 테스트")
    @Test
    void isEmptyTrueTest() {
        //given (준비) - 어떤 데이터가 준비되었을때 (빈 avl 객체)
        AvlTree Avl2 = new AvlTree();

        //when - 어떤 함수가 실행되면 (isEmpty() - 비었는지 판별했을때)
        //then - 어떤 결과가 나와야 한다. (True)
        assertThat(Avl2.isEmpty()).isTrue();
    }

    @DisplayName("isEmpty() - false 테스트")
    @Test
    void isEmptyFalseTest() {
        assertThat(AvlR.isEmpty()).isFalse();
    }


    @DisplayName("contains() - true Test")
    @ParameterizedTest(name = "test {index}")
    @ValueSource(ints = {1, 5, 10, 15, 20, 25})
    void containsTrueTest(int number) {
        assertThat(AvlR.contains(number)).isTrue();
    }

    @DisplayName("contains() - false Test")
    @ParameterizedTest(name = "test {index}")
    @ValueSource(ints = {-1, -2, -3, -4, -5, 0})
    void containsFalseTest(int number) {
        assertThat(AvlR.contains(number)).isFalse();
    }


    private static Stream<Arguments> deleteTestParams() {
        return Stream.of(
                Arguments.of(5, 3, 10),
                Arguments.of(10, 3, 20)
        );
    }
    @DisplayName("delete() - 자식이 한개인 경우 테스트")
    @ParameterizedTest(name = "test {index}")
    @MethodSource("deleteTestParams")
    void deleteTrueTest(int number, int height, int value) {
        AvlL.delete(number);
        assertThat(AvlL.getRootHeight()).isEqualTo(height);
        assertThat(AvlL.getRootValue()).isEqualTo(value);
    }




    private static Stream<Arguments> rotateLeftTestParams1() {
        return Stream.of(
                Arguments.of(1, 1, 1),
                Arguments.of(2, 2, 1),
                Arguments.of(3, 2, 2),
                Arguments.of(4, 3, 2),
                Arguments.of(5, 3, 2),
                Arguments.of(6, 3, 4)
        );
    }
    static AvlTree AvlRR = new AvlTree();

    @DisplayName("rotateLeft() - RR경우 회전 후 테스트")
    @ParameterizedTest(name = "test {index}")
    @MethodSource("rotateLeftTestParams1")
    void rotateLeftTrueTest1(int number, int height, int value) {
        AvlRR.insert(number);
        assertThat(AvlRR.getRootHeight()).isEqualTo(height);
        assertThat(AvlRR.getRootValue()).isEqualTo(value);
    }


    private static Stream<Arguments> rotateLeftTestParams2() {
        return Stream.of(
                Arguments.of(1, 5, -1,-1),
                Arguments.of(5, 15,1, 10),
                Arguments.of(10, 5,-1,-1),
                Arguments.of(15, -1,5,20),
                Arguments.of(20, 15,-1,25),
                Arguments.of(25, 20,-1,-1)
        );
    }
    @DisplayName("rotateLeft() - RR경우 회전 후 최종 연결 노드 테스트")
    @ParameterizedTest(name = "test {index}")
    @MethodSource("rotateLeftTestParams2")
    void rotateLeftTrueTest2(int number, int parentValue, int leftValue, int rightValue) {
        assertThat(AvlR.getParentValue(number)).isEqualTo(parentValue);
        assertThat(AvlR.getLeftValue(number)).isEqualTo(leftValue);
        assertThat(AvlR.getRightValue(number)).isEqualTo(rightValue);
    }



    private static Stream<Arguments> rotateRightTestParams1() {
        return Stream.of(
                Arguments.of(6, 1, 6),
                Arguments.of(5, 2, 6),
                Arguments.of(4, 2, 5),
                Arguments.of(3, 3, 5),
                Arguments.of(2, 3, 5),
                Arguments.of(1, 3, 3)
        );
    }

    static AvlTree AvlLL = new AvlTree();

    @DisplayName("rotateRight() - LL 경우 회전 후 테스트")
    @ParameterizedTest(name = "test {index}")
    @MethodSource("rotateRightTestParams1")
    void rotateRightTrueTest1(int number, int height, int value) {
        AvlLL.insert(number);
        assertThat(AvlLL.getRootHeight()).isEqualTo(height);
        assertThat(AvlLL.getRootValue()).isEqualTo(value);
    }


    private static Stream<Arguments> rotateRightTestParams2() {
        return Stream.of(
                Arguments.of(1, 5, -1,-1),
                Arguments.of(5, 10,1, -1),
                Arguments.of(10, -1,5,20),
                Arguments.of(15, 20,-1,-1),
                Arguments.of(20, 10,15,25),
                Arguments.of(25, 20,-1,-1)
        );
    }
    @DisplayName("rotateRight() - LL 경우 회전 후 최종 연결 노드 테스트")
    @ParameterizedTest(name = "test {index}")
    @MethodSource("rotateRightTestParams2")
    void rotateLRightTrueTest2(int number, int parentValue, int leftValue, int rightValue) {
        assertThat(AvlL.getParentValue(number)).isEqualTo(parentValue);
        assertThat(AvlL.getLeftValue(number)).isEqualTo(leftValue);
        assertThat(AvlL.getRightValue(number)).isEqualTo(rightValue);
    }


}