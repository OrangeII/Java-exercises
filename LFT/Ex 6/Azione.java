public class Azione{
	public String tipo;
	public int valore;
	public int mag;
	public String nonTerminale;
	public Azione(int valore){
		this.tipo = "shift";
		this.valore = valore;
	}
	public Azione(int valore, int mag, String nonT){
		this.tipo = "reduce";
		this.valore = valore;
		this.mag = mag;
		this.nonTerminale = nonT;
	}
	public Azione(int valore, int mag, char nonT){
		this.tipo = "reduce";
		this.valore = valore;
		this.mag = mag;
		this.nonTerminale = ""+nonT;
	}
}
