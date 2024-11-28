package Bank;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Utility {

	public static int userIntInput(Scanner in) {
		int input = 0;
		try {
			input = in.nextInt();
			in.nextLine();
		} catch (InputMismatchException e) {
			System.out.println("Char detected where an integer was expected.");
			in.nextLine();
		}
		return input;
	}

	public static float userFloatInput(Scanner in) {
		float input = 0.0f;
		try {
			input = in.nextFloat();
			in.nextLine();
		} catch (InputMismatchException e) {
			System.out.println("Char detected where an float was expected.");
			in.nextLine();
		}
		return input;
	}

}
