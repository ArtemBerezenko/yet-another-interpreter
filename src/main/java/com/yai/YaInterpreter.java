package com.yai;

import com.yai.evaluator.StatementEvaluator;
import com.yai.model.Value;
import org.antlr.v4.runtime.BailErrorStrategy;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import java.util.Map;


public class YaInterpreter {

	public Map<String, Value> evaluate(String statement) {
        YaInterpreterLexer lexer = new YaInterpreterLexer(CharStreams.fromString(statement));
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		YaInterpreterParser parser = new YaInterpreterParser(tokens);
		parser.setErrorHandler(new BailErrorStrategy());
		ParseTree tree = parser.statement();
        System.out.println(tree.toStringTree(parser));
		return new StatementEvaluator(lexer, tree).getVariables();
	}
}