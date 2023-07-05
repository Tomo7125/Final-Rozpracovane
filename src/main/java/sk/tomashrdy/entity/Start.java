package sk.tomashrdy.entity;

import sk.tomashrdy.GUI.Frame;
import sk.tomashrdy.dbCon.DatabaseConnection;

public class Start {
    //Pouijem na uloenie prihláseného pouívatela
    User user;

    //Vráti udaje pouívatela ktorého tu budem ma uloeneho ako prihlaseneho
    public User getUser() {
        return user;
    }
    // Nastavím si pouivátela po prihlásení
    public void setUser(User user) {
        this.user = user;
    }
    //Konštruktor
    public Start() {
    }
    //Metóda pre spustenie programu
    public void spusti(){Frame frame = new Frame(this);}

    public void deleteUserByEmail(String email){
        DatabaseConnection databaseConnection = new DatabaseConnection();
        databaseConnection.executeUpdate("DELETE FROM users WHERE email = ?" ,
                email);
        databaseConnection.disconnect();
    }

}
