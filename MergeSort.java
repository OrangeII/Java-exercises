class MergeSort{
	public static void stampa(int[] dati){
		for (int i = 0; i<dati.length; i++) {
			System.out.print(dati[i]);
		}
		System.out.println();
	}
	public static void sort(int[] a, int sx, int dx){
		if(sx < dx){
			int m = (sx + dx)/2;
			sort(a, sx, m);
			sort(a, m+1, dx);

		}
	}
	public static void merge(int[] a, int sx, int m , int dx){
		
	}
	public static void main(String[] args){

	}
}
