package com.yai.evaluator;

import com.yai.YaInterpreterBaseVisitor;
import com.yai.YaInterpreterParser;
import com.yai.model.Value;
import com.yai.util.LexerHandler;
import org.antlr.v4.runtime.tree.ParseTree;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class ExpressionEvaluator extends YaInterpreterBaseVisitor<Void> {
    protected Map<String, Value> variables;
    protected final Stack<Double> terms = new Stack<>();

    public ExpressionEvaluator() {
        this.variables = new HashMap<>();
    }


    public ExpressionEvaluator(Map<String, Value> variables, ParseTree tree) {
        this.variables = variables;
        visit(tree);
    }

    public Double getResult() {
        return terms.peek();
    }

    @Override
    public Void visitExpression(YaInterpreterParser.ExpressionContext ctx) {
        visit(ctx.product(0));
        for (int i = 1; i < ctx.product().size(); i++) {
            visit(ctx.product(i));
            performOperation(ctx.addOperation(i - 1).getText());
        }
        return null;
    }

    @Override
    public Void visitProduct(YaInterpreterParser.ProductContext ctx) {
        visit(ctx.term(0));
        for (int i = 1; i < ctx.term().size(); i++) {
            visit(ctx.term(i));
            performOperation(ctx.productOperation(i - 1).getText());
        }
        return null;
    }

    @Override
    public Void visitTerm(YaInterpreterParser.TermContext ctx) {
        String type = LexerHandler.lexer.getVocabulary().getSymbolicName(ctx.getStart().getType());
        if ("NUMBER".equals(type)) {
            terms.push(Double.parseDouble(ctx.getText()));
        } else if ("VARIABLE".equals(type)) {
            String variable = ctx.getText();
            if (!variables.containsKey(variable)) {
                throw new IllegalArgumentException("Undefined variable: " + variable);
            }
            terms.push(variables.get(variable).asDouble());
        } else {
            visit(ctx.expression());
        }
        return null;
    }

    private void performOperation(String operator) {
        Double rhs = terms.pop();
        Double lhs = terms.pop();
        if ("+".equals(operator)) {
            terms.push(lhs + rhs);
        } else if ("-".equals(operator)) {
            terms.push(lhs - rhs);
        } else if ("*".equals(operator)) {
            terms.push(lhs * rhs);
        } else if ("/".equals(operator)) {
            terms.push(lhs / rhs);
        } else {
            throw new IllegalArgumentException("Unsupported operation: " + operator);
        }
    }
}