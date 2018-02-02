public class Ex1_3{

	public static boolean scan(String s){
		int state = 0; //current state
		int i = 0; //next symbol to read

		while(i >= 0 && i < s.length()){
			//System.out.println("Stato: " + state);
			final char ch = s.charAt(i++);
			switch(state){
				case 0:{
					if ((ch >= '0' && ch <= '9') && Character.getNumericValue(ch) % 2 == 0) {
						state = 1;
					} else if ((ch >= '0' && ch <= '9') && Character.getNumericValue(ch) % 2 != 0) {
						state = 2;
					} else {
						state = -1;
					} break;
				}
				case 1: {
					if ((ch >= '0' && ch <= '9') && Character.getNumericValue(ch) % 2 == 0) {
						state = 1;
					} else if ((ch >= '0' && ch <= '9') && Character.getNumericValue(ch) % 2 != 0) {
						state = 2;
					} else if (ch >= 'A' && ch <= 'K') {
						state = 3;
					} else {
						state = -1;
					} break;
				}
				case 2: {
					if ((ch >= '0' && ch <= '9') && Character.getNumericValue(ch) % 2 == 0) {
						state = 1;
					} else if ((ch >= '0' && ch <= '9') && Character.getNumericValue(ch) % 2 != 0) {
						state = 2;
					} else if (ch >= 'L' && ch <= 'Z') {
						state = 3;
					} else {
						state = -1;
					} break;
				}
				case 3: {
					if (ch >= 'a' && ch <= 'z') {
						state = 3;
					} else {
						state = -1;
					} break;
				}
			}
		}
		return state == 3;
	}
	public static void main(String[] args){
		System.out.println(scan(args[0]) ? "OK" : "NOPE");
	}
}
