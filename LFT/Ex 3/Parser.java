import java.io.*;

public class Parser{

  //parser come soluzione all esercizio 3.2*
  //*il parser ha un artificio nella funzione di derivazione di S per sopperire
  //all'ambiguità per la derivazione di if then o if then else
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
    } else error("syntax error: " + look + " " + t + " Expected ");
  }

  private void prog(){
    if (look.tag == Tag.ID ||
        look.tag == Tag.NUM ||
        look.tag == Tag.PRINT ||
        look.tag == Tag.READ ||
        look.tag == Tag.IF ||
        look.tag == Tag.FOR ||
        look.tag == Tag.BEGIN ) {
      statlist();
      match(Tag.EOF);
    } else {
        error("Unexpected symbol: " + look);
    }
  }
  //funzioni di produzione per i nonterminali della grammatica
  private void statlist(){
    if (look.tag == Tag.ID ||
        look.tag == Tag.NUM ||
        look.tag == Tag.PRINT ||
        look.tag == Tag.READ ||
        look.tag == Tag.IF ||
        look.tag == Tag.FOR ||
        look.tag == Tag.BEGIN ) {
      stat();
      statlistp();
    } else {
        error("Unexpected symbol: " + look);
    }
  }
  private void statlistp(){
    if(look.tag == (int) ';'){
      match(';');
      stat();
      statlistp();
    } else if (look.tag == Tag.END) {
      //epsilon
    } else if (look.tag == Tag.EOF){
      //epsilon
    } else {
      error("Unexpected symbol: " + look);
    }
  }

  private void stat(){
    if(look.tag == Tag.PRINT){
      match(Tag.PRINT);
      match('(');
      expr();
      match(')');
    } else if (look.tag == Tag.READ){
      match(Tag.READ);
      match('(');
      match(Tag.ID);
      match(')');
    } else if (look.tag == Tag.IF) {
      match(Tag.IF);
      bexpr();
      match(Tag.THEN);
      stat();
      //artificio:
      //questo if non ci potrebbe essere in un parser LL(1), perchè sto guardando 1 simbolo in più 
      //per decidere se devo matchare else
      //se non posso cambiare la grammatica devo fare così
      if(look.tag == Tag.ELSE){
        match(Tag.ELSE);
        stat();
      }
    } else if (look.tag == Tag.ID) {
      match(Tag.ID);
      match('=');
      expr();
    } else if (look.tag == Tag.FOR) {
      match(Tag.FOR);
      match('(');
      match(Tag.ID);
      match('=');
      expr();
      match(';');
      bexpr();
      match(')');
      match(Tag.DO);
      stat();
    } else if (look.tag == Tag.BEGIN) {
      match(Tag.BEGIN);
      statlist();
      match(Tag.END);
    } else {
      error("Unexpected symbol: " + look);
    }
  }


  private void bexpr(){
    if (look.tag == ((int) '(') || look.tag == Tag.NUM || look.tag == Tag.ID) {
      expr();
      //devo matchare relop = {<, <>, <=, >, >=}
      match(Tag.RELOP);
      expr();
    } else {
      error("Unexpected symbol: " + look);
    }
  }

  private void expr(){
    if (look.tag == ((int) '(') || look.tag == Tag.NUM || look.tag == Tag.ID) {
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
      error("Unexpected symbol: " + look);
    }
  }

  private void term(){
    if (look.tag == ((int) '(') || look.tag == Tag.NUM || look.tag == Tag.ID) {
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
    } else if (look.tag == Tag.ID) {
      match(Tag.ID);
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
      parser.prog();
      System.out.println("Input OK");
      br.close();
    } catch (IOException e) {e.printStackTrace();}
  }
}
