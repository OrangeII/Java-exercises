import java.io.*;
import java.util.*;
public class Parser{

  //parser come soluzione all esercizio 3.1
  //per compilare rinominare in Parser.java

  private Lexer lex;
  private BufferedReader pbr;
  private Token look;
  private Stack<Integer> stack;
  private ParsingTable table ;



  public Parser(Lexer l, BufferedReader br, Stack<Integer> pila){
    lex = l;
    pbr = br;
    stack = pila;
    stack.push(0); //inserisco lo stato iniziale
    //creo la tabella di parsing
    //ACTION
    List<Map<Integer, Azione>> azioni = new ArrayList<Map<Integer, Azione>>();
    azioni.add(new HashMap<Integer, Azione>());
    azioni.get(0).put(Tag.NUM, new Azione(4));
    azioni.get(0).put((int)'(', new Azione(5));
    azioni.add(new HashMap<Integer, Azione>());
    azioni.get(1).put((int)'-', new Azione(7));
    azioni.get(1).put((int)'+', new Azione(6));
    azioni.get(1).put(Tag.EOF, new Azione(-1)); //accettazione
    azioni.add(new HashMap<Integer, Azione>());
    azioni.get(2).put((int)'-', new Azione(2, 1, 'E'));
    azioni.get(2).put((int)'+', new Azione(2, 1, 'E'));
    azioni.get(2).put((int)'/', new Azione(9));
    azioni.get(2).put((int)'*', new Azione(8));
    azioni.get(2).put((int)')', new Azione(2, 1, 'E'));
    azioni.get(2).put(Tag.EOF, new Azione(2, 1, 'E'));
    azioni.add(new HashMap<Integer, Azione>());
    azioni.get(3).put((int)'-', new Azione(5, 1, 'T'));
    azioni.get(3).put((int)'+', new Azione(5, 1, 'T'));
    azioni.get(3).put((int)'/', new Azione(5, 1, 'T'));
    azioni.get(3).put((int)'*', new Azione(5, 1, 'T'));
    azioni.get(3).put((int)')', new Azione(5, 1, 'T'));
    azioni.get(3).put(Tag.EOF, new Azione(5, 1, 'T'));
    azioni.add(new HashMap<Integer, Azione>());
    azioni.get(4).put((int)'-', new Azione(6, 1, 'F'));
    azioni.get(4).put((int)'+', new Azione(6, 1, 'F'));
    azioni.get(4).put((int)'/', new Azione(6, 1, 'F'));
    azioni.get(4).put((int)'*', new Azione(6, 1, 'F'));
    azioni.get(4).put((int)')', new Azione(6, 1, 'F'));
    azioni.get(4).put(Tag.EOF, new Azione(6, 1, 'F'));
    azioni.add(new HashMap<Integer, Azione>());
    azioni.get(5).put(Tag.NUM, new Azione(4));
    azioni.get(5).put((int)'(', new Azione(5));
    azioni.add(new HashMap<Integer, Azione>());
    azioni.get(6).put(Tag.NUM, new Azione(4));
    azioni.get(6).put((int)'(', new Azione(5));
    azioni.add(new HashMap<Integer, Azione>());
    azioni.get(7).put(Tag.NUM, new Azione(4));
    azioni.get(7).put((int)'(', new Azione(5));
    azioni.add(new HashMap<Integer, Azione>());
    azioni.get(8).put(Tag.NUM, new Azione(4));
    azioni.get(8).put((int)'(', new Azione(5));
    azioni.add(new HashMap<Integer, Azione>());
    azioni.get(9).put(Tag.NUM, new Azione(4));
    azioni.get(9).put((int)'(', new Azione(5));
    azioni.add(new HashMap<Integer, Azione>());
    azioni.get(10).put((int)'-', new Azione(7));
    azioni.get(10).put((int)'+', new Azione(6));
    azioni.get(10).put((int)')', new Azione(15));
    azioni.add(new HashMap<Integer, Azione>());
    azioni.get(11).put((int)'-', new Azione(0, 3, 'E'));
    azioni.get(11).put((int)'+', new Azione(0, 3, 'E'));
    azioni.get(11).put((int)')', new Azione(0, 3, 'E'));
    azioni.get(11).put(Tag.NUM, new Azione(0, 3, 'E'));
    azioni.get(11).put((int)'/', new Azione(9));
    azioni.get(11).put((int)'*', new Azione(8));
    azioni.get(11).put(Tag.EOF, new Azione(0, 3, 'E'));
    azioni.add(new HashMap<Integer, Azione>());
    azioni.get(12).put((int)'-', new Azione(1, 3, 'E'));
    azioni.get(12).put((int)'+', new Azione(1, 3, 'E'));
    azioni.get(12).put((int)')', new Azione(1, 3, 'E'));
    azioni.get(12).put(Tag.NUM, new Azione(1, 3, 'E'));
    azioni.get(12).put((int)'/', new Azione(9));
    azioni.get(12).put((int)'*', new Azione(8));
    azioni.get(12).put(Tag.EOF, new Azione(1, 3, 'E'));
    azioni.add(new HashMap<Integer, Azione>());
    azioni.get(13).put((int)'-', new Azione(3, 3, 'T'));
    azioni.get(13).put((int)'+', new Azione(3, 3, 'T'));
    azioni.get(13).put((int)')', new Azione(3, 3, 'T'));
    azioni.get(13).put(Tag.NUM, new Azione(3, 3, 'T'));
    azioni.get(13).put((int)'/', new Azione(3, 3, 'T'));
    azioni.get(13).put((int)'*', new Azione(3, 3, 'T'));
    azioni.get(13).put(Tag.EOF, new Azione(3, 3, 'T'));
    azioni.add(new HashMap<Integer, Azione>());
    azioni.get(14).put((int)'-', new Azione(4, 3, 'T'));
    azioni.get(14).put((int)'+', new Azione(4, 3, 'T'));
    azioni.get(14).put((int)')', new Azione(4, 3, 'T'));
    azioni.get(14).put(Tag.NUM, new Azione(4, 3, 'T'));
    azioni.get(14).put((int)'/', new Azione(4, 3, 'T'));
    azioni.get(14).put((int)'*', new Azione(4, 3, 'T'));
    azioni.get(14).put(Tag.EOF, new Azione(4, 3, 'T'));
    azioni.add(new HashMap<Integer, Azione>());
    azioni.get(15).put((int)'-', new Azione(7, 3, 'F'));
    azioni.get(15).put((int)'+', new Azione(7, 3, 'F'));
    azioni.get(15).put((int)')', new Azione(7, 3, 'F'));
    azioni.get(15).put(Tag.NUM, new Azione(7, 3, 'F'));
    azioni.get(15).put((int)'/', new Azione(7, 3, 'F'));
    azioni.get(15).put((int)'*', new Azione(7, 3, 'F'));
    azioni.get(15).put(Tag.EOF, new Azione(7, 3, 'F'));
    //GOTO
    List<Map<String, Azione>> colonne_goto = new ArrayList<Map<String, Azione>>();
    colonne_goto.add(new HashMap<String, Azione>());
    colonne_goto.get(0).put("E", new Azione(1));
    colonne_goto.get(0).put("T", new Azione(2));
    colonne_goto.get(0).put("F", new Azione(3));
    colonne_goto.add(new HashMap<String, Azione>());
    colonne_goto.add(new HashMap<String, Azione>());
    colonne_goto.add(new HashMap<String, Azione>());
    colonne_goto.add(new HashMap<String, Azione>());
    colonne_goto.add(new HashMap<String, Azione>());
    colonne_goto.get(5).put("E", new Azione(10));
    colonne_goto.get(5).put("T", new Azione(2));
    colonne_goto.get(5).put("F", new Azione(3));
    colonne_goto.add(new HashMap<String, Azione>());
    colonne_goto.get(6).put("T", new Azione(11));
    colonne_goto.get(6).put("F", new Azione(3));
    colonne_goto.add(new HashMap<String, Azione>());
    colonne_goto.get(7).put("T", new Azione(12));
    colonne_goto.get(7).put("F", new Azione(3));
    colonne_goto.add(new HashMap<String, Azione>());
    colonne_goto.get(8).put("F", new Azione(13));
    colonne_goto.add(new HashMap<String, Azione>());
    colonne_goto.get(9).put("F", new Azione(14));
    colonne_goto.add(new HashMap<String, Azione>());
    colonne_goto.add(new HashMap<String, Azione>());
    colonne_goto.add(new HashMap<String, Azione>());
    colonne_goto.add(new HashMap<String, Azione>());
    colonne_goto.add(new HashMap<String, Azione>());
    colonne_goto.add(new HashMap<String, Azione>());

    table = new ParsingTable(azioni, colonne_goto);

  }

