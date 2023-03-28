package com.yai.evaluator;

import com.yai.YaInterpreterBaseVisitor;
import com.yai.YaInterpreterParser;
import com.yai.exception.SequenceEvaluatorException;
import com.yai.sequence.Sequence;
import org.antlr.v4.runtime.tree.ParseTree;

public class SequenceEvaluator extends YaInterpreterBaseVisitor<Sequence<Integer>> {
    private final Sequence<Integer> sequence = new Sequence<>();

    public SequenceEvaluator(ParseTree tree) {
        visit(tree);
    }

    public Sequence<Integer> getSequence() {
        return sequence;
    }

    @Override
    public Sequence<Integer> visitSequence(YaInterpreterParser.SequenceContext ctx) {
        if (!ctx.NUMBER().isEmpty() && ctx.NUMBER().size() == 2) {
            String fromStr = ctx.NUMBER().get(0).toString();
            String toStr = ctx.NUMBER().get(1).toString();
            if (isDouble(fromStr) || isDouble(toStr)) {
                throw new SequenceEvaluatorException("Sequence shouldn't have double");
            }
            int from = Integer.parseInt(fromStr);
            int to = Integer.parseInt(toStr);

            if (to > from ) {
                int n = to - from;
                for (int i = 0; i <= n; i++) {
                    sequence.add(from++);
                }
            }
        }
        return super.visitSequence(ctx);
    }

    private boolean isDouble(String str) {
        return str.equals(Double.toString(Double.parseDouble(str)));
    }

}
