package sample.models;

import java.time.LocalDate;

public class Account {

    private int id;
    private String username;
    private String password;
    private Calories calories;
    private Macros macros;
    private String dateOfLogIn;

    public String getDateOfLogIn() {
        return dateOfLogIn;
    }

    public void setDateOfLogIn(String dateOfLogIn) {
        this.dateOfLogIn = dateOfLogIn;
    }


    public Calories getCalories() {
        return calories;
    }

    public void setCalories(Calories calories) {
        this.calories = calories;
    }

    public Macros getMacros() {
        return macros;
    }

    public void setMacros(Macros macros) {
        this.macros = macros;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
