package utils.fp.tailrecursion;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class TailRecursionTest {
    @Test
    void factorialTest() {
        assertThat(factorial(0, 1).get()).isEqualTo(1);
        assertThat(factorial(1, 1).get()).isEqualTo(1);
        assertThat(factorial(5, 1).get()).isEqualTo(120);
        assertThat(factorial(11, 1).get()).isEqualTo(39916800);
    }

    private TailRecursion<Integer> factorial(int n, int acc) {
        return (n <= 1)
                ? (Done<Integer>) () -> acc
                : (TailCall<Integer>) () -> factorial(n - 1, acc * n);
    }

    @Test
    void fibonacciTest() {
        assertThat(fibonacci(0, 0, 1).get()).isEqualTo(0);
        assertThat(fibonacci(1, 0, 1).get()).isEqualTo(1);
        assertThat(fibonacci(2, 0, 1).get()).isEqualTo(1);
        assertThat(fibonacci(3, 0, 1).get()).isEqualTo(2);
        assertThat(fibonacci(4, 0, 1).get()).isEqualTo(3);
        assertThat(fibonacci(5, 0, 1).get()).isEqualTo(5);
        assertThat(fibonacci(6, 0, 1).get()).isEqualTo(8);
    }

    private TailRecursion<Integer> fibonacci(int n, int a, int b) {
        switch (n) {
            case 0:
                return (Done<Integer>) () -> a;
            case 1:
                return (Done<Integer>) () -> b;
            default:
                return (TailCall<Integer>) () -> fibonacci(n - 1, b, a + b);
        }
    }
}