// Classe per modellare uno stack con capacita` finita di numeri
// interi
public class Stack
{
    private int[] stack; // stack != null
    private int size;    // 0 <= size <= stack.length

    public Stack(int capacity)
    {
	assert capacity >= 0;
	stack = new int[capacity];
	size = 0;
    }

    // e` conveniente mettere a disposizione due operazioni per sapere
    // se lo stack e` vuoto o pieno. Cio` consente all'utilizzatore
    // dello stack di sapere quando un'operazione push/pop e` lecita o
    // meno
    public boolean empty()
    { return size == 0; }

    public boolean full()
    { return size == stack.length; }

    public void push(int x)
    {
	assert !full();
	stack[size++] = x;
    }

    public int pop()
    {
	assert !empty();
	return stack[--size];
    }
}
