package il.ac.hit.functionalprogramming.haim.cw5.combinator.java;

/**
 * lifemichael.com
 * @author Life Michael
 */
public class UserValidationV1Demo {
    public static void main(String args[]) {

        User moshe = new User("moshmosh","mosh@gmail.co.il","fafa123", 19, "Israel");

        UserValidationV1 allowAction18Movies =
                UserValidationV1.ageBiggerThan18().and(UserValidationV1.usernameLengthBiggerThan8());
        System.out.println(allowAction18Movies.apply(moshe));


    }
}
