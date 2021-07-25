package il.ac.hit.functionalprogramming.haim.cw3.java;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Develop a simple application that creates a list with the following numbers: 3,5,2,32,43,30 and 321.
 * Your application should call the filter method on the list object passing over the required anonymous
 * function in order to get a new sub list composed of all numbers that divide by 3 without any residual.
 * Your application should print out the new list to the screen.
 *
 * @author Haim Adrian
 * @since 25 Jul 2021
 */
public class FilterListUsingAnonymousLambda {
    public static void main(String[] args) {
        List<Integer> unmodifiableList = Arrays.asList(3, 5, 2, 32, 43, 30, 321);
        System.out.println("List: " + unmodifiableList);
        System.out.println("Numbers divided by 3, using stream API: " + unmodifiableList.stream().filter(num -> num % 3 == 0).collect(Collectors.toList()));

        // Arrays.asList is unmodifiable. Create a new modifiable array list.
        List<Integer> modifiableList = new ArrayList<>(unmodifiableList);
        modifiableList.removeIf(num -> num % 3 != 0);
        System.out.println("Numbers divided by 3, using removeIf: " + modifiableList);
    }
}
