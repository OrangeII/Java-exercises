class Metodi_Es1{

	static boolean verifica(String stringa, char c){
		//if char c is contained in string stringa
		if (stringa.indexOf(c)>=0) {
			return true;
		}
		return false;
	}

	public static void main(String[] args){
		//takes inputs
		String s1 = SavitchIn.readLine();
		String s2 = SavitchIn.readLine();

		//if the strings are the same length
		if (s1.length() == s2.length()) {
			System.out.println(verifica(s1, s2.charAt(0)));
		} else {
			System.out.println(verifica(s2, s1.charAt(s1.length()-1)));
		}
	}

}
