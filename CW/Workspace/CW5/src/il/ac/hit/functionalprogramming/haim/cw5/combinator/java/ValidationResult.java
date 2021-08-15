package il.ac.hit.functionalprogramming.haim.cw5.combinator.java;

import java.util.Optional;

/**
 * lifemichael.com
 * @author Life Michael
 */
interface ValidationResult {
    static ValidationResult valid() {
        return new Valid();
    }

    static ValidationResult invalid(String reason) {
        return new Invalid(reason);
    }

    boolean isValid();

    Optional<String> getReason();
}

