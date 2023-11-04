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
    JLabel boost_cd;
    JPanel hud;
    JPanel health_bar;
    JLabel health_image;
    Player_character player;
    JLayeredPane frame;
    JLayeredPane death_screen = new JLayeredPane();
    JLabel death_label = new JLabel();
    JButton restart_button = new JButton(get_sized_image("reset_button.png", 100, 50));
    public Hud(JLayeredPane frame, Player_character player){

        SwingUtilities.invokeLater(() -> {
        this.frame = frame;
        this.player = player;
        //Adds the panel that will contain all the elements
        hud = new JPanel();
        hud.setLayout(new BorderLayout());
        hud.setBounds(0,880, 1920, 200);
        hud.setVisible(true);
        hud.setBackground(Color.GRAY);
        hud.setBorder(new LineBorder(Color.ORANGE, 10));
        frame.add(hud, JLayeredPane.PALETTE_LAYER);
        death_screen.setBounds(0,0, 1920, 1080);
        death_screen.setVisible(false);
        frame.add(death_screen, JLayeredPane.POPUP_LAYER);

        //cd
        boost_cd = new JLabel();
        boost_cd.setBounds(1550, 950, 100, 50);
        boost_cd.setText("Boost CD: " + player.get_boosted_cd());
        hud.add(boost_cd);
        frame.add(boost_cd, JLayeredPane.MODAL_LAYER);
        update_boost_cd();


        //Add the health bar
        health_bar = new JPanel(null);
        hud.add(health_bar);
        frame.add(health_bar, JLayeredPane.MODAL_LAYER);
        ImageIcon healthIcon = new ImageIcon("hp5.png");
        health_image = new JLabel(healthIcon);
        // Scale the image to fit the health_bar dimensions
        int barWidth = 800;
        int barHeight = 40;
        Image scaledImage = healthIcon.getImage().getScaledInstance(barWidth, barHeight, Image.SCALE_SMOOTH);
        health_image.setIcon(new ImageIcon(scaledImage));
        // Position the image manually
        int imageX = 0;
        int imageY = 0; // Adjust this value as needed to align the image correctly
        health_image.setBounds(imageX, imageY, barWidth, barHeight);
        // Add the scaled image to the health_bar panel
        health_bar.add(health_image);
        health_bar.setBounds(500, 950, barWidth, barHeight);
        //screen.add_element("grass_full.png", 0, 0, 1920, 1080, "bg");
        });


    }

    public void update_boost_cd(){
        if(player.get_boosted()){
            boost_cd.setText("Boost CD: Active");
        }
        else if(player.get_boosted_cd() == 0){
            boost_cd.setText("Boost CD: Ready");
        }
        else
        boost_cd.setText("Boost CD: " + player.get_boosted_cd());
    }
    public void update_health(){
    int barWidth = 800;
    int barHeight = 40;
        switch(player.get_health()){
            case 5:
                health_image.setIcon(get_sized_image("hp5.png", barWidth, barHeight));
                break;
            case 4:
                health_image.setIcon(get_sized_image("hp4.png", barWidth, barHeight));
                break;
            case 3:
                health_image.setIcon(get_sized_image("hp3.png", barWidth, barHeight));
                break;
            case 2:
                health_image.setIcon(get_sized_image("hp2.png", barWidth, barHeight));
                break;
            case 1:
                health_image.setIcon(get_sized_image("hp1.png", barWidth, barHeight));
                break;
            case 0:
                health_image.setIcon(get_sized_image("hp0.png", barWidth, barHeight));
                break;
            default:
                health_image.setIcon(get_sized_image("hp0.png", barWidth, barHeight));
                break;
        }
    }



    public ImageIcon get_sized_image(String filename, int width, int height){
        ImageIcon healthIcon = new ImageIcon(filename);
        Image scaledImage = healthIcon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImage);
        // Position the image manually
    }

    public void generate_death_screen(){

        SwingUtilities.invokeLater(() -> {
            JPanel restart_button_panel = new JPanel(null);
            JPanel death_label_panel = new JPanel();
            restart_button_panel.setBounds(890, 650, 100, 50);
            restart_button.setBounds(0, 0, 100, 50);
            restart_button_panel.add(restart_button);
            death_label_panel.setBounds(0,0, 1920, 1080);
            death_label_panel.add(death_label);
            death_screen.add(restart_button_panel, JLayeredPane.POPUP_LAYER);
            death_label.setIcon(get_sized_image("death_screen.png", 1920, 1080));
            death_screen.add(death_label_panel, JLayeredPane.DEFAULT_LAYER);
            death_screen.setVisible(true);
        });
    }

    public void hide_death_screen(){
        update_health();
        death_screen.removeAll();
        death_screen.setVisible(false);
    }

    public JButton get_restart_button(){
        return restart_button;
    }
}
