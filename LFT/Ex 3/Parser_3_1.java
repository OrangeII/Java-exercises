import java.io.*;

public class Parser{

  //parser come soluzione all esercizio 3.1
  //per compilare rinominare in Parser.java

  private Lexer lex;
  private BufferedReader pbr;
  private Token look;

  public Parser(Lexer l, BufferedReader br){
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
  public void start(){
    if (look.tag == ((int) '(') || look.tag == Tag.NUM) {
      expr();
      match(Tag.EOF);
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
      exprp();
    } else if (look.tag == (int) '+') {
      match((int) '+');
      term();
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
      termp();
    } else if (look.tag == (int) '/') {
      match((int) '/');
      fact();
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
      match(Tag.NUM);
    } else {
      error("Unexpected symbol: " + look);
    }
  }

  //main
  public static void main(String[] args) {
    Lexer lex = new Lexer();
    String path = "test.txt";
    try {
      BufferedReader br = new BufferedReader(new FileReader(path));
      Parser parser = new Parser(lex, br);
      parser.start();
      System.out.println("Input OK");
      br.close();
    } catch (IOException e) {e.printStackTrace();}
  }
}
