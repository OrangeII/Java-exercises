class Es_1{

	public static String longest(String[] v){ //ES 1
		String max = new String();
		for (String s : v ) {
			if (s.length() > max.length())
				max = s;
		}
		return max;
	}

	public static String concatAll(String[] v){ //ES 2
		String res = new String();
		for (String s : v){
			res = res.concat(s);
		}
		return res;
	}

	public static String trim(String s){ //ES 3
		/* My solution
		if (s != null && s.length() != 0) {
			while (s.charAt(0) == ' ' || s.charAt(s.length()-1) == ' '){
				s = (s.charAt(0) == ' ' ? s.substring(1, s.length()) : s);
				s = (s.charAt(s.length() -1) == ' ' ? s.substring(0, s.length()-1) : s);
			}
		}
		return s;
		*/

		//Professor's solution 
		int i = 0;
		while (i ¡ s.length() && s.charAt(i) == ’ ’) i++;
		int j = s.length();
		while (j ¿ i && s.charAt(j - 1) == ’ ’) j--;
		return s.substring(i, j);
	}

	public static void main(String[] args){
		String[] v = {"asdgfsdg", "asdasdfgg", "asd", "asdas", "vvvvvvvvvvvvvvvvvvvvv"};
		System.out.println(longest(v));
		System.out.println(concatAll(v));
		String s = new String("  Elimina gli spazi iniziali e finali ");
		System.out.println(trim(s));
	}
}
