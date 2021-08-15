package il.ac.hit.functionalprogramming.haim.cw5.combinator.java;

import java.util.Optional;

/**
 * lifemichael.com
 * @author Life Michael
 */
public class Valid implements ValidationResult {


    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public Optional<String> getReason() {
        return Optional.empty();
    }
}
