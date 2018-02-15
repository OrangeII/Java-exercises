import java.util.*;
import java.io.*;

public class Lexer{

  //Lexer come soluzione all'esercizio 2.1
  //per compilare rinominare il file "Lexer.java"

  public static int line = 1;
  private char peek = ' '; //gli spazi vengono saltati, quando riconosco un token imposto peek = ' ' così il buffer passerà al prossimo carattere

  private void readch(BufferedReader br){
    try {
      peek = (char) br.read();
    } catch (IOException exc){
      peek = (char) -1; //Errore
    }
  }

  public Token lexical_scan(BufferedReader br){
    while(peek == ' ' || peek == '\t' || peek == '\n' || peek == '\r'){
      if (peek == '\n') line++;
      readch(br);
    }

    switch (peek){
      //singoli caratteri
      case '!':
        peek = ' ';
        return Token.not;
      case '(':
        peek = ' ';
        return Token.lpt;
      case ')':
        peek = ' ';
        return Token.rpt;
      case '+':
        peek = ' ';
        return Token.plus;
      case '-':
        peek = ' ';
        return Token.minus;
      case '*':
        peek = ' ';
        return Token.mult;
      case '/':
        peek = ' ';
        return Token.div;
      case ';':
        peek = ' ';
        return Token.semicolon;
      case '&':
        readch(br);
        if (peek == '&'){
          peek = ' ';
          return Word.and;
        } else {
          System.err.println("Erroneus character after & : " + peek);
            return null;
        }
      case '|':
        readch(br);
        if (peek == '|'){
          peek = ' ';
          return Word.or;
        } else {
          System.err.println("Erroneus character after | : " + peek);
          return null;
        }
      case '<':
        readch(br);
        if (peek == '='){
          peek = ' ';
          return Word.le;
        } else if (peek == '>'){
          peek = ' ';
          return Word.ne;
        } else {
          //peek = ' '; Non assegno peek = ' ' perchè ho già letto il prossimo carattere
          return Word.lt;
        }
      case '>':
        readch(br);
        if (peek == '='){
          peek = ' ';
          return Word.ge;
        } else {
          //peek = ' '; Non assegno peek = ' ' perchè ho già letto il prossimo carattere
          return Word.gt;
        }
      case '=':
        readch(br);
        if (peek == '='){
          peek = ' ';
          return Word.eq;
        } else {
          //peek = ' '; Non assegno peek = ' ' perchè ho già letto il prossimo carattere
          return Token.assign;
        }
      case (char) -1:
        return new Token(Tag.EOF);
      default:{
        if (Character.isLetter(peek)){
          //leggo e concateno finche non trovo qualcosa diverso da [a-zA-z0-9]
          String matchWord = new String(Character.toString(peek));
          readch(br);
          while ((peek >= 'a' && peek <= 'z') || (peek >= 'A' && peek <= 'Z') || (peek >= '0' && peek <= '9')) {
            matchWord = matchWord.concat(Character.toString(peek));
            readch(br);
          }
          //controllo se ho letto una parola chiave
          //altrimenti ho letto un identificatore
          //Non assegno peek = ' ' perchè ho già letto il prossimo carattere
          switch(matchWord){
            case "then":{
              return Word.then;
            }
            case "else":{
              return Word.elsetok;
            }
            case "for":{
              return Word.fortok;
            }
            case "do":{
              return Word.dotok;
            }
            case "print":{
              return Word.print;
            }
            case "if":{
              return Word.iftok;
            }
            case "read":{
              return Word.read;
            }
            case "begin":{
              return Word.begin;
            }
            case "end":{
              return Word.end;
            }
            default:{
              //ho letto un ID
              return new Word(Tag.ID, matchWord);
            }
          }
        }
        else if(Character.isDigit(peek)){
          //La consegna vuole che i numeri siano 0|[1-9][0-9]* Quindi solo interi positivi
          //se ho letto 0 il token è 0
          if (peek == '0') {
            peek = ' ';
            return new NumberTok(Tag.NUM, 0);
          }
          //leggo e concateno finche trovo qualcosa diverso da [0-9]
          //poi converto a int
          String matchNumber = new String(Character.toString(peek));
          readch(br);
          while (peek >= '0' && peek <= '9') {
            matchNumber = matchNumber.concat(Character.toString(peek));
            readch(br);
          }
          //converto a int
          return new NumberTok(Tag.NUM, Integer.parseInt(matchNumber));
        }
      }
    }
    return null;
  }

  public static void main(String[] args) {
    Lexer lex = new Lexer();
    String path = "test.txt";
    try {
      BufferedReader br = new BufferedReader(new FileReader(path));
      Token tok;
      do {
        tok = lex.lexical_scan(br);
        System.out.println("Scan: " + tok);
      } while (tok.tag != Tag.EOF);
    } catch (IOException e) {e.printStackTrace();}
  }

}
