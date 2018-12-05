import java.io.IOException;

public class Parser {
   private Lexer lex;
   private Token tok;

   public Parser(Lexer lex) throws IOException {
      this.lex = lex;
      this.tok = lex.getToken();
   }

   private void error(Token.T[] expected) {
      StringBuilder b = new StringBuilder();
      if (expected.length > 0) {
         b.append(expected[0]);
         for (int i = 1; i < expected.length; i++)
            b.append(", ").append(expected[i]);
      }
      ErrorMsg.error(tok.line, tok.col, "syntax error", "expecting " + b.toString() + ", found " + tok.type);
      System.exit(3);
   }

   private void advance() throws IOException {
      tok = lex.getToken();
   }

   private void eat(Token.T t) throws IOException {
      if (tok.type == t)
         advance();
      else
         error(new Token.T[]{t});
   }

   private void S() throws IOException {
      switch (tok.type) {
         case BOOL:
            Program();
            break;
         case INT:
            Program();
            break;
         default:
            error(new Token.T[]{Token.T.BOOL, Token.T.INT});
      }
   }

   private void Program() throws IOException {
      switch (tok.type) {
         case BOOL:
            Funs();
            break;
         case INT:
            Funs();
            break;
         default:
            error(new Token.T[]{Token.T.BOOL, Token.T.INT});
      }
   }

