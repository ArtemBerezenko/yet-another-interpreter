grammar YaInterpreter;

/* PARSER */
statement
	:	(VAR variable ASSIGNMENT expression SEMI)+
	;

variable
	:	VARIABLE
	;

expression
	:	product (addOperation product)*
	;

addOperation
	:	ADD
	|	SUBTRACT
	;

product
	:	term (productOperation term)*
	;

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
SEMI
	:	';'
	;

LPAREN
	:	'('
	;

RPAREN
	:	')'
	;

ADD
	:	'+'
	;

SUBTRACT
	:	'-'
	;

MULTIPLY
	:	'*'
	;

DIVIDE
	:	'/'
	;

ASSIGNMENT
	:	'='
	;

NUMBER
	:	('0' .. '9') + ('.' ('0' .. '9') +)?
	;

VAR
    : 'var'
    ;

VARIABLE
    : ALPHA ( ALPHA | NUMBER )*
    ;

DIGIT
    : [0-9]
    ;

ALPHA
    : [a-zA-Z_]+
    ;

WS
    :	[ \r\n\t] + -> skip
    ;