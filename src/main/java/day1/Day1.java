package day1;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class Day1 {
    private static final String INPUT_FILE = "src/main/resources/Day1/inputFile.txt";

    private  static final HashMap<String, Integer> wordToIntValue = new HashMap<String, Integer>(){{
        put("one", 1);
        put("two", 2);
        put("three", 3);
        put("four", 4);
        put("five", 5);
        put("six", 6);
        put("seven", 7);
        put("eight", 8);
        put("nine", 9);
    }};

    public static int calculateSumOfCalibrationValues() {
        int sum = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(INPUT_FILE))) {
            String inputLine;
            while ((inputLine = br.readLine()) != null) {
                int lineValue = calculateSumOfCalibrationValues(inputLine);
                System.out.println(lineValue);
                sum += lineValue;
            }
        } catch (Exception e) {
            System.out.println("Where is the file??????");
        }
        return sum;
    }

    private static int calculateSumOfCalibrationValues(String input) {
        int firstDigit = 0;
        int otherDigit = 0;
        boolean firstDigitFound = false;
        for (int i = 0; i < input.length(); i++) {
            char character = input.charAt(i);
            int parsedDigit = parseTextToDigit(input.substring(i));

            if (Character.isDigit(character)) {
                if (!firstDigitFound) {
                    firstDigit = Character.getNumericValue(character) * 10;
                    firstDigitFound = true;
                }
                otherDigit = Character.getNumericValue(character);
            } else if (parsedDigit > 0) {
                if (!firstDigitFound) {
                    firstDigit = parsedDigit * 10;
                    firstDigitFound = true;
                }
                otherDigit = parsedDigit;
            }
        }
        return firstDigit + otherDigit;
    }

    private static int parseTextToDigit(String substring) {
        for (Map.Entry<String, Integer> entry : wordToIntValue.entrySet()) {
            if (substring.startsWith(entry.getKey())) {
                return entry.getValue();
            }
        }
        return -1;
    }
}
