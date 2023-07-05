package sk.tomashrdy.entity;

import sk.tomashrdy.GUI.Frame;
import sk.tomashrdy.dbCon.DatabaseConnection;

public class Start {
    //Pou�ijem na ulo�enie prihl�sen�ho pou��vatela
    User user;

    //Vr�ti udaje pou��vatela ktor�ho tu budem ma� ulo�eneho ako prihlaseneho
    public User getUser() {
        return user;
    }
    // Nastav�m si pou�iv�tela po prihl�sen�
    public void setUser(User user) {
        this.user = user;
    }
    //Kon�truktor
    public Start() {
    }
    //Met�da pre spustenie programu
    public void spusti(){Frame frame = new Frame(this);}

    public void deleteUserByEmail(String email){
        DatabaseConnection databaseConnection = new DatabaseConnection();
        databaseConnection.executeUpdate("DELETE FROM users WHERE email = ?" ,
                email);
        databaseConnection.disconnect();
    }

}
