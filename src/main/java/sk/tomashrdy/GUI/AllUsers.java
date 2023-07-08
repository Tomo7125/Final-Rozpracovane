package sk.tomashrdy.GUI;

import sk.tomashrdy.dbCon.DatabaseConnection;
import sk.tomashrdy.entity.Start;
import sk.tomashrdy.entity.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AllUsers implements ActionListener {
    private JPanel panelAllUsers;
    private JLabel jlUsers;
    private JTable table1;
    private JButton buttonDeleteUser;
    private JButton buttonDeleteAllUsers;
    private JButton buttonBack;
    private Frame frame;
    private Start start;

    public AllUsers(Frame frame, Start start) {
        this.frame = frame;
        this.start = start;
        buttonBack.addActionListener(this);
        buttonDeleteAllUsers.addActionListener(this);
        buttonDeleteUser.addActionListener(this);

        ArrayList<User> users = getAllUsers();

            DefaultTableModel model = (DefaultTableModel) table1.getModel();

            // vytvorený mame default model , tu mu nasetujem row na 0
            model.setRowCount(0);

            //Vytvorím model tabulky
            model.addColumn("Index");
            model.addColumn("first_name");
            model.addColumn("last_name");
            model.addColumn("email");
            model.addColumn("Score");
            model.addColumn("isAdmin");
            model.addRow(new Object[]{ "Index" , "Name" , "Last name" , "Email" ,"Score" , "Admin"});
//            TableColumn column = table1.getColumnModel().getColumn(0); // Získanie st?pca pod?a indexu (v tomto prípade index 0)
//            column.setPreferredWidth(30); // nastavý šírku prvého stlpca

            //pomocná index nam rata po?et predmetov po každom cykle ju navýšime
            int index = 0;
            for (User user : users) {
                if (user != null) {
                    String first_name = user.getName();
                    String last_name = user.getLastName();
                    String email = user.getEmail();
                    int score = user.getScore();
                    String admin = user.isAdmin() ? "User is admin" : "User is not admin";


                    //pridame novy riadok do tabulky
                    model.addRow(new Object[]{index, first_name , last_name , email ,score , admin});
                    index++;
                }
            }
            //vezme tabulku a nasetuje model
            table1.setModel(model);
        }

    public JPanel getContent(){return this.panelAllUsers;}

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(buttonBack)){
            frame.setContext(new AdminMenu(frame , start).getContent());
        }
        if (e.getSource().equals(buttonDeleteAllUsers)){
            DatabaseConnection dbCon = new DatabaseConnection();
            dbCon.executeQuery("DELETE FROM users");
            dbCon.disconnect();
            JOptionPane.showMessageDialog(null , "All users is delete" , "Delete" , JOptionPane.WARNING_MESSAGE);
            frame.setContext(new AllUsers(frame , start).getContent());
        }
        if (e.getSource().equals(buttonDeleteUser)){
            int selectedRow = table1.getSelectedRow();
            Object email = table1.getValueAt(selectedRow, 3);
            if (email != null) {
                String emailValue = email.toString();
                start.deleteUserByEmail(emailValue);
                JOptionPane.showMessageDialog(null , emailValue + " is delete" , "User delete" , JOptionPane.INFORMATION_MESSAGE);
                frame.setContext(new AllUsers(frame , start).getContent());
            }
        }
    }
    public ArrayList<User> getAllUsers() {
        ArrayList<User> allUsers = new ArrayList<>();
        DatabaseConnection dbConnect = new DatabaseConnection();
        ResultSet resultSet;

        resultSet = dbConnect.executeQuery("SELECT * FROM users");

        try {
            while (resultSet.next()) {
                String name = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                String email = resultSet.getString("email");
                int score = resultSet.getInt("score");
                Boolean isAdmin = resultSet.getBoolean("isAdmin");

                User user = new User(name, lastName, email , isAdmin , score);
                allUsers.add(user);
            }
            dbConnect.disconnect();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return allUsers;
    }
}
