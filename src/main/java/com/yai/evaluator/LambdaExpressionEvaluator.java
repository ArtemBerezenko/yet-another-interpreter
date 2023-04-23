package com.yai.evaluator;

import com.yai.YaInterpreterParser;
import com.yai.util.LexerHandler;
import org.antlr.v4.runtime.tree.ParseTree;

import java.util.Map;

public class LambdaExpressionEvaluator extends ExpressionEvaluator {
    private final Map<String, Integer> lambdaHolder;

    public LambdaExpressionEvaluator(ParseTree tree, Map<String, Integer> lambdaHolder) {
        super();
        this.lambdaHolder = lambdaHolder;
        super.visit(tree);
    }

    @Override
    public Void visitTerm(YaInterpreterParser.TermContext ctx) {
        String type = LexerHandler.lexer.getVocabulary().getSymbolicName(ctx.getStart().getType());
        if ("NUMBER".equals(type)) {
            terms.push(Double.parseDouble(ctx.getText()));
        } else if ("VARIABLE".equals(type)) {
            String variable = ctx.getText();

            if (lambdaHolder.containsKey(variable)) {
                terms.push(Double.valueOf(lambdaHolder.get(variable)));
            } else {
                if (!variables.containsKey(variable) && !lambdaHolder.containsKey(variable)) {
                    throw new IllegalArgumentException("Undefined variable: " + variable);
                }
                terms.push(variables.get(variable).asDouble());
            }

        } else {
            visit(ctx.expression());
        }
        return null;
    }
}