   private void Funs() throws IOException {
      switch (tok.type) {
         case BOOL:
            Fun();
            Funs_Linha();
            break;
         case INT:
            Fun();
            Funs_Linha();
            break;
         default:
            error(new Token.T[]{Token.T.BOOL, Token.T.INT});
      }
   }
   private void Funs_Linha() throws IOException {
      switch (tok.type) {
         case BOOL:
            Funs();
            break;
         case INT:
            Funs();
            break;
         case EOF:
            break;
         default:
            error(new Token.T[]{Token.T.BOOL, Token.T.INT,Token.T.EOF});
      }
   }
   private void Fun() throws IOException {
      switch (tok.type) {
         case BOOL:
            TypeId();
            eat(Token.T.T_PARENTESEL);
            TypeIds();
            eat(Token.T.T_PARENTESER);
            eat(Token.T.T_IGUAL);
            Exp();
            break;
         case INT:
            TypeId();
            eat(Token.T.T_PARENTESEL);
            TypeIds();
            eat(Token.T.T_PARENTESER);
            eat(Token.T.T_IGUAL);
            Exp();
            break;
         default:
            error(new Token.T[]{Token.T.BOOL, Token.T.INT});
      }
   }
   private void TypeId() throws IOException {
      switch (tok.type) {
         case BOOL:
            eat(Token.T.BOOL);
            eat(Token.T.ID);
            break;
         case INT:
            eat(Token.T.INT);
            eat(Token.T.ID);
            break;
         default:
            error(new Token.T[]{Token.T.BOOL, Token.T.INT});
      }
   }
   private void TypeIds() throws IOException {
      switch (tok.type) {
         case BOOL:
            TypeId();
            TypeIds_Linha();
            break;
         case INT:
            TypeId();
            TypeIds_Linha();
            break;
         default:
            error(new Token.T[]{Token.T.BOOL, Token.T.INT});
      }
   }
   private void TypeIds_Linha() throws IOException {
      switch (tok.type) {
         case T_PARENTESER:
            break;
         case T_VIRGULA:
            eat(Token.T.T_VIRGULA);
            TypeIds();
            break;
         default:
            error(new Token.T[]{Token.T.T_PARENTESER, Token.T.T_VIRGULA});
      }
   }
   private void Exp() throws IOException {
      switch (tok.type) {
         case LET:
            eat(Token.T.LET);
            eat(Token.T.ID);
            eat(Token.T.T_IGUAL);
            Exp();
            eat(Token.T.IN);
            Exp();
            break;
         case ID:
            A();
            Exp_Linha();
            break;
         case NUM:
            A();
            Exp_Linha();
            break;
         case IF:
            eat(Token.T.IF);
            Exp();
            eat(Token.T.THEN);
            Exp();
            eat(Token.T.ELSE);
            Exp();
            break;
         default:
            error(new Token.T[]{Token.T.LET, Token.T.ID, Token.T.NUM, Token.T.IF});
      }
   }
   private void Exp_Linha() throws IOException {
      switch (tok.type) {
         case BOOL:
            break;
         case INT:
            break;
         case IN:
            break;
         case THEN:
            break;
         case ELSE:
            break;
         case T_PARENTESER:
            break;
         case T_IGUAL:
            eat(Token.T.T_IGUAL);
            A();
            break;
         case T_VIRGULA:
            break;
         case EOF:
            break;
         default:
            error(new Token.T[]{Token.T.BOOL, Token.T.INT, Token.T.IN, Token.T.THEN,Token.T.ELSE, Token.T.T_PARENTESER, Token.T.T_IGUAL, Token.T.T_VIRGULA, Token.T.EOF });
      }
   }
   private void A() throws IOException {
      switch (tok.type) {
         case ID:
            T();
            A_Linha();
            break;
         case NUM:
            T();
            A_Linha();
            break;
         default:
            error(new Token.T[]{Token.T.ID, Token.T.NUM,});
      }
   }
   private void A_Linha() throws IOException {
      switch (tok.type) {
         case BOOL:
            break;
         case INT:
            break;
         case IN:
            break;
         case THEN:
            break;
         case ELSE:
            break;
         case T_PARENTESER:
            break;
         case T_MAIS:
            eat(Token.T.T_MAIS);
            T();
            A_Linha();
            break;
         case T_IGUAL:
            break;
         case T_VIRGULA:
            break;
         case EOF:
            break;
         default:
            error(new Token.T[]{Token.T.BOOL, Token.T.INT, Token.T.IN, Token.T.THEN,Token.T.ELSE, Token.T.T_PARENTESER, Token.T.T_IGUAL, Token.T.T_VIRGULA, Token.T.EOF });
      }
   }
   private void T() throws IOException {
      switch (tok.type) {
         case ID:
            eat(Token.T.ID);
            T_Linha();
            break;
         case NUM:
            eat(Token.T.NUM);
            break;
         default:
            error(new Token.T[]{Token.T.ID, Token.T.NUM,});
      }
   }
   private void T_Linha() throws IOException {
      switch (tok.type) {
         case BOOL:
            break;
         case INT:
            break;
         case IN:
            break;
         case THEN:
            break;
         case ELSE:
            break;
         case T_PARENTESEL:
            eat(Token.T.T_PARENTESEL);
            Exps();
            eat(Token.T.T_PARENTESER);
            break;
         case T_PARENTESER:
            break;
         case T_MAIS:
            break;
         case T_IGUAL:
            break;
         case T_VIRGULA:
            break;
         case EOF:
            break;
         default:
            error(new Token.T[]{Token.T.BOOL, Token.T.INT, Token.T.IN, Token.T.THEN,Token.T.ELSE, Token.T.T_PARENTESER, Token.T.T_IGUAL, Token.T.T_VIRGULA, Token.T.EOF });
      }
   }
   private void Exps() throws IOException {
      switch (tok.type) {
         case LET:
            Exp();
            Exps_Linha();
            break;
         case ID:
            Exp();
            Exps_Linha();
            break;
         case NUM:
            Exp();
            Exps_Linha();
            break;
         case IF:
            Exp();
            Exps_Linha();
            break;
         default:
            error(new Token.T[]{Token.T.LET, Token.T.ID, Token.T.NUM, Token.T.IF});
      }
   }
   private void Exps_Linha() throws IOException {
      switch (tok.type) {
         case T_PARENTESER:
            break;
         case T_VIRGULA:
            eat(Token.T.T_VIRGULA);
            Exps();
            break;
         default:
            error(new Token.T[]{Token.T.ID, Token.T.NUM,});
      }
   }
   public void parse() throws IOException {
      S();
      eat(Token.T.EOF);
   }
}
