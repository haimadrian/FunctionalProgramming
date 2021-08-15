package il.ac.hit.functionalprogramming.haim.cw5.combinator.java;

import java.util.Optional;

/**
 * lifemichael.com
 * @author Life Michael
 */
class Invalid implements ValidationResult {

    private final String reason;

    Invalid(String reason) {
        this.reason = reason;
    }

    public boolean isValid() {
        return false;
    }

    public Optional<String> getReason() {
        return Optional.of(reason);
    }


}