package sk.tomashrdy.entity;

import sk.tomashrdy.GUI.Frame;

public class Start {
    //Použijem na uloženie prihláseného používatela
    User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Start() {
    }

    public void spusti(){Frame frame = new Frame(this);}

}
