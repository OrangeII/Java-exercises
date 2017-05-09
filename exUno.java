class exUno{
	public static boolean exUno(int[] a, int[] b){
		boolean res = false ;
		if(a != null && b!= null){
			res = true;
			for(int i = 0; i<a.length && !res; i++ ){
				int aux = 0;
				for(int j = 0; j<b.length && a[i] > b[j]; j++){
					aux= j;
				}
				res = (aux == b.length);
			}
		}
		return res;
	}
	public static boolean exUno2(int[] a, int[] b){
		boolean res = false ; int i = 0, j = 0;
		if(a != null && b!= null){
			while(i < a.length && !res){
				while(j < b.length && a[i] > b[j]){
					j++;
				}
				res = (j == b.length);
				i++;
			}
		}
		return res;
	}

	public static boolean exDueRic(int[] a, int k, int sx, int dx){
		if(sx < dx){
			return exDueRic(a, k, sx, (sx +dx)/2) && exDueRic(a, k, ((sx + dx) / 2) +1, dx);
		} else {
			if(sx % 2 == 0){
				if(a[sx] == k) {
					return true;
				} else return false;
			} else return true;
		}
	}
	public static boolean exDue(int[] a, int k){
			return exDueRic(a , k, 0, a.length-1);


	}
	public static void main(String[] args){
		int[] a = {1, 2, 100, 3};
		int[] b = {5, 6, 7, 8, 2, 0, 23};
		System.out.println(exUno(a, b));

		int[] c = {4, 1, 4, 1, 4};
		System.out.println(exDue(a, 4));
	}
}
