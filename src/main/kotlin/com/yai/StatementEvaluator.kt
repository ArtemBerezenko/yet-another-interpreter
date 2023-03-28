//package com.yai
//
//import com.yai.YaInterpreterParser.StatementContext
//import com.yai.YaInterpreterParser.VariableContext
//import org.antlr.v4.runtime.tree.ParseTree
//
//class StatementEvaluator(
//    private val lexer: YaInterpreterLexer,
//    tree: ParseTree?
//) : YaInterpreterBaseVisitor<Map<String, Double>>() {
//
//    private val variables: MutableMap<String, Double> = LinkedHashMap()
//    private var currentVariable: String = ""
//
//    init {
//        visit(tree)
//    }
//
//    fun getVariables(): Map<String, Double> {
//        return variables
//    }
//
//    override fun visitStatement(ctx: StatementContext?): Map<String, Double> {
//        for (i in ctx!!.expression().indices) {
//            visit(ctx.variable(i) as ParseTree)
//            val result = ExpressionEvaluator(lexer, variables, ctx.expression(i) as ParseTree).result as Double
//            variables[currentVariable] = result
//        }
//        return variables
//    }
//
//    override fun visitVariable(ctx: VariableContext?): Map<String, Double> {
//        currentVariable = ctx!!.text
//        return variables
//    }
//
//}