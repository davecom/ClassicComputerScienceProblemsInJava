package chapter1;

public class Fib1 {

	// This method will cause a java.lang.StackOverflowError
	private static int fib1(int n) {
		return fib1(n - 1) + fib1(n - 2);
	}

	public static void main(String[] args) {
		// Don't run this!
		System.out.println(Fib1.fib1(5));
	}
}
