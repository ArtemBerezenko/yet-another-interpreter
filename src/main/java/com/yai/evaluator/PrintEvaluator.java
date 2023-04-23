package com.yai.evaluator;

import com.yai.YaInterpreterBaseVisitor;
import com.yai.YaInterpreterParser;
import org.antlr.v4.runtime.tree.ParseTree;

public class PrintEvaluator extends YaInterpreterBaseVisitor<Void> {
    private String value;

    public PrintEvaluator(ParseTree tree) {
        visit(tree);
    }

    public void println() {
        System.out.println(value);
    }

    public void print() {
        System.out.print(value);
    }

    @Override
    public Void visitPrint(YaInterpreterParser.PrintContext ctx) {
        value = ctx.STRING().getText().replace('"', ' ');
        return super.visitPrint(ctx);
    }

    @Override
    public Void visitOut(YaInterpreterParser.OutContext ctx) {
        value = ctx.expression().getText();
        return super.visitOut(ctx);
    }
}