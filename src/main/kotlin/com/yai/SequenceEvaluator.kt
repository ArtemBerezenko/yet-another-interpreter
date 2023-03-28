//package com.yai
//
//import com.yai.YaInterpreterParser.SequenceContext
//import org.antlr.v4.runtime.tree.ParseTree
//
//
//class SequenceEvaluator(private val tree: ParseTree) : YaInterpreterBaseVisitor<List<Int>>() {
//    val sequence = listOf<Int>()
//
//    init {
//        visit(tree)
//    }
//
//    override fun visitStatement(ctx: YaInterpreterParser.StatementContext): List<Int> {
//        for (i in ctx.sequence().indices) {
//            visit(ctx.variable(i) as ParseTree)
//        }
//        return sequence
//    }
//
//    override fun visitSequence(ctx: SequenceContext): List<Int> {
//        if (ctx.INT().isNotEmpty() && ctx.INT().size == 2) {
//            var from = ctx.INT()[0].text.toInt()
//            var to = ctx.INT()[1].text.toInt()
//
//            if (to > from ) {
//                val n = to - from
//                for (i in 0..n) {
//                    sequence[from++]
//                }
//            }
//        }
//        return super.visitSequence(ctx)
//    }
//
//}