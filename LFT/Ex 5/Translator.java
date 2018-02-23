import java.io.*;

public class Translator{

  //Translator come soluzione all esercizio 5.2*
  //*il Translator ha un artificio nella funzione di derivazione di S per sopperire
  //all'ambiguità per la derivazione di if then o if then else
  //per compilare rinominare in Translator.java

  private Lexer lex;
  private BufferedReader pbr;
  private Token look;
  private Token precedente;
  SymbolTable st = new SymbolTable();
  CodeGenerator code = new CodeGenerator();
  int count=0;

  public Translator(Lexer l, BufferedReader br){
    lex = l;
    pbr = br;
    move();
  }

  void move(){
    precedente = look;
    look = lex.lexical_scan(pbr);
    System.out.println("token = " + look);
  }

  void error(String s){
    throw new Error("near line " + lex.line + ": " + s);
  }


  //siccome c'è move() alla fine, lo uso solo per matchare i terminali,
  //ovvero quando trovo un terminale in una produzione e devo dunque avanzare l'input
  //quando devo matchare un nonterminale mi basta chiamare la sua funzione di produzione
  void match(int t){
    if (look.tag == t){
      if (look.tag != Tag.EOF) move();
    } else error("syntax error: Expected " + t + " but found " + look + " after " + precedente );
  }

  //funzioni di produzione per i nonterminali della grammatica
  private void prog(){
    if (look.tag == Tag.ID ||
        look.tag == Tag.NUM ||
        look.tag == Tag.PRINT ||
        look.tag == Tag.READ ||
        look.tag == Tag.IF ||
        look.tag == Tag.FOR ||
        look.tag == Tag.BEGIN ) {
      int sl_next = code.newLabel();
      statlist(sl_next);
      code.emitLabel(sl_next);
      match(Tag.EOF);
      try {
        code.toJasmin();
      } catch (java.io.IOException e) {
        System.out.println("IO error\n");
        e.printStackTrace();
      }
    } else {
        error("(progr) Unexpected symbol: " + look + " after " + precedente);
    }
  }
  private void statlist(int sl_next){
    if (look.tag == Tag.ID ||
        look.tag == Tag.NUM ||
        look.tag == Tag.PRINT ||
        look.tag == Tag.READ ||
        look.tag == Tag.IF ||
        look.tag == Tag.FOR ||
        look.tag == Tag.BEGIN ) {
      int s_next = code.newLabel();
      stat(s_next);
      code.emitLabel(s_next);
      int slp_next = sl_next;
      statlistp(slp_next);
    } else {
        error("(statilist) Unexpected symbol: " + look + " after " + precedente);
    }
  }
  private void statlistp(int slp_next){
    if(look.tag == (int) ';'){
      match(';');
      int s_next = code.newLabel();
      stat(s_next);
      code.emitLabel(s_next);
      int slp1_next = slp_next;
      statlistp(slp1_next);
    } else if (look.tag == Tag.END) {
      //epsilon
    } else if (look.tag == Tag.EOF){
      //epsilon
    } else {
      error("(statlistp) Unexpected symbol: " + look + " after " + precedente);
    }
  }

