import java_cup.runtime.Symbol;
import java_cup.runtime.ComplexSymbolFactory;
import java_cup.runtime.ComplexSymbolFactory.Location;

%%
%public
%class Lexer
%cup
%implements sym
%char
%line
%column
%type Symbol

%{
    StringBuffer string = new StringBuffer();
    public Lexer(java.io.Reader in, ComplexSymbolFactory sf){
        this(in);
        symbolFactory = sf;
    }
    
    ComplexSymbolFactory symbolFactory;

    private Symbol symbol(String name, int sym) {
        return symbolFactory.newSymbol(name, sym, new Location(yyline+1,yycolumn+1), new Location(yyline+1,yycolumn+yylength()));
    }

    private Symbol symbol(String name, int sym, Object val) {
        Location left = new Location(yyline+1,yycolumn+1);
        Location right= new Location(yyline+1,yycolumn+yylength());
        return symbolFactory.newSymbol(name, sym, left, right,val);
    }
    private Symbol symbol(String name, int sym, Object val,int buflength) {
        Location left = new Location(yyline+1,yycolumn+yylength()-buflength);
        Location right= new Location(yyline+1,yycolumn+yylength());
        return symbolFactory.newSymbol(name, sym, left, right,val);
    }
    private void error(String message) {
        System.out.println("Error at line "+(yyline+1)+", column "+(yycolumn+1)+" : "+message);
    }
%}

%eofval{
     return symbolFactory.newSymbol("EOF", EOF, new Location(yyline+1,yycolumn+1,yychar), new Location(yyline+1,yycolumn+1,yychar+1));
%eofval}

new_line = \r|\n|\r\n
white_space = {new_line} | [ \t\f]
NUMBER = [0-9]+
FLOAT = [0-9]*\.[0-9]+
STRING = \"[^\"]*\"
VAR = [a-zA-Z_$](([a-zA-Z0-9]*)|([\_\-\$]*))
BINOP = \|\||&&
COMP = >=|<=|>|<
EQUALITYCOMP = ==|\!=
TYPE = int|float|string|bool
BOOL = true|false

%%

{white_space}   { }
{new_line}      { return symbol("line_break", LINEBREAK); }
print           { return symbol("print", PRINT); }
read           { return symbol("read", READ); }
if              { return symbol("if", IF); }
else            { return symbol("else", ELSE); }
while           { return symbol("while", WHILE); }
{TYPE}          { return symbol(yytext(), TYPE, yytext()); }
{BOOL}          { return symbol(yytext(), BOOL, yytext()); }

{BINOP}         { return symbol(yytext(), BINOP, yytext()); }
\!              { return symbol("!", BINOPNEG); }
{EQUALITYCOMP}  { return symbol(yytext(), EQUALCOMP, yytext()); }
{COMP}          { return symbol(yytext(), COMP, yytext()); }

{NUMBER}        { return symbol(yytext(), INT, new Integer(Integer.parseInt(yytext()))); }
{FLOAT}         { return symbol(yytext(), FLOAT, Float.parseFloat(yytext())); }
{VAR}           { return symbol(yytext(), ID, yytext()); }

\(              { return symbol("(", LPAR); }
\)              { return symbol(")", RPAR); }
\{              { return symbol("{", LBAR); }
\}              { return symbol("}", RBAR); }
=               { return symbol("=",ASSIGN); }
\+              { return symbol("+",PLUS); }
-               { return symbol("-",MOINS); }
\*              { return symbol("*",MULT); }
\/              { return symbol("/", DIV); }
;               { return symbol(";", SEMI); }

{STRING}        { return symbol(yytext(), STRING, yytext().substring(1, yylength() - 1)); }


<<EOF>>         { return symbol("EOF", EOF); }
[^]             { error("Illegal argument <" + yytext() + ">"); }