package com.company;

import java.util.ArrayList;

/**
 * Created by gilbertakpan on 12/6/16.
 */
public class User {
    String name;
    String password;
    ArrayList<Messages> posts =new ArrayList<>();

    public User(String name, String password){
        this.name = name;
        this.password = password;
    }
}
/*Add a form in messages.html which lets you delete a message by entering its number.
Add a form in messages.html which lets you edit a message by entering its number and the text you want to replace it with.
Optional: Make the microblog persist data on the disk by encoding the data as JSON in each POST route,
and decoding it when the web app first runs.*/