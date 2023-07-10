package sk.tomashrdy.GUI;


import sk.tomashrdy.start.Start;

import javax.swing.*;
import java.awt.*;

public class Frame extends JFrame {


    LoginWindow lw;
    //Konötruktor okna ktor˝ ma na vstupe metodu ktora bude obsluhovaù okno
    // Obsahuje zakladne prikazy na naöt˝lovanie okna
    public Frame(Start start) {
        setLayout(new FlowLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        // ZÌskanie veækosti obrazovky
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        Rectangle screenRect = ge.getMaximumWindowBounds();

        // VypoËÌtanie veækosti okna bez medzier
        int insetX = screenRect.x;
        int insetY = screenRect.y;
        int width = screenRect.width;
        int height = screenRect.height;

        // Pridanie v˝öky liöty so systÈmov˝mi tlaËidlami (napr. 30 pixelov)
        int titleBarHeight = getInsets().top;
        insetY += titleBarHeight;
        height -= titleBarHeight;

        // Nastavenie veækosti okna
        setBounds(insetX, insetY, width, height);


        lw = new LoginWindow(this , start);
        setContentPane(lw.getContent());

        // NaËÌtanie faviconu
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