// versione alternativa della calcolatrice in cui le operazioni di
// inserimento (push) ed estrazione (pop) di elementi dalla pila sono
// fattorizzate in metodi corrispondenti. Notare che la pila deve
// essere rappresentata da variabili globali, il che non risolve il
// problema se lo stesso programma necessita di 2 o piu` pile. In
// generale, l'uso di variabili globali e` sintomo di un problema
// nella struttura del programma

public class Calcolatrice2
{
    public static int[] stack = new int[100];
    public static int size = 0;

    public static void push(int x)
    {
	stack[size] = x;
	size++;
    }

    public static int pop()
    {
	size--;
	return stack[size];
    }

    public static void main(String[] args)
    {
	String code = args[0];
	int pc = 0;
	while (pc < code.length()) {
	    char c = code.charAt(pc);
	    if (c >= '0' && c <= '9') {
		push(c - '0');
	    } else if (c == '+') {
		int a = pop();
		int b = pop();
		push(a + b);
	    } else if (c == '*') {
		int a = pop();
		int b = pop();
		push(a * b);
  } else if (c == '-') { //ES 2
    int a = pop();
    int b = pop();
    push(b - a); //*1 la pila è LIFO quindi gli operandi sono invertiti
  } else if (c == '/') {
    int a = pop();
    int b = pop();
    push(b / a); //*1
  } else if (c == '%') {
    int a = pop();
    int b = pop();
    push(b % a); //*1
  }
	    pc++;
	}
	System.out.println(pop());
    }
}
