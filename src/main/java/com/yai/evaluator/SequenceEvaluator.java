package com.yai.evaluator;

import com.yai.YaInterpreterBaseVisitor;
import com.yai.YaInterpreterParser;
import com.yai.exception.SequenceEvaluatorException;
import org.antlr.v4.runtime.tree.ParseTree;

import java.util.ArrayList;
import java.util.List;

public class SequenceEvaluator extends YaInterpreterBaseVisitor<List<Integer>> {
    private final List<Integer> sequence = new ArrayList<>();
    private static final String DOUBLE = "[0-9]{1,13}(\\.[0-9]+)?";

    public SequenceEvaluator(ParseTree tree) {
        visit(tree);
    }

    public List<Integer> getSequence() {
        return sequence;
    }

    @Override
    public List<Integer> visitSequence(YaInterpreterParser.SequenceContext ctx) {
        if (!ctx.NUMBER().isEmpty() && ctx.NUMBER().size() == 2) {
            String fromStr = ctx.NUMBER().get(0).toString();
            String toStr = ctx.NUMBER().get(1).toString();
            if (fromStr.matches(DOUBLE) || toStr.matches(DOUBLE)) {
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

}
