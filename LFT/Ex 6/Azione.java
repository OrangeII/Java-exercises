public class Azione{
	public String tipo;
	public int valore;
	public int mag;
	public Azione(int valore){
		this.tipo = "shift";
		this.valore = valore;
	}
	public Azione(int valore, int mag){
		this.tipo = "reduce";
		this.valore = valore;
		this.mag = mag;
	}
}
