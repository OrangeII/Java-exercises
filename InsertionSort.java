class InsertionSort{

	public static int[] dati = {3,6,7,8,0,2,3,4,1,7,5,8};

	public static void sort(int[] dati){
		int v, j;
		for (int i = 1; i<dati.length; i++) {
			v = dati[i];
			j = i;
			while	((j>0) && (v < dati[j-1])){
				dati[j] = dati[j-1];
				j--;
			}
			dati[j] = v;
			stampa(dati);
		}
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
