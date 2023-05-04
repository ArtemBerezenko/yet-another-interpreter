package com.yai

import org.junit.jupiter.api.Test

internal class YaInterpreterPrintTest {

    @Test
    fun printOut() {
        val value = YaInterpreter().evaluate("var a = reduce({5, 7}, 1, x y -> x * y)")["a"]?.asInteger()
        YaInterpreter().evaluate("print \"a =\"")
        YaInterpreter().evaluate("out $value")
    }
}