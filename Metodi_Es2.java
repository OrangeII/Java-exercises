class Metodi_Es2{

	//checks possible divisions for given integer
	static int controlloDivisori(int n){
		int div = 2;
		while (div <= n/2){
			if (n%div == 0){
				return div;
			}
			div++;
		}
		return n;
	}

	public static void main(String[] args){
		int n1 = SavitchIn.readLineInt();
		int n2 = controlloDivisori(n1);
		if ( n2 == n1) {
			System.out.println("il numero è primo");
		} else {
			System.out.println(n2);
		}
	}
}
