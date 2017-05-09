class Sim2016_2{

	/** Metodo privato che deve essere usato dalla soluzione ai primi due esercizi */
  private static boolean P(int x) {
		return (x % 2 == 0);
	}

	/**
	 * ESERCIZIO 1 (Massimo 7 punti -- da consegnare elettronicamente).
	 *
	 * Scrivere un metodo iterativo exUno con le seguenti caratteristiche:
	 * a) exUno ha un parametro formale a di tipo reference ad un array di interi
	 * ed un parametro formale l di tipo intero compreso nell'intervallo [0...a.length - 1];
	 * b) exUno modifica l'array a riorganizzandolo in modo che,
	 * per ogni elemento a[i] a destra di l
	 * se P(a[i]) e' vero, allora, nell'ordine:
	 *    1) a[i] va spostato alla posizione l, usando ESCLUSIVAMENTE il meccanismo dell'Insertion sort,
	 *    2) l va sposato verso destra di una posizione.
	 *
	 * ESEMPI.
	 * Se b=={0, 2, 5, 1, 4, 6}, allora exUno(b, 2) restituisce b=={0, 2, 4, 6, 5, 1}.
	 * Se b=={0, 2, 5, 1, 4, 6}, allora exUno(b, 1) restituisce b=={0, 4, 6, 2, 5, 1}.
	 *
	 * DOVE SCRIVERE exUno.
	 * Il metodo exUno va scritto immediatamente al di fuori di questo commento. L'aggiunta del metodo
	 * exUno deve mantenere compilabile la classe.
	*/
	public static int[] exUno(int[] a, int l){
		if (a == null || l < 0 || l > a.length -1)
			return a;
		for (int i = l +1  ; i<a.length; i++){
			if(P(a[i])){
				int j = l;
				int n = a[j];
				while (j < k){
					a[j] = a[j-1];
					j--;
				}
				a[l] = n;
				l++;
			}
		}
	}
	/**
	 * ESERCIZIO 2 (Massimo 8 punti -- da consegnare elettronicamente).
	 *
	 * Scrivere un metodo ricorsivo exDue con le seguenti caratteristiche:
	 * a) exDue ha un parametro formale a di tipo reference ad un array di interi;
	 * b) exDue restituisce l'array a modificato in modo che:
	 *    1) tutti e soli gli elementi di a per cui il metodo P dato e' vero siano sostituiti dal valore 1.
	 *       I rimanenti valori, invece, devono essere sostituiti dal valore 0.
	 *    2) la visita dell'array a DEVE procedere suddividendo a in sotto array di lunghezza essenzialmente
	 *    identica, in analogia alla ricerca dicotomica.
	 *
	 * ESEMPI.
	 * Se b=={0, 2, 5, 1, 4} allora exDue(b) restituisce b=={1,1,0,0,1}.
	 * Se b=={2, 5, 8} allora exUno(b) restituisce b=={1,0,1}.
	 * Se b=={2} allora exUno(b) restituisce b=={1}.
	 * Se b=={1} allora exUno(b) restituisce b=={0}.
	 * Se b=={} allora exUno(b) restituisce b=={}.
	 *
	 * DOVE SCRIVERE exDue.
	 * Il metodo exDue va scritto immediatamente al di fuori di questo commento.
	 * L'aggiunta del metodo exDue deve mantenere compilabile la classe.
	 */
	public static void exDue(int[] a, int sx, int dx){
		if (a != null){
			if(sx < dx){
				int m = (sx + dx)/2;
				exDue(a, sx, m);
				exdue(a, m+1, dx);
			} else {
				a[sx] = (P(a[sx]) ? 1 : 0);
			}
		}
	}

	public static void main(String[] args){

	}
}
