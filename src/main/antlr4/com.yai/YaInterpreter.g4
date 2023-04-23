grammar YaInterpreter;

/* PARSER */
statement
	:	(VAR variable ASSIGNMENT expression SEMI)+
	|   (VAR variable ASSIGNMENT sequence SEMI)+
	|   (VAR variable ASSIGNMENT map SEMI)+
	;

map         : 'map(' sequence ',' args=VARIABLE+ '->' body=expression ')' ;
sequence    : '{' NUMBER ( ',' NUMBER)* '}' ;
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
SEMI            :	';' ;
LPAREN          :	'(' ;
RPAREN          :	')' ;
ADD             :	'+' ;
SUBTRACT        :	'-' ;
MULTIPLY        :	'*' ;
DIVIDE          :	'/' ;
ASSIGNMENT      :	'=' ;

VAR             : 'var' ;
STRING          : ["] ( ~["\r\n\\] | '\\' ~[\r\n] )* ["]
                | ['] ( ~['\r\n\\] | '\\' ~[\r\n] )* [']
                ;

VARIABLE        : ALPHA ( ALPHA )* ;

NUMBER          :	('0' .. '9') + ('.' ('0' .. '9') +)? ;

ALPHA           : [a-zA-Z_]+ ;
WS              : [ \t\r\n]+ -> skip ;