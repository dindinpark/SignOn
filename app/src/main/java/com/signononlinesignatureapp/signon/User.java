package com.signononlinesignatureapp.signon;

/**
 * Created by Omaimah on 2/19/2016.
 */
public class User {

   //private Point point = new Point();
    private String key;
    private String Email;
   //BigInteger a;
    private String birthdate;
    //boolean infinity;
    private String password;
    private String username;
    //private BigInteger x;
    //private BigInteger y;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    /*public BigInteger getY() {
        return point.getY();
    }

    public void setY(BigInteger y) {
        this.y = point.getY();
    }

    public BigInteger getX() {
        return point.getX();
    }

    public void setX(BigInteger x) {

        this.x = point.getX();
    }

    public BigInteger getA() {
        return point.getA();
    }

    public void setA(BigInteger a) {
        this.a = point.getA();
    }

    public boolean isInfinity() {
        return point.isInfinity();
    }

    public void setInfinity(boolean infinity) {
        this.infinity = point.isInfinity();
    }*/



    public User(String key, String email, String birthdate, String password, String username) {
        this.key = key;
        Email = email;
        this.birthdate = birthdate;
        this.password = password;
        this.username = username;
        /*this.a = getA();
        this.infinity = isInfinity();
        this.x = getX();
        this.y = getY();*/
    }
}