  void stampa(){
    System.out.print("Stack: " + Arrays.toString(stack.toArray()));
    //System.out.print("  input: " + )
    System.out.print("  look: " + look + "  ");
  }

  void move(){
    look = lex.lexical_scan(pbr);
    //System.out.println("token = " + look);
  }

  void error(String s){
    throw new Error("near line " + lex.line + ": " + s);
  }


  public void parse(){
    //inizio parsificazione bottom up
    move();
    while (true) {
      stampa();
      int top = stack.peek();
      Azione azione = table.parser_azioni(top, look.tag);
      if (azione != null) {
        if (azione.valore == -1) {
          //accettazione
          System.out.println("Input OK");
          return;
        } else if (azione.tipo == "shift") {
          System.out.println("Applico shift " + azione.valore);
          stack.push(azione.valore);
          move();
        } else if (azione.tipo == "reduce") {
          System.out.println("Applico riduzione " + azione.valore);
          for(int i = 0; i < azione.mag; i++){
            stack.pop(); //faccio pop tante volte quante il mag della riduzione da eseguire
          }
          stampa();
          top = stack.peek();
          Azione a_goto = table.parser_goto(top, azione.nonTerminale);
          if (a_goto != null) {
            stack.push(a_goto.valore);
          } else {
            error("Errore dopo aver applicato la riduzione " + azione.valore);
          }
        }
      } else {
        error("Unexpected symbol " + look);
      }
    }
  }


  //main
  public static void main(String[] args) {
    Lexer lex = new Lexer();
    String path = "test.txt";
    Stack<Integer> pila = new Stack<Integer>();
    try {
      BufferedReader br = new BufferedReader(new FileReader(path));
      Parser parser = new Parser(lex, br, pila);
      parser.parse();
      br.close();
    } catch (IOException e) {e.printStackTrace();}
  }
}
