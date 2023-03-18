package com.yai;

import org.antlr.v4.runtime.BailErrorStrategy;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import java.util.Map;


public class YaInterpreter {

	public Map<String, Double> evaluate(String statement) {
		YaInterpreterLexer lexer = new YaInterpreterLexer(CharStreams.fromString(statement));
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		YaInterpreterParser parser = new YaInterpreterParser(tokens);
		parser.setErrorHandler(new BailErrorStrategy());
		ParseTree tree = parser.statement();
		return new StatementEvaluator(lexer, tree).getVariables();
	}
}
