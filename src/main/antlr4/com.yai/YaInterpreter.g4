grammar YaInterpreter;

/* PARSER */
statement
	:	(VAR variable ASSIGNMENT expression (NEWLINE | EOF))+
	|   (VAR variable ASSIGNMENT sequence (NEWLINE | EOF))+
	|   (VAR variable ASSIGNMENT map (NEWLINE | EOF))+
	|   (VAR variable ASSIGNMENT reduce (NEWLINE | EOF))+
	|   print
	|   out
	;

out         :   OUT expression  ;
print       :   PRINT STRING ;

reduce      :   'reduce(' sequence ',' NUMBER ',' arg1=VARIABLE+ arg2=VARIABLE+ '->' body=expression ')' ;
map         :   'map(' sequence ',' args=VARIABLE+ '->' body=expression ')' ;
sequence    :   '{' NUMBER ( ',' NUMBER)* '}' ;
variable    :	VARIABLE ;
expression  :	product (addOperation product)* ;
addOperation
	:	ADD
	|	SUBTRACT
	;

product : term (productOperation term)* ;

productOperation
	:	MULTIPLY
	|	DIVIDE
	;

term
	:	NUMBER
	|	VARIABLE
	|	LPAREN expression RPAREN
	;

/* LEXER */
OUT             :   'out'   ;
PRINT           :   'print' ;
SEMI            :	';'     ;
LPAREN          :	'('     ;
RPAREN          :	')'     ;
ADD             :	'+'     ;
SUBTRACT        :	'-'     ;
MULTIPLY        :	'*'     ;
DIVIDE          :	'/'     ;
ASSIGNMENT      :	'='     ;

VAR             : 'var' ;
STRING          : ["] ( ~["\r\n\\] | '\\' ~[\r\n] )* ["]
                ;

VARIABLE        : ALPHA ( ALPHA )* ;

NUMBER          :	('0' .. '9') + ('.' ('0' .. '9') +)? ;

ALPHA           : [a-zA-Z_]+ ;
NEWLINE         : '\n' | '\r' '\n'? ;
WS              : [\t ]+ -> skip ;