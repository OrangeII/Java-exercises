import java.util.*;
public class ParsingTable{
	public List<Map<Integer, Azione>> tabAzioni; //come index delle colonne azioni posso usare la tag del togen
	public List<Map<String, Azione>> tabGoto;  //per i non teminali(colonne GOTO) uso una lista di mappe per poter usare string come chiavi

	public ParsingTable(List<Map<Integer, Azione>> inAzioni, List<Map<String, Azione>> inGoto){
		this.tabGoto = inGoto;
		this.tabAzioni = inAzioni;
	}

	public Azione parser_azioni(int stato, int terminale){
		if (!exists_azioni(stato, terminale)) {
			return null;
		}
		return this.tabAzioni.get(stato).get(terminale);
	}


	public Azione parser_goto(int stato, String nonTerminale){
		if (!exists_goto(stato, nonTerminale)) {
			return null;
		}
		return this.tabGoto.get(stato).get(nonTerminale);
	}

	public boolean exists_azioni(int stato, int terminale){
		if (tabAzioni.size() < stato) {
			return false;
		}
		if (!tabAzioni.get(stato).containsKey(terminale)) {
			return false;
		}
		return true;
	}

	public boolean exists_goto(int stato, String nonTerminale){
		if (tabGoto.size() < stato) {
			return false;
		}
		if (!tabGoto.get(stato).containsKey(nonTerminale)) {
			return false;
		}
		return true;
	}

}
