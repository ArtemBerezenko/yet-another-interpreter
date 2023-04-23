package com.yai.evaluator;

import com.yai.YaInterpreterBaseVisitor;
import com.yai.YaInterpreterParser;
import com.yai.sequence.Sequence;
import org.antlr.v4.runtime.tree.ParseTree;

import java.util.Map;

public class ReduceEvaluator extends YaInterpreterBaseVisitor<Void> {
    private Integer result;

    public ReduceEvaluator(ParseTree tree) {
        visit(tree);
    }

    public Integer getResult() {
        return result;
    }

    @Override
    public Void visitReduce(YaInterpreterParser.ReduceContext ctx) {
        Sequence<Integer> source = new SequenceEvaluator(ctx).getSequence();
        var iterator = source.iterator();


        String key1 = ctx.arg1.getText();
        String key2 = ctx.arg2.getText();
        int accumulator = Integer.parseInt(ctx.NUMBER().getText());

        while (iterator.hasNext()) {
            Map<String, Integer> args = Map.of(key1, accumulator, key2, iterator.next());
            accumulator = new LambdaExpressionEvaluator(ctx.expression(), args).getResult().intValue();

        }
        this.result = accumulator;

        return null;
    }
}