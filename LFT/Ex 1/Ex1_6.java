public class Ex1_6{
	//thanks Ravindra sir
	//https://www.youtube.com/watch?v=4yB7ECgZJmw&t=228s

	public static boolean scan(String s){
		int state = 0; //current state
		int i = 0; //next symbol to read

		while(i >= 0 && i < s.length()){
			System.out.println("Stato: " + state);
			final char ch = s.charAt(i++);
			switch(state){
				case 0:{
					if (ch == '1') {
						state = 1;
					} else if (ch == '0') {
						state = 0;
					} else {
						state = -1;
					} break;
				}
				case 1: {
					if (ch == '0') {
						state = 2;
					} else if (ch == '1') {
						state = 0;
					} else {
						state = -1;
					} break;
				}
				case 2: {
					if (ch == '0') {
						state = 1;
					} else if (ch == '1') {
						state = 2;
					} else {
						state = -1;
					} break;
				}
			}
		}
		return (state == 0);
	}
	public static void main(String[] args){
		System.out.println(scan(args[0]) ? "OK" : "NOPE");
	}
}
