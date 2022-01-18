package com.accenture.projecttask;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class ProjectTaskApplicationTests {
    Calculator calculator = new Calculator();

    @Test
    void itShouldAddTwoNumbers() {
        // giver
        int numberOne = 10;
        int numberTwo = 15;
        // when
        int result = calculator.add(numberOne, numberTwo);
        // then
        int expected = 25;
        assertEquals(expected, result);
    }

    class Calculator {
        int add(int a, int b) {
            return a + b;
        }
    }

}
