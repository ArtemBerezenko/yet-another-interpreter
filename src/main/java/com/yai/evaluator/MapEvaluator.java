package com.yai.evaluator;

import com.yai.YaInterpreterBaseVisitor;
import com.yai.YaInterpreterParser;
import com.yai.sequence.Sequence;
import org.antlr.v4.runtime.tree.ParseTree;

import java.util.Map;

public class MapEvaluator extends YaInterpreterBaseVisitor<Sequence<Integer>> {
    private final Sequence<Integer> sequence = new Sequence<>();

    public MapEvaluator(ParseTree tree) {
        visit(tree);
    }

    public Sequence<Integer> getSequence() {
        return sequence;
    }

    @Override
    public Sequence<Integer> visitMap(YaInterpreterParser.MapContext ctx) {
        Sequence<Integer> source = new SequenceEvaluator(ctx).getSequence();
        String lambdaKey = ctx.args.getText();

        for (Integer i : source) {
            Double result = new LambdaExpressionEvaluator(ctx.expression(), Map.of(lambdaKey, i)).getResult();
            sequence.add(result.intValue());
        }

        return sequence;
    }
}
