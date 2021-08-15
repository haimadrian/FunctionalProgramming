package il.ac.hit.functionalprogramming.haim.cw5.combinator.java;

import java.util.function.Function;
import java.util.function.Predicate;

/**
 * lifemichael.com
 * @author Life Michael
 */
interface UserValidationV2 extends Function<User, ValidationResult> {
    static UserValidationV2 nameIsNotEmpty() {
        return check(user -> user.getUsername().trim().isEmpty(), "username is not empty");
    }

    static UserValidationV2 emailEndsWithIL() {
        return check(user -> user.getEmail().endsWith(".il"), "email not ends with il");
    }

    static UserValidationV2 check(Predicate<User> p, String message){
        return user -> p.test(user) ? new Valid() : new Invalid(message);
    }

    default UserValidationV2 and(UserValidationV2 other) {
        return user -> {
            final ValidationResult result = this.apply(user);
            return result.isValid() ? other.apply(user) : result;
        };
    }
}