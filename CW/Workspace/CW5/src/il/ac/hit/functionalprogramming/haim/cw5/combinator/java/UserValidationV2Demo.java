package il.ac.hit.functionalprogramming.haim.cw5.combinator.java;

/**
 * lifemichael.com
 * @author Life Michael
 */
public class   UserValidationV2Demo {
    public static void main(String args[]) {
        UserValidationV2 validation = UserValidationV2.emailEndsWithIL().and(UserValidationV2.nameIsNotEmpty());
        User moshe = new User("moshmosh","mosh@gmail.co.il","fafa123", 19, "Israel");
        ValidationResult result = validation.apply(moshe);
        System.out.println(result.isValid());
        //result.getReason().ifPresent(reason -> System.out.println(reason));
        result.getReason().ifPresent(System.out::println);

    }
}
