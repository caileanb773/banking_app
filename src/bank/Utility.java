package bank;

import java.math.BigDecimal;
import java.math.RoundingMode;
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

    public static BigDecimal userAmountInput(Scanner in) {
        BigDecimal input = BigDecimal.ZERO;
        while (true) {
			System.out.print("Enter a dollar value:\n> ");
            String userInput = in.nextLine();
            if (!userInput.matches("\\d+(\\.\\d{2})?")) {
                System.out.println("Please enter a whole dollar value or a dollar value followed by two decimal places.");
                continue;
            }
            try {
                input = new BigDecimal(userInput).setScale(2, RoundingMode.HALF_UP);
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid decimal number.");
            }
        }
        return input;
    }

}
