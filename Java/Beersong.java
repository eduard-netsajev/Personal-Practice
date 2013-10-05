public class Beersong {
	public static void main(String[] args){
		int x = 99;
		while(x > 1){
			System.out.println(x+" bottles of beer on the wall, "+x+" bottles of beer.");
			x--;
			System.out.println("Take one down and pass it around,"+x+" bottles of beer on the wall.");
			System.out.println();
		}
		System.out.println("1 bottle of beer on the wall, 1 bottle of beer.");
		System.out.println("Take one down and pass it around, no more bottles of beer on the wall.");
		System.out.println();
		System.out.println("No more bottles of beer on the wall, no more bottles of beer. ");
		System.out.println("Go to the store and buy some more, 99 bottles of beer on the wall.");
	}
}