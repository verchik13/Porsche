public class MakeChanges{
	

	public static void main(String[] args){
		int amount = 87;
		int q,d,n,p;

		q = amount / 25;
		amount = amount % 25;
		d = amount / 10;
		 amount = amount % 10;
		 n = amount / 5;
		 amount = amount % 5;
		 p= amount;



		System.out.println("Quarters:" + q + " Dimes");
	}
}