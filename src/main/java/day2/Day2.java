package day2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

public class Day2 {

    private static final String GAME_ID_PREFIX = "Game ";
    private static final char GAME_ID_DELIMITER = ':';
    private static final String INPUT_FILE = "src/main/resources/Day2/inputFile.txt";

    private static final Map<String, Integer> COLOR_PER_NUMBER_OF_CUBES = new HashMap<>(){{
        put("red", 12);
        put("blue", 14);
        put("green", 13);
    }};

    public static int calculateSumOfPossibleGames() {
        try (BufferedReader br = new BufferedReader(new FileReader(INPUT_FILE))) {
            String inputLine;
            int sumOfIds = 0;
            int sumOfPowersOfCubes = 0;
            while ((inputLine = br.readLine()) != null) {
                int gameId = parseGameId(inputLine);
                boolean isPossible = calculatePossibilityOfGame(inputLine.substring(inputLine.indexOf(':') + 2));
                sumOfPowersOfCubes += calculatePowerOfSet(
                        inputLine.substring(inputLine.indexOf(':') + 2),
                        new HashMap<>(){{
                            put("red", 0);
                            put("green", 0);
                            put("blue", 0);
                        }});
                if (isPossible) {
                    sumOfIds += gameId;
                }
            }
            System.out.println(sumOfIds);
            System.out.println(sumOfPowersOfCubes);
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return 0;
    }

    private static int calculatePowerOfSet(String inputLine, Map<String, Integer> maxValues) {
        int endOfAmountInput = inputLine.indexOf(' ');
        int amount = Integer.parseInt(inputLine.substring(0, endOfAmountInput));
        int colorDelimiterInput = inputLine.indexOf(',', endOfAmountInput);
        int entryDelimiter = inputLine.indexOf(';', endOfAmountInput);
        if (colorDelimiterInput == -1 && entryDelimiter == -1) {
            String color = inputLine.substring(endOfAmountInput + 1);
            if (maxValues.get(color) < amount) {
                maxValues.put(color, amount);
            }
            return maxValues.get("red") * maxValues.get("green") * maxValues.get("blue");
        } else if ((colorDelimiterInput < entryDelimiter && colorDelimiterInput > -1) || entryDelimiter == -1) {
            String color = inputLine.substring(endOfAmountInput + 1, colorDelimiterInput);
            if (maxValues.get(color) < amount) {
                maxValues.put(color, amount);
            }
            return calculatePowerOfSet(inputLine.substring(colorDelimiterInput + 2), maxValues);
        } else {
            String color = inputLine.substring(endOfAmountInput + 1, entryDelimiter);
            if (maxValues.get(color) < amount) {
                maxValues.put(color, amount);
            }
            return calculatePowerOfSet(inputLine.substring(entryDelimiter + 2), maxValues);
        }
    }

    private static boolean calculatePossibilityOfGame(String inputLine) {
        int endOfAmountInput = inputLine.indexOf(' ');
        int amount = Integer.parseInt(inputLine.substring(0, endOfAmountInput));
        int colorDelimiterInput = inputLine.indexOf(',', endOfAmountInput);
        int entryDelimiter = inputLine.indexOf(';', endOfAmountInput);
        if (colorDelimiterInput == -1 && entryDelimiter == -1) {
            String color = inputLine.substring(endOfAmountInput + 1);
            return amount <= COLOR_PER_NUMBER_OF_CUBES.get(color);
        } else if ((colorDelimiterInput < entryDelimiter && colorDelimiterInput > -1) || entryDelimiter == -1) {
            String color = inputLine.substring(endOfAmountInput + 1, colorDelimiterInput);
            if (amount > COLOR_PER_NUMBER_OF_CUBES.get(color)) {
                return false;
            }
            return calculatePossibilityOfGame(inputLine.substring(colorDelimiterInput + 2));
        } else {
            String color = inputLine.substring(endOfAmountInput + 1, entryDelimiter);
            if (amount > COLOR_PER_NUMBER_OF_CUBES.get(color)) {
                return false;
            }
            return calculatePossibilityOfGame(inputLine.substring(entryDelimiter + 2));
        }
    }

    private static int parseGameId(String inputLine) {
        return Integer.parseInt(inputLine.substring(GAME_ID_PREFIX.length(), inputLine.indexOf(GAME_ID_DELIMITER)));
    }
}
