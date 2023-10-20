import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
/**
This class is responisble for the hud.
It sets up a JPanel that is to contain all the elements in the hud.
 @author Joachim'
 @version 0.0.1
 */

public class Hud {
    JPanel hud;
    JPanel health_bar;
    JLabel health_image;
    public Hud(JLayeredPane frame){
        //Adds the panel that will contain all the elements
        hud = new JPanel();
        hud.setLayout(new BorderLayout());
        hud.setBounds(0,880, 1920, 200);
        hud.setVisible(true);
        hud.setBackground(Color.PINK);
        hud.setBorder(new LineBorder(Color.ORANGE, 10));
        frame.add(hud, JLayeredPane.PALETTE_LAYER);

        //Add the health bar
        health_bar = new JPanel();
        hud.add(health_bar);
        frame.add(health_bar, JLayeredPane.PALETTE_LAYER);
        health_image = new JLabel(new ImageIcon("hp0.png"));
        health_bar.add(health_image);
        health_bar.setBounds(0,0, 1000, 35);


        //screen.add_element("grass_full.png", 0, 0, 1920, 1080, "bg");

    }
}
