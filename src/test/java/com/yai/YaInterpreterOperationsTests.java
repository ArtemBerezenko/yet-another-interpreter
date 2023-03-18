package com.yai;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class YaInterpreterOperationsTests {
    protected YaInterpreter interpreter;
    protected double delta = 0.001;

    @BeforeEach
    public void setup() {
        this.interpreter = new YaInterpreter();
    }

    @Test
     void testInvalidInput() {
        assertThrows(Exception.class, () -> interpreter.evaluate(null));
        assertThrows(Exception.class, () -> interpreter.evaluate(""));
        assertThrows(Exception.class, () -> interpreter.evaluate("2"));
        assertThrows(Exception.class, () -> interpreter.evaluate("a = abc"));
        assertThrows(Exception.class, () -> interpreter.evaluate("a = 2"));
    }

    @Test
     void testSingleTerms() {
        assertEquals(1.0, interpreter.evaluate("var a = 1;").get("a"), delta);
        assertEquals(10.0, interpreter.evaluate("var a = 10;").get("a"), delta);
        assertEquals(1.0, interpreter.evaluate("var a = (1);").get("a"), delta);
        assertEquals(10.0, interpreter.evaluate("var a = (10);").get("a"), delta);
        assertEquals(10.012, interpreter.evaluate("var a = (10.01200);").get("a"), delta);
    }

    @Test
     void testAddition() {
        assertEquals(3.0, interpreter.evaluate("var a = 1+2;").get("a"), delta);
        assertEquals(3.0, interpreter.evaluate("var a = 2+1;").get("a"), delta);
        assertEquals(6.0, interpreter.evaluate("var a = 1+(2+3);").get("a"), delta);
        assertEquals(6.0, interpreter.evaluate("var a = (1+2)+3;").get("a"), delta);
        assertEquals(3.2, interpreter.evaluate("var a = 1.1+2.1;").get("a"), delta);
        assertEquals(3.2, interpreter.evaluate("var a = 2.1+1.1;").get("a"), delta);
        assertEquals(6.3, interpreter.evaluate("var a = 1.1+(2.1+3.1);").get("a"), delta);
        assertEquals(6.3, interpreter.evaluate("var a = (1.1+2.1)+3.1;").get("a"), delta);
        assertEquals(15.0, interpreter.evaluate("var a = 1+2+3+4+5;").get("a"), delta);
    }

    @Test
     void testSubtraction() {
        assertEquals(-1.0, interpreter.evaluate("var a = 1-2;").get("a"), delta);
        assertEquals(1.0, interpreter.evaluate("var a = 2-1;").get("a"), delta);
        assertEquals(2.0, interpreter.evaluate("var a = 1-(2-3);").get("a"), delta);
        assertEquals(-4.0, interpreter.evaluate("var a = (1-2)-3;").get("a"), delta);
        assertEquals(-1.0, interpreter.evaluate("var a = 1.1-2.1;").get("a"), delta);
        assertEquals(1.0, interpreter.evaluate("var a = 2.1-1.1;").get("a"), delta);
        assertEquals(2.1, interpreter.evaluate("var a = 1.1-(2.1-3.1);").get("a"), delta);
        assertEquals(-4.1, interpreter.evaluate("var a = (1.1-2.1)-3.1;").get("a"), delta);
        assertEquals(-13.0, interpreter.evaluate("var a = 1-2-3-4-5;").get("a"), delta);
    }

    @Test
     void testMultiplication() {
        assertEquals(2.0, interpreter.evaluate("var a = 1*2;").get("a"), delta);
        assertEquals(2.0, interpreter.evaluate("var a = 2*1;").get("a"), delta);
        assertEquals(6.0, interpreter.evaluate("var a = 1*(2*3);").get("a"), delta);
        assertEquals(6.0, interpreter.evaluate("var a = (1*2)*3;").get("a"), delta);
        assertEquals(2.31, interpreter.evaluate("var a = 1.1*2.1;").get("a"), delta);
        assertEquals(2.31, interpreter.evaluate("var a = 2.1*1.1;").get("a"), delta);
        assertEquals(7.161, interpreter.evaluate("var a = 1.1*(2.1*3.1);").get("a"), delta);
        assertEquals(7.161, interpreter.evaluate("var a = (1.1*2.1)*3.1;").get("a"), delta);
        assertEquals(120.0, interpreter.evaluate("var a = 1*2*3*4*5;").get("a"), delta);
    }

    @Test
     void testDivision() {
        assertEquals(0.5, interpreter.evaluate("var a = 1/2;").get("a"), delta);
        assertEquals(2.0, interpreter.evaluate("var a = 2/1;").get("a"), delta);
        assertEquals(1.5, interpreter.evaluate("var a = 1/(2/3);").get("a"), delta);
        assertEquals(0.16666666666, interpreter.evaluate("var a = (1/2)/3;").get("a"), delta);
        assertEquals(0.52380952381, interpreter.evaluate("var a = 1.1/2.1;").get("a"), delta);
        assertEquals(1.90909090909, interpreter.evaluate("var a = 2.1/1.1;").get("a"), delta);
        assertEquals(1.62380952381, interpreter.evaluate("var a = 1.1/(2.1/3.1);").get("a"), delta);
        assertEquals(0.16897081413, interpreter.evaluate("var a = (1.1/2.1)/3.1;").get("a"), delta);
        assertTrue(Double.isInfinite(interpreter.evaluate("var a = 1/0;").get("a")));
        assertEquals(0.00833333333, interpreter.evaluate("var a = 1/2/3/4/5;").get("a"), delta);
    }

    @Test
     void testOrderOfOperations() {
        assertEquals(17.0, interpreter.evaluate("var a = 15+5-3;").get("a"), delta);
        assertEquals(30.0, interpreter.evaluate("var a = 15+5*3;").get("a"), delta);
        assertEquals(16.66666666667, interpreter.evaluate("var a = 15+5/3;").get("a"), delta);

        assertEquals(13.0, interpreter.evaluate("var a = 15-5+3;").get("a"), delta);
        assertEquals(0.0, interpreter.evaluate("var a = 15-5*3;").get("a"), delta);
        assertEquals(13.3333333333, interpreter.evaluate("var a = 15-5/3;").get("a"), delta);

        assertEquals(78.0, interpreter.evaluate("var a = 15*5+3;").get("a"), delta);
        assertEquals(72.0, interpreter.evaluate("var a = 15*5-3;").get("a"), delta);
        assertEquals(25.0, interpreter.evaluate("var a = 15*5/3;").get("a"), delta);

        assertEquals(6.0, interpreter.evaluate("var a = 15/5+3;").get("a"), delta);
        assertEquals(0.0, interpreter.evaluate("var a = 15/5-3;").get("a"), delta);
        assertEquals(9.0, interpreter.evaluate("var a = 15/5*3;").get("a"), delta);
    }

    @Test
     void testMixedOperations() {
        assertEquals(454.0, interpreter.evaluate("var a = 15*2*(6*2+3)+4;").get("a"), delta);
        assertEquals(36.0, interpreter.evaluate("var a = (1+2)*3*4;").get("a"), delta);
        assertEquals(36.0, interpreter.evaluate("var a = (((1.0+2)))*((3.0*4));").get("a"), delta);
        assertEquals(5.0, interpreter.evaluate("var a = 1+2*3-4/2;").get("a"), delta);
        assertEquals(2.0, interpreter.evaluate("var a = 1*2+4/2-2;").get("a"), delta);
        assertEquals(15.0, interpreter.evaluate("var a = 1+2+3-4+5*3-4/2;").get("a"), delta);
        assertEquals(-4.0, interpreter.evaluate("var a = 1*2+3-4-5+4/2-2;").get("a"), delta);
    }

    @Test
     void testVariableUsage() {
        Map<String, Double> variables = interpreter.evaluate("var a = 5; var b = 4; var c = (a*b)+(a-b)-1.9;");
        assertEquals(5.0, variables.get("a"), delta);
        assertEquals(4.0, variables.get("b"), delta);
        assertEquals(19.1, variables.get("c"), delta);
        assertNull(variables.get("d"));

        assertThrows(IllegalArgumentException.class, () -> {
            interpreter.evaluate("var a = 2; var b = c * d;");
        });
    }
}
