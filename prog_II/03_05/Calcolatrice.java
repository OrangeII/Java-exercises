// Calcolatrice.java
// calcolatrice RPN
// RPN = Reverse Polish Notation
// Notazione Polacca Inversa

public class Calcolatrice
{
    public static void main(String[] args)
    {
	// code = espressione da valutare
	String code = args[0];
	// pc = indice del prossimo simbolo
	// da considerare
	int pc = 0;
	// rappresentiamo la pila con un
	// array di numeri interi
	int[] stack = new int[100];
	// size rappresenta la dimensione
	// della pila, ovvero il numero
	// di elementi in essa
	int size = 0;
	while (pc < code.length()) {
	    char c = code.charAt(pc);
	    if (c >= '0' && c <= '9') {
		// inseriamo il valore di c
		// in cima alla pila
		stack[size] = c - '0';
		// incrementare la dimensione
		// della pila
		size++;
	    } else if (c == '+') {
		// decrementare la dimensione
		// della pila
		size--;
		// memorizzare nella variable
		// locale a il valore in cima
		// alla pila
		int a = stack[size];
		size--;
		int b = stack[size];
		stack[size] = a + b;
		size++;
	    } else if (c == '*') {
		size--;
		int a = stack[size];
		size--;
		int b = stack[size];
		stack[size] = a * b;
		size++;
	    }
	    pc++;
	}
	// estraiamo il valore in cima alla pila,
	// che e` anche il valore dell'intera
	// espressione
	size--;
	int risultato = stack[size];
	System.out.println(risultato);
    }
}
