package com.yai

import com.yai.exception.SequenceEvaluatorException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class YaInterpreterSequenceTests {
    private lateinit var interpreter: YaInterpreter

    @BeforeEach
    fun setup() {
        interpreter = YaInterpreter()
    }

    @Test
    fun createSequence() {
        val sequence = YaInterpreter().evaluate("var a = {1, 5};")["a"]?.asSequence()
        val expected = listOf(1, 2, 3, 4, 5)
        assertEquals(expected, sequence)
    }

    @Test
    fun createSequenceInvalidInput() {
        assertThrows(SequenceEvaluatorException::class.java) {
            YaInterpreter().evaluate("var a = {1.0, 5.0};")
        }
    }
}