class BubbleSort{

	public static int dati[] = {2, 8, 6, 7, 4, 5, 0, 1, 9};

	//ascending order
	public static void sort(int[] dati){
		boolean b = true;
		while(b == true){
			b = false;
			for (int i = 0; i<dati.length-1; i++) {
				if (dati[i] > dati[i+1]) {
					scambio(dati, i, i+1);
					b = true;
				}
			}
			stampa(dati);
		}
		return;
	}

	public static void scambio(int[] dati, int i, int j){
		int aux;
		aux = dati[i];
		dati[i] = dati[j];
		dati[j] = aux;
	}

	public static void stampa(int[] dati){
		for (int i = 0; i<dati.length; i++) {
			System.out.print(dati[i]);
		}
		System.out.println();
	}
	public static void main(String[] args){
		stampa(dati);
		sort(dati);
		stampa(dati);
	}
}
