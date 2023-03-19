package com.yai

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class YaInterpreterOperationsTests {
    private lateinit var interpreter: YaInterpreter
    private val delta = 0.001
    
    @BeforeEach
    fun setup() {
        interpreter = YaInterpreter()
    }

    @Test
    fun testInvalidInput() {
        assertThrows(Exception::class.java) { interpreter.evaluate(null) }
        assertThrows(Exception::class.java) {interpreter.evaluate("") }
        assertThrows(Exception::class.java) {interpreter.evaluate("2") }
        assertThrows(Exception::class.java) {interpreter.evaluate("a = abc") }
        assertThrows(Exception::class.java) {interpreter.evaluate("a = 2") }
    }

    @Test
    fun testSingleTerms() {
        assertEquals(1.0,interpreter.evaluate("var a = 1;")["a"]!!, delta)
        assertEquals(10.0,interpreter.evaluate("var a = 10;")["a"]!!, delta)
        assertEquals(1.0,interpreter.evaluate("var a = (1);")["a"]!!, delta)
        assertEquals(10.0,interpreter.evaluate("var a = (10);")["a"]!!, delta)
        assertEquals(10.012,interpreter.evaluate("var a = (10.01200);")["a"]!!, delta)
    }

    @Test
    fun testAddition() {
        assertEquals(3.0,interpreter.evaluate("var a = 1+2;")["a"]!!, delta)
        assertEquals(3.0,interpreter.evaluate("var a = 2+1;")["a"]!!, delta)
        assertEquals(6.0,interpreter.evaluate("var a = 1+(2+3);")["a"]!!, delta)
        assertEquals(6.0,interpreter.evaluate("var a = (1+2)+3;")["a"]!!, delta)
        assertEquals(3.2,interpreter.evaluate("var a = 1.1+2.1;")["a"]!!, delta)
        assertEquals(3.2,interpreter.evaluate("var a = 2.1+1.1;")["a"]!!, delta)
        assertEquals(6.3,interpreter.evaluate("var a = 1.1+(2.1+3.1);")["a"]!!, delta)
        assertEquals(6.3,interpreter.evaluate("var a = (1.1+2.1)+3.1;")["a"]!!, delta)
        assertEquals(15.0,interpreter.evaluate("var a = 1+2+3+4+5;")["a"]!!, delta)
    }

    @Test
    fun testSubtraction() {
        assertEquals(-1.0,interpreter.evaluate("var a = 1-2;")["a"]!!, delta)
        assertEquals(1.0,interpreter.evaluate("var a = 2-1;")["a"]!!, delta)
        assertEquals(2.0,interpreter.evaluate("var a = 1-(2-3);")["a"]!!, delta)
        assertEquals(-4.0,interpreter.evaluate("var a = (1-2)-3;")["a"]!!, delta)
        assertEquals(-1.0,interpreter.evaluate("var a = 1.1-2.1;")["a"]!!, delta)
        assertEquals(1.0,interpreter.evaluate("var a = 2.1-1.1;")["a"]!!, delta)
        assertEquals(2.1,interpreter.evaluate("var a = 1.1-(2.1-3.1);")["a"]!!, delta)
        assertEquals(-4.1,interpreter.evaluate("var a = (1.1-2.1)-3.1;")["a"]!!, delta)
        assertEquals(-13.0,interpreter.evaluate("var a = 1-2-3-4-5;")["a"]!!, delta)
    }

    @Test
    fun testMultiplication() {
        assertEquals(2.0,interpreter.evaluate("var a = 1*2;")["a"]!!, delta)
        assertEquals(2.0,interpreter.evaluate("var a = 2*1;")["a"]!!, delta)
        assertEquals(6.0,interpreter.evaluate("var a = 1*(2*3);")["a"]!!, delta)
        assertEquals(6.0,interpreter.evaluate("var a = (1*2)*3;")["a"]!!, delta)
        assertEquals(2.31,interpreter.evaluate("var a = 1.1*2.1;")["a"]!!, delta)
        assertEquals(2.31,interpreter.evaluate("var a = 2.1*1.1;")["a"]!!, delta)
        assertEquals(7.161,interpreter.evaluate("var a = 1.1*(2.1*3.1);")["a"]!!, delta)
        assertEquals(7.161,interpreter.evaluate("var a = (1.1*2.1)*3.1;")["a"]!!, delta)
        assertEquals(120.0,interpreter.evaluate("var a = 1*2*3*4*5;")["a"]!!, delta)
    }

    @Test
    fun testDivision() {
        assertEquals(0.5,interpreter.evaluate("var a = 1/2;")["a"]!!, delta)
        assertEquals(2.0,interpreter.evaluate("var a = 2/1;")["a"]!!, delta)
        assertEquals(1.5,interpreter.evaluate("var a = 1/(2/3);")["a"]!!, delta)
        assertEquals(0.16666666666,interpreter.evaluate("var a = (1/2)/3;")["a"]!!, delta)
        assertEquals(0.52380952381,interpreter.evaluate("var a = 1.1/2.1;")["a"]!!, delta)
        assertEquals(1.90909090909,interpreter.evaluate("var a = 2.1/1.1;")["a"]!!, delta)
        assertEquals(1.62380952381,interpreter.evaluate("var a = 1.1/(2.1/3.1);")["a"]!!, delta)
        assertEquals(0.16897081413,interpreter.evaluate("var a = (1.1/2.1)/3.1;")["a"]!!, delta)
        Assertions.assertTrue(java.lang.Double.isInfinite(interpreter!!.evaluate("var a = 1/0;")["a"]!!))
        assertEquals(0.00833333333,interpreter.evaluate("var a = 1/2/3/4/5;")["a"]!!, delta)
    }

    @Test
    fun testOrderOfOperations() {
        assertEquals(17.0,interpreter.evaluate("var a = 15+5-3;")["a"]!!, delta)
        assertEquals(30.0,interpreter.evaluate("var a = 15+5*3;")["a"]!!, delta)
        assertEquals(16.66666666667,interpreter.evaluate("var a = 15+5/3;")["a"]!!, delta)
        assertEquals(13.0,interpreter.evaluate("var a = 15-5+3;")["a"]!!, delta)
        assertEquals(0.0,interpreter.evaluate("var a = 15-5*3;")["a"]!!, delta)
        assertEquals(13.3333333333,interpreter.evaluate("var a = 15-5/3;")["a"]!!, delta)
        assertEquals(78.0,interpreter.evaluate("var a = 15*5+3;")["a"]!!, delta)
        assertEquals(72.0,interpreter.evaluate("var a = 15*5-3;")["a"]!!, delta)
        assertEquals(25.0,interpreter.evaluate("var a = 15*5/3;")["a"]!!, delta)
        assertEquals(6.0,interpreter.evaluate("var a = 15/5+3;")["a"]!!, delta)
        assertEquals(0.0,interpreter.evaluate("var a = 15/5-3;")["a"]!!, delta)
        assertEquals(9.0,interpreter.evaluate("var a = 15/5*3;")["a"]!!, delta)
    }

    @Test
    fun testMixedOperations() {
        assertEquals(454.0,interpreter.evaluate("var a = 15*2*(6*2+3)+4;")["a"]!!, delta)
        assertEquals(36.0,interpreter.evaluate("var a = (1+2)*3*4;")["a"]!!, delta)
        assertEquals(36.0,interpreter.evaluate("var a = (((1.0+2)))*((3.0*4));")["a"]!!, delta)
        assertEquals(5.0,interpreter.evaluate("var a = 1+2*3-4/2;")["a"]!!, delta)
        assertEquals(2.0,interpreter.evaluate("var a = 1*2+4/2-2;")["a"]!!, delta)
        assertEquals(15.0,interpreter.evaluate("var a = 1+2+3-4+5*3-4/2;")["a"]!!, delta)
        assertEquals(-4.0,interpreter.evaluate("var a = 1*2+3-4-5+4/2-2;")["a"]!!, delta)
    }

    @Test
    fun testVariableUsage() {
        val variables =interpreter.evaluate("var a = 5; var b = 4; var c = (a*b)+(a-b)-1.9;")
        assertEquals(5.0, variables["a"]!!, delta)
        assertEquals(4.0, variables["b"]!!, delta)
        assertEquals(19.1, variables["c"]!!, delta)
        Assertions.assertNull(variables["d"])
        assertThrows(IllegalArgumentException::class.java) {interpreter.evaluate("var a = 2; var b = c * d;") }
    }
}