package il.ac.hit.functionalprogramming.haim.cw5.combinator.java;

/**
 * lifemichael.com
 * @author Life Michael
 */
public class User{
    private String username;
    private String email;
    private String password;
    private int age;
    private String country;

    public User(String username, String email, String password, int age, String country) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.age = age;
        this.country = country;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        //..
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        //...
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}