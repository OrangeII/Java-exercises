import java.io.*;

public class Valutatore{

  //Valutatore come soluzione all esercizio 4.1
  //per compilare rinominare in Valutatore.java

  private Lexer lex;
  private BufferedReader pbr;
  private Token look;

  public Valutatore(Lexer l, BufferedReader br){
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
    int expr_val;
    if (look.tag == ((int) '(') || look.tag == Tag.NUM) {
      expr_val =expr();
      match(Tag.EOF);
      System.out.println(expr_val);
    } else {
        error("Unexpected symbol: " + look);
    }
  }

  private int expr(){
    int term_val, exprp_i, exprp_val, expr_val;
    if (look.tag == ((int) '(') || look.tag == Tag.NUM) {
      term_val = term();
      exprp_i = term_val;
      exprp_val = exprp(exprp_i);
      expr_val = exprp_val;
    } else {
      error("Unexpected symbol: " + look);
      return 0;
    }
    return expr_val;
  }

  private int exprp(int exprp_i){
    int exprp1_i, exprp1_val, term_val, exprp_val;
    if (look.tag == (int) '-') {
      match((int) '-');
      term_val = term();
      exprp1_i = exprp_i - term_val;
      exprp1_val = exprp(exprp1_i);
      exprp_val = exprp1_val;
    } else if (look.tag == (int) '+') {
      match((int) '+');
      term_val = term();
      exprp1_i = exprp_i + term_val;
      exprp1_val = exprp(exprp1_i);
      exprp_val = exprp1_val;
    } else if (look.tag == (int) ')') {
      //match((int) ')'); //Non devo matcharli qui perche è una derivazione a epsilon
      exprp_val = exprp_i;
    } else if (look.tag == Tag.EOF) {
      //match(Tag.EOF); ////Non devo matcharli qui perche è una derivazione a epsilon
      exprp_val = exprp_i;
    } else {
      error("Unexpected symbol: " + look);
      return 0;
    }
    return exprp_val;
  }

  private int term(){
    int term_val, fact_val, termp_i, termp_val;
    if (look.tag == ((int) '(') || look.tag == Tag.NUM) {
      fact_val = fact();
      termp_i = fact_val;
      termp_val = termp(termp_i);
      term_val = termp_val;
    } else {
      error("Unexpected symbol: " + look);
      return 0;
    }
    return term_val;
  }

  private int termp(int termp_i){
    int termp_val, termp1_i, termp1_val, fact_val;
    if (look.tag == (int) '-') {
      //match((int) '-'); //Non devo matcharli qui perche è una derivazione a epsilon
      termp_val = termp_i;
    } else if (look.tag == (int) '+') {
      //match((int) '+'); //Non devo matcharli qui perche è una derivazione a epsilon
      termp_val = termp_i;
    } else if (look.tag == (int) ')') {
      //match((int) ')'); //Non devo matcharli qui perche è una derivazione a epsilon
      termp_val = termp_i;
    } else if (look.tag == (int) '*') {
      match((int) '*');
      fact_val = fact();
      termp1_i = termp_i * fact_val;
      termp1_val = termp(termp1_i);
      termp_val = termp1_val;
    } else if (look.tag == (int) '/') {
      match((int) '/');
      fact_val = fact();
      termp1_i = termp_i / fact_val;
      termp1_val = termp(termp1_i);
      termp_val = termp1_val;
    } else if (look.tag == Tag.EOF) {
      //match(Tag.EOF); //Non devo matcharli qui perche è una derivazione a epsilon
      termp_val = termp_i;
    } else {
      error("Unexpected symbol: " + look);
      return 0;
    }
    return termp_val;
  }

  private int fact(){
    int fact_val, expr_val, num_val;
    if(look.tag == (int) '('){
      match((int) '(');
      expr_val = expr();
      fact_val = expr_val;
      match((int) ')');
    } else if (look.tag == Tag.NUM) {
      num_val = ((NumberTok)look).lexeme;
      match(Tag.NUM);
      fact_val = num_val;
    } else {
      error("Unexpected symbol: " + look);
      return 0;
    }
    return fact_val;
  }

  //main
  public static void main(String[] args) {
    Lexer lex = new Lexer();
    String path = "test.txt";
    try {
      BufferedReader br = new BufferedReader(new FileReader(path));
      Valutatore valutatore = new Valutatore(lex, br);
      valutatore.start();
      System.out.println("Input OK");
      br.close();
    } catch (IOException e) {e.printStackTrace();}
  }
}
