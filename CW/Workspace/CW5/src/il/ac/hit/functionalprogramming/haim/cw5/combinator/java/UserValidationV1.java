package il.ac.hit.functionalprogramming.haim.cw5.combinator.java;

import java.util.function.Function;

/**
 * lifemichael.com
 * @author Life Michael
 */
interface UserValidationV1 extends Function<User, Boolean> {
    static UserValidationV1 usernameLengthBiggerThan8() {
        //return user -> user.getUsername().trim().length()>8;
        return new UserValidationV1() {

            @Override
            public Boolean apply(User user) {
                return user.getUsername().trim().length() > 8;
            }
        };
    }

    static UserValidationV1 emailEndsWithIL() {
        return user -> user.getEmail().endsWith("il");
    }

    static UserValidationV1 ageBiggerThan18() {
        return user -> user.getAge() > 18;
    }

    default UserValidationV1 and(UserValidationV1 other) {

        //return user -> this.apply(user) && other.apply(user);

        return new UserValidationV1() {

            @Override
            public Boolean apply(User user) {
                return this.apply(user) && other.apply(user);
            }
        };
    }

    default UserValidationV1 or(UserValidationV1 other) {

        //return user -> this.apply(user) && other.apply(user);

        return new UserValidationV1() {

            @Override
            public Boolean apply(User user) {
                return this.apply(user) || other.apply(user);
            }
        };
    }
}