class RicDicotomica{

	public static int iterativa(int[] v, int n){
		int sx = 0, dx = v.length -1, m;
		while(sx < dx){
			//detarmina la mediana
			m = (sx + dx) / 2;
			if (v[m]>n) { //se il valore nella mediana è maggiore di quello ricercato, devo cercare più a sinistra
				dx = m - 1;
			} else if (v[m] < n){ //se è minore devo cercare più a destra
				sx = m + 1;
			} else { //se è uguale fermo la ricerca
				sx= dx = m;
			}
		}
		return (v[m] == n) ? m : -1;
	}

	public static int ricorsiva(int[] v, int n, int sx, int dx){
		int m;
		if (sx < dx){
			m = (sx + dx) / 2;
			if (v[m] > n) {
				ricorsiva(v, n, sx, m-1);
			} else if (vet[m] < n) {
				ricorsiva(v, n, m+1, dx);
			} else {
				//return m;
			}
		} else {
			if (v[sx] == n) {
				m = sx;
			} else m = -1;
		}
		reutrn m;
	}

	public static void main(String[] args){

	}
}
