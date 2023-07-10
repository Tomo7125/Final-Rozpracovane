package sk.tomashrdy.GUI;


import sk.tomashrdy.start.Start;

import javax.swing.*;
import java.awt.*;

public class Frame extends JFrame {


    LoginWindow lw;
    //Kon�truktor okna ktor� ma na vstupe metodu ktora bude obsluhova� okno
    // Obsahuje zakladne prikazy na na�t�lovanie okna
    public Frame(Start start) {
        setLayout(new FlowLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        // Z�skanie ve�kosti obrazovky
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        Rectangle screenRect = ge.getMaximumWindowBounds();

        // Vypo��tanie ve�kosti okna bez medzier
        int insetX = screenRect.x;
        int insetY = screenRect.y;
        int width = screenRect.width;
        int height = screenRect.height;

        // Pridanie v��ky li�ty so syst�mov�mi tla�idlami (napr. 30 pixelov)
        int titleBarHeight = getInsets().top;
        insetY += titleBarHeight;
        height -= titleBarHeight;

        // Nastavenie ve�kosti okna
        setBounds(insetX, insetY, width, height);


        lw = new LoginWindow(this , start);
        setContentPane(lw.getContent());

        // Na��tanie faviconu
        ImageIcon favicon = new ImageIcon("D:\\Kurz\\Macrosoft\\Final_Projekt_02\\src\\main\\resources\\Img\\fv_Brain.png");
        setIconImage(favicon.getImage());

        setVisible(true);
    }



    //Metoda nastavy obsah okna
    public void setContext(JPanel novyContext){
        setContentPane(novyContext);
        revalidate();
        repaint();
    }
}