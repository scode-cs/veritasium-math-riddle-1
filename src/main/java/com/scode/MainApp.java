package com.scode;

import java.util.*;
import java.util.stream.IntStream;

public class MainApp {

    private static Map<Integer, Integer> boxes;
    private static final Integer TOTAL_ATTEMPT = 200; // INPUT for number of try

    public static void main(String[] args) {
        System.out.println("Starting...");

        var results = new HashMap<Integer, Boolean>();
        IntStream.range(1, TOTAL_ATTEMPT+1).forEach(iteration -> {
//            System.out.println("Starting iteration " + iteration);
            boxes = generateRandomBoxes();
//            System.out.println("Boxes are ready!");
            var result = startFinding();
//            System.out.println(iteration + ": " + result);
            results.put(iteration, result);
        });

        var successCount = results.values().stream().filter(result -> Objects.equals(Boolean.TRUE, result)).count();
        System.out.println("Total attempt: " + TOTAL_ATTEMPT + ", Success count = " + successCount);

    }

    private static Map<Integer, Integer> generateRandomBoxes() {
        var boxes = new HashMap<Integer, Integer>();

        var numbers = new ArrayList<Integer>();
        IntStream.range(1, 101).forEach(numbers::add);
        Collections.shuffle(numbers);

        IntStream.range(1, 101).forEach(index -> boxes.put(index, numbers.get(index-1)));

        return boxes;
    }

    private static Boolean startFinding() {
        var unsuccessfulPrisoners = new LinkedHashSet<Integer>();
        IntStream.range(1, 101).forEach(prisoner -> {
            var result = findBox(prisoner);
            if (!result) {
                unsuccessfulPrisoners.add(prisoner);
            }
        });
//        System.out.println("Unsuccessful Prisoners: " + unsuccessfulPrisoners.size());
        return unsuccessfulPrisoners.isEmpty() ? Boolean.TRUE : Boolean.FALSE;
    }

    private static Boolean findBox(Integer prisoner) {
        var result = false;
        var numberInTheBox = 0;

        for (var attempt = 0; attempt < 50; attempt++) {
            if (attempt == 0) {
                numberInTheBox = boxes.get(prisoner);
            } else {
                numberInTheBox = boxes.get(numberInTheBox);
            }

            if (Objects.equals(prisoner, numberInTheBox)) {
                result = true;
                break;
            }
        }

        return result;
    }

}
