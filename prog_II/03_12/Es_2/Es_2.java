class Es_2{
	public static void main(String[] args){
		Stack stack = new Stack(100);

		String code = args[0];
		int pc = 0;
		while(pc < code.length()){
			char c = code.charAt(pc);
			if (c >= '0' && c <= '9'){
				stack.push(c-'0');
			} else if (c == '*') {
				int a = stack.pop();
				int b = stack.pop();
				stack.push(a * b);
			} else if (c == '+') {
				int a = stack.pop();
				int b = stack.pop();
				stack.push(a + b);
			} else if (c == '-') {
				int a = stack.pop();
				int b = stack.pop();
				stack.push(b - a);
			} else if (c == '/') {
				int a = stack.pop();
				int b = stack.pop();
				stack.push(b / a);
			} else if (c == '%') {
				int a = stack.pop();
				int b = stack.pop();
				stack.push(b % a);
			}
			pc++;
		}
		System.out.println(stack.pop());
	}
}
