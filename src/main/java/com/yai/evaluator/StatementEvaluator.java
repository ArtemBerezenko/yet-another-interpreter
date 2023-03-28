package com.yai.evaluator;

import com.yai.YaInterpreterBaseVisitor;
import com.yai.YaInterpreterLexer;
import com.yai.YaInterpreterParser;
import com.yai.evaluator.ExpressionEvaluator;
import com.yai.model.Value;
import org.antlr.v4.runtime.tree.ParseTree;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class StatementEvaluator extends YaInterpreterBaseVisitor<Map<String, Value>> {

    private final YaInterpreterLexer lexer;
    private final Map<String, Value> variables = new LinkedHashMap<>();

    private String currentVariable = null;

    public StatementEvaluator(YaInterpreterLexer lexer, ParseTree tree) {
        this.lexer = lexer;
        visit(tree);
    }

    public Map<String, Value> getVariables() {
        return variables;
    }

    @Override
    public Map<String, Value> visitStatement(YaInterpreterParser.StatementContext ctx) {
        for (int i = 0; i < ctx.expression().size(); i++) {
            visit(ctx.variable(i));
            double result = new ExpressionEvaluator(lexer, variables, ctx.expression(i)).getResult();
            variables.put(currentVariable, new Value(result));
        }
        for (int i = 0; i < ctx.sequence().size(); i++) {
            visit(ctx.variable(i));
            List<Integer> sequence = new SequenceEvaluator(ctx.sequence(i)).getSequence();
            variables.put(currentVariable, new Value(sequence));
        }
        return variables;
    }

    @Override
    public Map<String, Value> visitVariable(YaInterpreterParser.VariableContext ctx) {
        currentVariable = ctx.getText();
        return variables;
    }
}