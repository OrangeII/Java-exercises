public class Ex1_1{

	/*
	Come deve essere modificato il DFA in Figura 1 per riconoscere il linguaggio complementare,
	ovvero il linguaggio delle stringhe di 0 e 1 che non contengono 3 zeri consecutivi?  Progettare e
	implementare il DFA modificato, e testare il suo funzionamento.
	*/
	//devo scambiare gli stati di accettazione con quelli di non accettazione,
	//basta testare che lo stato finale sia diverso da 3, il quale diventa l'unico stato non-accettante

	//to get the compementary language i must swap the accepting states with the non-accepting states
	//so every state but state 3 becomes an accepting state, state 3 becomes a non-accepting state
	//I just test if the final state is not 3

	public static boolean scan(String s){
		int state = 0; //current state
		int i = 0; //next symbol to read

		while(i >= 0 && i < s.length()){
			final char ch = s.charAt(i++);
			switch(state){
				case 0:{
					if(ch == '0'){
						state = 1;
					} else if(ch == '1'){
						state = 0;
					} else {
						state = -1;
					}
					break;
				}
				case 1:{
					if(ch == '0'){
						state = 2;
					} else if(ch == '1'){
						state = 0;
					} else {
						state = -1;
					}
					break;
				}
				case 2:{
					if(ch == '0'){
						state = 3;
					} else if(ch == '1'){
						state = 0;
					} else {
						state = -1;
					}
					break;
				}
				case 3:{
					if(ch == '0' || ch == '1'){
						state = 3;
					} else {
						state = -1;
					}
					break;
				}
			}
		}
		return (state != 3 && state != -1);
	}
	public static void main(String[] args){
		System.out.println(scan(args[0]) ? "OK" : "NOPE");
	}
}