  private void stat(int s_next){
    if(look.tag == Tag.PRINT){
      match(Tag.PRINT);
      match('(');
      expr();
      code.emit(OpCode.invokestatic, 1);
      match(')');
    } else if (look.tag == Tag.READ){
      match(Tag.READ);
      match('(');
      if (look.tag == Tag.ID) {
        int read_id_addr = st.lookupAddress(((Word) look).lexeme);
        if (read_id_addr == -1) {
          //se non ho già letto/dichiarato la variabile
          read_id_addr = count;
          st.insert(((Word) look).lexeme, count++);
        }
        match(Tag.ID);
        match(')');
        code.emit(OpCode.invokestatic, 0);
        code.emit(OpCode.istore, read_id_addr);
      } else {
        error("Error in grammar (stat) after reading( with " + look);
      }
    } else if (look.tag == Tag.IF) {
      match(Tag.IF);
      int b_true = code.newLabel();
      int b_false = code.newLabel();
      bexpr(b_true, b_false);
      code.emitLabel(b_true);
      match(Tag.THEN);
      int s1_next = s_next;
      stat(s1_next);
      //artificio:
      //questo if non ci potrebbe essere in un Translator LL(1), perchè sto guardando 1 simbolo in più
      //per decidere se devo matchare else
      //se non posso cambiare la grammatica devo fare così
      if(look.tag == Tag.ELSE){
        code.emit(OpCode.GOto, s1_next);
        match(Tag.ELSE);
        code.emitLabel(b_false);
        int s2_next = s_next;
        stat(s2_next);
      }
    } else if (look.tag == Tag.ID) {
      int id_addr = st.lookupAddress(((Word) look).lexeme);
      if (id_addr == -1) {
        //se non ho già letto/dichiarato la variabile
        id_addr = count;
        st.insert(((Word) look).lexeme, count++);
      }
      match(Tag.ID);
      match('=');
      expr();
      code.emit(OpCode.istore, id_addr);
    } else if (look.tag == Tag.FOR) {
      /*
      S -> for(ID = E{emit(istore(addr(ID.lessema)))} ; {begin=newlabel(), emitlabel(begin), B.true=newlabel( ), B.false=S.next}B {emitlabel(B.true))} do {S1.next= begin} S1 {emit(‘goto’S1.next)}
      */
      //l'incremento del contatore ID avviente implicitamente dopo l'esecuzione di S
      match(Tag.FOR);
      match('(');
      int id_addr = st.lookupAddress(((Word) look).lexeme);
      if (id_addr == -1) {
        //se non ho già letto/dichiarato la variabile
        id_addr = count;
        st.insert(((Word) look).lexeme, count++);
      }
      match(Tag.ID);
      match('=');
      expr();
      code.emit(OpCode.istore, id_addr);
      match(';');
      int begin = code.newLabel();
      code.emitLabel(begin);
      int b_true = code.newLabel();
      int b_false = s_next;
      bexpr(b_true, b_false);
      code.emitLabel(b_true);
      match(')');
      match(Tag.DO);
      int incremento = code.newLabel();
      int s1_next = incremento;
      stat(s1_next);
      code.emit(OpCode.GOto, s1_next);
      //istruzioni per incrementare il contatore
      code.emitLabel(incremento);
      code.emit(OpCode.iload, id_addr);
      code.emit(OpCode.ldc, 1);
      code.emit(OpCode.iadd);
      code.emit(OpCode.istore, id_addr);
      //fine incremento contatore
      code.emit(OpCode.GOto, begin);
    } else if (look.tag == Tag.BEGIN) {
      match(Tag.BEGIN);
      int sl_next = s_next;
      statlist(sl_next);
      match(Tag.END);
    } else {
      error("(stat) Unexpected symbol: " + look+ " after " + precedente);
    }
  }


  private void bexpr(int b_true, int b_false){
    if (look.tag == ((int) '(') || look.tag == Tag.NUM || look.tag == Tag.ID) {
      expr();
      //devo matchare relop = {<, <>, <=, >, >=}
      OpCode istruzione = OpCode.if_icmplt;
      switch (((Word) look).lexeme){
        case "<":
          istruzione = OpCode.if_icmplt;
          break;
        case ">":
          istruzione = OpCode.if_icmpgt;
          break;
        case "<=":
          istruzione = OpCode.if_icmple;
          break;
        case ">=":
          istruzione = OpCode.if_icmpge;
          break;
        case "==":
          istruzione = OpCode.if_icmpeq;
          break;
        case "<>":
          istruzione = OpCode.if_icmpne;
          break;
        default:
          error("Unexpected symbol: " + look + " Expected RELOP");
      }
      match(Tag.RELOP);
      expr();
      code.emit(istruzione, b_true);
      code.emit(OpCode.GOto, b_false);
    } else {
      error("(bexpr) Unexpected symbol: " + look + " after " + precedente);
    }
  }

  private void expr(){
    if (look.tag == ((int) '(') || look.tag == Tag.NUM || look.tag == Tag.ID) {
      term();
      exprp();
    } else {
      error("(expr) Unexpected symbol: " + look + " after " + precedente);
    }
  }

