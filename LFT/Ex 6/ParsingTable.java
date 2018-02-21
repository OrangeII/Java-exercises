import java.util.*;
public class ParsingTable{
	public List<Map<Integer, Azione>> tabAzioni; //come index delle colonne azioni posso usare la tag del togen
	public List<Map<String, Azione>> tabGoto;  //per i non teminali(colonne GOTO) uso una lista di mappe per poter usare string come chiavi

	public ParsingTable(List<Map<Integer, Azione>> inAzioni, List<Map<String, Azione>> inGoto){
		this.tabGoto = inGoto;
		this.tabAzioni = inAzioni;
	}

	public Azione parser_azioni(int stato, int terminale){
		return this.tabGoto.get(stato).get(terminale);
	}


	public Azione parser_goto(int stato, String nonTerminale){
		return this.tabGoto.get(stato).get(nonTerminale);
	}

}
