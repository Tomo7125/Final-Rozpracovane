package sk.tomashrdy.entity;

import sk.tomashrdy.GUI.Frame;

public class Start {
    //Pou�ijem na ulo�enie prihl�sen�ho pou��vatela
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
