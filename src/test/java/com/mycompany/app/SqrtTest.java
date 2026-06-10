package com.mycompany.app;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Проверка всех публичных методов {@link Sqrt}.
 * Тесты сгруппированы по сценариям, а не по шаблону «один assert на метод».
 */
class SqrtTest {

    private static final double INPUT = 16.0;
    private static final double START_GUESS = 1.0;

    private Sqrt sqrt;

    @BeforeEach
    void setUp() {
        sqrt = new Sqrt(INPUT);
    }

    @Test
    void constructorStoresArgumentUsedByCalc() {
        Sqrt unit = new Sqrt(25.0);
        assertEquals(5.0, unit.calc(), 1e-7);
    }

    @Test
    void averageReturnsArithmeticMean() {
        assertEquals(3.5, sqrt.average(2.0, 5.0), 1e-12);
    }

    @Test
    void averageIsCommutative() {
        double direct = sqrt.average(7.0, 3.0);
        double reversed = sqrt.average(3.0, 7.0);
        assertEquals(direct, reversed, 1e-12);
    }

    @Test
    void goodAcceptsGuessWithinDelta() {
        assertTrue(sqrt.good(4.0, 16.0));
    }

    @Test
    void goodRejectsGuessOutsideDelta() {
        assertFalse(sqrt.good(3.0, 16.0));
    }

    @Test
    void improveReturnsAverageOfGuessAndQuotient() {
        double guess = 3.0;
        double quotient = INPUT / guess;
        assertEquals(sqrt.average(guess, quotient), sqrt.improve(guess, INPUT), 1e-12);
    }

    @Test
    void improveNarrowsBracketAroundRoot() {
        double guess = 1.0;
        double partner = INPUT / guess;
        double refined = sqrt.improve(guess, INPUT);
        assertTrue(refined >= Math.min(guess, partner));
        assertTrue(refined <= Math.max(guess, partner));
    }

    @Test
    void iterStopsWhenGuessAlreadyGood() {
        assertEquals(4.0, sqrt.iter(4.0, INPUT), 1e-12);
    }

    @Test
    void iterRefinesBadGuessUntilConvergence() {
        double root = sqrt.iter(START_GUESS, INPUT);
        assertEquals(4.0, root, 1e-7);
    }

    @Test
    void calcFindsSquareRootOfTwo() {
        Sqrt fromTwo = new Sqrt(2.0);
        assertEquals(Math.sqrt(2.0), fromTwo.calc(), 1e-7);
    }

    @ParameterizedTest
    @CsvSource({"1, 1", "9, 3", "0.25, 0.5", "100, 10"})
    void calcPreservesSquareForPerfectSquares(double value, double expectedRoot) {
        Sqrt unit = new Sqrt(value);
        assertEquals(expectedRoot, unit.calc(), 1e-7);
    }

    @Test
    void calcResultSquaredMatchesOriginalArgument() {
        double root = sqrt.calc();
        assertEquals(INPUT, root * root, 1e-7);
    }
}
