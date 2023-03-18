package com.yai;

import org.antlr.v4.runtime.tree.ParseTree;

import java.util.LinkedHashMap;
import java.util.Map;

public class StatementEvaluator extends YaInterpreterBaseVisitor<Map<String, Double>> {

    private final YaInterpreterLexer lexer;
    private final Map<String, Double> variables = new LinkedHashMap<>();

    private String currentVariable = null;

    public StatementEvaluator(YaInterpreterLexer lexer, ParseTree tree) {
        this.lexer = lexer;
        visit(tree);
    }

    public Map<String, Double> getVariables() {
        return variables;
    }


    public Map<String, Double> visitStatement(YaInterpreterParser.StatementContext ctx) {
        for (int i = 0; i < ctx.expression().size(); i++) {
            visit(ctx.variable(i));
            double result = new ExpressionEvaluator(lexer, variables, ctx.expression(i)).getResult();
            variables.put(currentVariable, result);
        }
        return variables;
    }

    @Override
    public Map<String, Double> visitVariable(YaInterpreterParser.VariableContext ctx) {
        currentVariable = ctx.getText();
        return variables;
    }
}