  private void exprp(){
    if (look.tag == (int) '-') {
      match((int) '-');
      term();
      code.emit(OpCode.isub);
      exprp();
    } else if (look.tag == (int) '+') {
      match((int) '+');
      term();
      code.emit(OpCode.iadd);
      exprp();
    } else if (look.tag == (int) ')') {
      //match((int) ')'); //Non devo matcharli qui perche è una derivazione a epsilon
    } else if (look.tag == Tag.EOF) {
      //match(Tag.EOF); ////Non devo matcharli qui perche è una derivazione a epsilon
    } else if(look.tag == Tag.ID ||
        look.tag == Tag.NUM ||
        look.tag == Tag.PRINT ||
        look.tag == Tag.READ ||
        look.tag == Tag.IF ||
        look.tag == Tag.THEN ||
        look.tag == Tag.FOR ||
        look.tag == Tag.BEGIN ||
        look.tag == Tag.ELSE ||
        look.tag == Tag.END ||
        look.tag == Tag.RELOP ||
        look.tag == (char) ';') {
          if (look.tag == (char) ';'){
            //epsilon
          }
          if (look.tag == Tag.END){
            //epsilon
          }
      //epsilon
    } else {
      error("(exprp) Unexpected symbol: " + look + " after " + precedente);
    }
  }

  private void term(){
    if (look.tag == ((int) '(') || look.tag == Tag.NUM || look.tag == Tag.ID) {
      fact();
      termp();
    } else {
      error("(term) Unexpected symbol: " + look + " after " + precedente);
    }
  }

  private void termp(){
    if (look.tag == (int) '-') {
      //match((int) '-'); //Non devo matcharli qui perche è una derivazione a epsilon
    } else if (look.tag == (int) '+') {
      //match((int) '+'); //Non devo matcharli qui perche è una derivazione a epsilon
    } else if (look.tag == (int) ')') {
      //match((int) ')'); //Non devo matcharli qui perche è una derivazione a epsilon
    } else if (look.tag == (int) '*') {
      match((int) '*');
      fact();
      code.emit(OpCode.imul);
      termp();
    } else if (look.tag == (int) '/') {
      match((int) '/');
      fact();
      code.emit(OpCode.idiv);
      termp();
    } else if (look.tag == Tag.EOF) {
      //match(Tag.EOF); //Non devo matcharli qui perche è una derivazione a epsilon
    } else if(look.tag == Tag.ID ||
        look.tag == Tag.NUM ||
        look.tag == Tag.PRINT ||
        look.tag == Tag.READ ||
        look.tag == Tag.IF ||
        look.tag == Tag.THEN ||
        look.tag == Tag.FOR ||
        look.tag == Tag.BEGIN ||
        look.tag == Tag.ELSE ||
        look.tag == Tag.END ||
        look.tag == Tag.RELOP ||
        look.tag == (char) ';' ||
        look.tag == (char) ')') {
      //epsilon
    } else {
      error("(termp) Unexpected symbol: " + look +  " after " + precedente);
    }
  }

  private void fact(){
    if(look.tag == (int) '('){
      match((int) '(');
      expr();
      match((int) ')');
    } else if (look.tag == Tag.NUM) {
      //lo devo fare prima perchè quando chiamo match l'input avanza
      //in alternativa posso memorizzarlo in una variabile
      code.emit(OpCode.ldc,((NumberTok) look).lexeme);
      match(Tag.NUM);
    } else if (look.tag == Tag.ID) {
      int id_addr = st.lookupAddress(((Word) look).lexeme);
      if (id_addr == -1) {
        error("Undeclared variable: " + ((Word) look).lexeme);
      }
      match(Tag.ID);
      code.emit(OpCode.iload, id_addr);
    } else {
      error("(fact) Unexpected symbol: " + look + " after " + precedente);
    }
  }

  //main
  public static void main(String[] args) {
    Lexer lex = new Lexer();
    String path = "test.p";
    try {
      BufferedReader br = new BufferedReader(new FileReader(path));
      Translator translator = new Translator(lex, br);
      translator.prog();
      System.out.println("Input OK");
      br.close();
    } catch (IOException e) {e.printStackTrace();}
  }
}
