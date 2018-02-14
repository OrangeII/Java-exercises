import java.utils.*;
import java.io.*;

public class Lexer{

  public static int line = 1;
  private char peek = ' ';

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
      case '=';
        peek = ' ';
        return Token.assign;

      //TODO ... gestire i casi di &&, ||, <, >, <=, >=, ==, <>, = ...
    }
  }

}
