//package com.yai
//
//import org.antlr.v4.runtime.BailErrorStrategy
//import org.antlr.v4.runtime.CharStreams
//import org.antlr.v4.runtime.CommonTokenStream
//import org.antlr.v4.runtime.tree.ParseTree
//
//class YaInterpreter {
//
//    fun evaluate(statement: String?): Map<String, Double> {
//        val lexer = YaInterpreterLexer(CharStreams.fromString(statement))
//        val tokens = CommonTokenStream(lexer)
//        val parser = YaInterpreterParser(tokens)
//        parser.errorHandler = BailErrorStrategy()
//        val tree: ParseTree = parser.statement() as ParseTree
//        return StatementEvaluator(lexer, tree).getVariables()
//    }
//}