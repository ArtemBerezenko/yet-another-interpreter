//package com.yai
//
//import com.yai.YaInterpreterParser.ProductContext
//import com.yai.YaInterpreterParser.TermContext
//import org.antlr.v4.runtime.tree.ParseTree
//import java.util.Stack
//
//class ExpressionEvaluator(
//    private val lexer: YaInterpreterLexer,
//    private val variables: MutableMap<String, Double>,
//    tree: ParseTree
//) : YaInterpreterBaseVisitor<Void?>() {
//
//    private val terms = Stack<Double?>()
//
//    init {
//        visit(tree)
//    }
//
//    val result: Double?
//        get() = terms.peek()
//
//    override fun visitExpression(ctx: YaInterpreterParser.ExpressionContext): Void?{
//        visit(ctx.product(0))
//        for (i in 1 until ctx.product().size) {
//            visit(ctx.product(i))
//            performOperation(ctx.addOperation(i - 1).text)
//        }
//        return null
//    }
//
//    override fun visitProduct(ctx: ProductContext): Void? {
//        visit(ctx.term(0))
//        for (i in 1 until ctx.term().size) {
//            visit(ctx.term(i))
//            performOperation(ctx.productOperation(i - 1).text)
//        }
//        return null
//    }
//
//    override fun visitTerm(ctx: TermContext): Void? {
//        when (lexer.vocabulary.getSymbolicName(ctx.getStart().type)) {
//            "NUMBER" -> {
//                terms.push(ctx.text.toDouble())
//            }
//            "VARIABLE" -> {
//                val variable = ctx.text
//                require(variables.containsKey(variable)) { "Undefined variable: $variable" }
//                terms.push(variables[variable])
//            }
//            else -> {
//                visit(ctx.expression())
//            }
//        }
//        return null
//    }
//
//    private fun performOperation(operator: String) {
//        val rhs = terms.pop()
//        val lhs = terms.pop()
//        when (operator) {
//            "+" -> {
//                terms.push(lhs!! + rhs!!)
//            }
//            "-" -> {
//                terms.push(lhs!! - rhs!!)
//            }
//            "*" -> {
//                terms.push(lhs!! * rhs!!)
//            }
//            "/" -> {
//                terms.push(lhs!! / rhs!!)
//            }
//            else -> {
//                throw IllegalArgumentException("Unsupported operation: $operator")
//            }
//        }
//    }
//}