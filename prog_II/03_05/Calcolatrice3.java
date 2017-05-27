//ES 6
class Calcolatrice3{

	static class WrapInt{
		public int n;
		public WrapInt(int n){
			this.n = n;
		}
	}

	public static void push(int x, int[] stack, WrapInt size){
		stack[size.n] = x;
		size.n ++;
	}

	public static int pop(int[] stack, WrapInt size){
		size.n --;
		return stack[size.n];
	}

	public static void print(int[] stack, WrapInt size){
		System.out.println("size: " + size.n);
		for (int i = size.n -1; i > 0; i--) {
			System.out.println(stack[i]);
		}
		System.out.println("-----");
	}

	public static void main(String[] args){
		int[] stack = new int[100];
		WrapInt size = new WrapInt(0);

		String code = args[0];
		int pc = 0;
		while(pc < code.length()){
			char c = code.charAt(pc);
			if (c >= '0' && c <= '9'){
				push(c-'0', stack, size);
			} else if (c == '*') {
				int a = pop(stack, size);
				int b = pop(stack, size);
				push(a * b, stack, size);
		  } else if (c == '+') {
				int a = pop(stack, size);
				int b = pop(stack, size);
				push(a + b, stack, size);
		  } else if (c == '-') {
				int a = pop(stack, size);
				int b = pop(stack, size);
				push(b - a, stack, size);
		  } else if (c == '/') {
				int a = pop(stack, size);
				int b = pop(stack, size);
				push(b / a, stack, size);
		  } else if (c == '%') {
				int a = pop(stack, size);
				int b = pop(stack, size);
				push(b % a, stack, size);
		  } else if (c == '#') {
				//ES 3
				print(stack, size);
		  }
			pc++;
		}
		System.out.println(pop(stack, size));
	}
}
