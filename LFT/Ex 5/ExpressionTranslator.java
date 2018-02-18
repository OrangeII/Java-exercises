import java.io.*;

public class ExpressionTranslator {

  //ExpressionTranslator come soluzione all esercizio 5.1
  //per compilare rinominare in ExpressionTranslator.java

  private Lexer lex;
  private BufferedReader pbr;
  private Token look;

  CodeGenerator code = new CodeGenerator();
  int count=0;

  public ExpressionTranslator (Lexer l, BufferedReader br){
    lex = l;
    pbr = br;
    move();
  }

  void move(){
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
    } else error("syntax error: " + look);
  }

  //funzioni di produzione per i nonterminali della grammatica
  public void prog(){
    if (look.tag == Tag.PRINT) {
      match(Tag.PRINT);
      match('(');
      expr();
      code.emit(OpCode.invokestatic,1);
      match(')');
      match(Tag.EOF);
      try {
        code.toJasmin();
      } catch (java.io.IOException e) {
        System.out.println("IO error\n");
        e.printStackTrace();
      }
    } else {
        error("Unexpected symbol: " + look);
    }
  }

  private void expr(){
    if (look.tag == ((int) '(') || look.tag == Tag.NUM) {
      term();
      exprp();
    } else {
      error("Unexpected symbol: " + look);
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
    } else {
      error("Unexpected symbol: " + look);
    }
  }

  private void term(){
    if (look.tag == ((int) '(') || look.tag == Tag.NUM) {
      fact();
      termp();
    } else {
      error("Unexpected symbol: " + look);
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
    } else {
      error("Unexpected symbol: " + look);
    }
  }

  private void fact(){
    if(look.tag == (int) '('){
      match((int) '(');
      expr();
      match((int) ')');
    } else if (look.tag == Tag.NUM) {
      //lo devo fare prima perchè quando chiamo match l'input avanza
      //in alternativa posso memorizzarlo in una variablie
      code.emit(OpCode.ldc,((NumberTok) look).lexeme);
      match(Tag.NUM);
    } else {
      error("Unexpected symbol: " + look);
    }
  }

  //main
  public static void main(String[] args) {
    Lexer lex = new Lexer();
    String path = "test.p";
    try {
      BufferedReader br = new BufferedReader(new FileReader(path));
      ExpressionTranslator expressionTranslator = new ExpressionTranslator(lex, br);
      expressionTranslator.prog();
      System.out.println("Input OK");
      br.close();
    } catch (IOException e) {e.printStackTrace();}
  }
}
