import java.util.Formatter;

public class Token {
   public enum T {NUM,BOOL,INT,IF, THEN, ELSE,LET,IN,ID,T_MAIS,T_IGUAL,T_PARENTESEL,T_PARENTESER,T_VIRGULA,EOF}

   public T type;
   public Object val;
   public int line;
   public int col;

   public Token(T type, Object val, int line, int col) {
      this.type = type;
      this.val = val;
      this.line = line;
      this.col = col;
   }

   public Token(T type, int line, int col) {
      this(type, null, line, col);
   }

   public String toString() {
      Formatter out = new Formatter();
      out.format("(%4d,%4d) %s", line, col, type);
      if (val != null)
         out.format(" [%s]", val);
      return out.toString();
   }
}
