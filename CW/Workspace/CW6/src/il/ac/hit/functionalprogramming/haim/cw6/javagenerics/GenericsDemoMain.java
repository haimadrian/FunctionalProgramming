package il.ac.hit.functionalprogramming.haim.cw6.javagenerics;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Haim Adrian
 * @since 15 Aug 2021
 */
public class GenericsDemoMain {
    public static void main(String[] args) {
        List<Object> lst = new ArrayList<>();
        lst.add(2.0);

        List<Integer> ints = new ArrayList<>();
        ints.add(3);

        // Compilation error - Doesn't work cause then we could add double, even though this is list of integers
        // lst = (List<Object>)ints;
    }
}
