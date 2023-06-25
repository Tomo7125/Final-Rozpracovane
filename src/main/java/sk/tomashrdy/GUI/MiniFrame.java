package sk.tomashrdy.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MiniFrame extends JFrame{
    //Mini frame budem pou��va� na vyhadzovanie okien s inform�ciou o chybe alebo �spe�nej akcii
    //Na vstupe dostane nadpis , spr�vu , farbu
    public MiniFrame(String title , String message , Color color) {
        // Nastavenie vlastnost� okna , nadpis -> velkos� okna -> aby sa zatvorilo po kliknuti na exit -> poziciu
        setTitle(title);
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        //Prid�m jLabel ( text )  , farbu textu spr�vy , a pridam message
        JLabel messageOnFrame = new JLabel(message);
        messageOnFrame.setHorizontalAlignment(JLabel.CENTER);
        messageOnFrame.setForeground(color);
        add(messageOnFrame);

        // Vytvorenie a pridanie tla�idla OK
        JButton okButton = new JButton("OK");
        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {dispose();} // Zavrie okno
        });
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(okButton);
        add(buttonPanel, BorderLayout.SOUTH);
        setVisible(true);
    }

}
