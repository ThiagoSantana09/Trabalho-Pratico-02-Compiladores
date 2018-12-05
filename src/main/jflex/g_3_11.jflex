/* -*-Mode: java-*- */

%%

%public
%class Lexer
%type Token
%function getToken
%line
%column

%{  
    private Token tok(Token.T typ, Object val) {
        return new Token(typ, val, yyline, yycolumn);
    }

    private Token tok(Token.T typ) {
        return tok(typ, null);
    }
    
    private void error(String msg) {
    	ErrorMsg.error(yyline, yycolumn, "lexical error", msg);
    }
    private int commentLevel;
%}

%state COMMENT
%state STR

num    = [0-9]+
id        = [a-zA-Z][a-zA-Z0-9_]*


%%
<YYINITIAL>{
[ \t\f\n\r]+          { /* IGNORA */ }
"%" .*       { /* IGNORA */ }
"{%"         { yybegin(COMMENT); commentLevel = 1; }

{num}     { return tok(Token.T.NUM, yytext()); }

"bool"                  { return tok(Token.T.BOOL); }
"int"                  { return tok(Token.T.INT); }
"if"                  { return tok(Token.T.IF); }
"then"                { return tok(Token.T.THEN); }
"else"                { return tok(Token.T.ELSE); }
"let"                { return tok(Token.T.LET); }
"in"                { return tok(Token.T.IN); }

{id}         { return tok(Token.T.ID, yytext().intern()); }

"+"          { return tok(Token.T.T_MAIS); }
"="          { return tok(Token.T.T_IGUAL); }
"("          { return tok(Token.T.T_PARENTESEL); }
")"          { return tok(Token.T.T_PARENTESER); }
","          { return tok(Token.T.T_VIRGULA); }

<<EOF>>               { return tok(Token.T.EOF); }
}

<COMMENT>{
"{%"         { ++commentLevel; }
"%}"         { if (--commentLevel == 0) yybegin(YYINITIAL); }
[^]          { }
<<EOF>>      { yybegin(YYINITIAL); error("unclosed comment"); }
}

.                     { error("illegal character: " + yytext()); }
