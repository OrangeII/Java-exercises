class Sim2016_1{

	/**
	 * ESERCIZIO 1 (Massimo 7 punti -- da consegnare elettronicamente).
	 * Scrivere un metodo iterativo exUno con le seguenti caratteristiche:
	 * a) exUno ha due parametri formali con nome a e b, rispettivamente,
	 *    entrambi di tipo (reference ad) array di interi;
	 * b) exUno restituisce un array r che soddisfi il seguente requisito:
	 *          per ogni i,
	 *          r[i] e' true se l'elemento di posizione i in a e' multiplo
	 *          dell'elemento di egual posizione in b o viceversa.
	 * Si osservi che i parametri attuali possono avere lunghezze differenti.
	 * Inoltre, exUno non deve generare alcun tipo d'errore.
	 *
	 * ESEMPI. Se a -> {1,2,6,4}, b -> {2,5,2,9,10} allora r -> {true,false,true,false}
	 *
	 * DOVE SCRIVERE exUno. Il metodo exUno va scritto immediatamente al di
	 * fuori di questo commento. L'aggiunta del metodo exUno deve mantenere
	 * compilabile la classe.
	 */
	 public static boolean[] exUno(int[] a, int[] b){
		 boolean[] r = null;

		 if(!(a == null || b == null)){
			if (a.length > b.length)
			 	r = new boolean[b.length];
			else
				r = new boolean[a.length];

			for(int i = 0; i<r.length;i++){
				r[i] = (a[i]%b[i] == 0) || (a[i]%b[i] == 0);
			}
		}
		return r;

	 }


	 /**
	 * ESERCIZIO 2 (Massimo 8 punti -- da consegnare elettronicamente).
	 *
	 * Scrivere un metodo exDue che, richiamando un metodo ricorsivo exDueRic, rispetti i requisiti riportati.
	 * a) exDue DEVE avere un singolo parametro formale di nome a
	 *    e di tipo reference ad un array di interi;
	 * b) se il riferimento a e' definito allora exDue restituisce il valore intero ottenuto
	 *    dal metodo ricorsivo exDueRic applicato ad a,
	 *    altrimenti exDue restituisce -1;
	 * c) exDueRic:
	 *    1) restituisce la somma degli opposti di tutti gli elementi di a
	 *       per i quali almeno uno tra testUno e testDue sia vero.
	         Altrimenti exDueRic restituisce 0.
	 *    2) ad ogni chiamata ricorsiva, procede nella visita dell'array a, suddividendola in due
	 *       parti la cui lunghezza differisca al piu' di una unita'.
	 *
	 * La chiamata a exDue non deve generare alcun tipo d'errore.
	 *
	 * ESEMPI.
	 * Se b->{10, 2, 2, 4, -1, 2, 4} allora exDue(b) restituisce -17 ottenuto dalla somma -10-4+1-4.
	 *
	 * DOVE SCRIVERE exDue.
	 * Il metodo exDue va scritto immediatamente al di fuori di questo commento.
	 * L'aggiunta del metodo exDue deve mantenere compilabile la classe.
	 */
	 private static boolean testUno(int x) {
			return (x < 0);
	 }
	 private static boolean testDue(int x) {
			return (x > 3);
	 }
	 public static int exDue(int[] a){
		 int r = -1;
		 if (a != null) {
		 	r = exDueRic(a, 0, a.length -1);
		 }
		 return r;
	 }
	 public static int exDueRic(int[] a, int sx, int dx){
		 if(sx < dx){
			 return exDueRic(a, sx, (sx + dx)/2) + exDueRic(a, (sx + dx)/2+1 , dx);
		 } else {
			 return (testUno(a[sx]) || testDue(a[sx])) ? -a[sx] : 0;
		 }
	 }


	public static void main(String[] args){
		int[] b = {10, 2, 2, 4, -1, 2, 4};
		System.out.println(exDue(b));
	}
}
