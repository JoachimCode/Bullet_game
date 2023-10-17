import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {

        JFrame root = new JFrame();
        //root.setLayout(new BorderLayout());
        JLayeredPane layeredPane = new JLayeredPane();
        Screen screen = new Screen();
        root.add(layeredPane);
        screen.setBounds(0, 0, 1920, 1080);
        layeredPane.add(screen, JLayeredPane.DEFAULT_LAYER);

        JPanel hud = new JPanel();
        hud.setBounds(0,0, 1000, 500);
        hud.setVisible(true);
        hud.setBackground(Color.red);
        layeredPane.add(hud, JLayeredPane.PALETTE_LAYER);

        Player_character character = new Player_character( screen);
        Level_generator level_one = new Level_generator(1, screen, character);
        Move listener = new Move(character, root, screen);
        screen.refresh_frame();
        root.setTitle("Game");
        root.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        root.setSize(1920, 1080);
        root.setVisible(true);
        SwingUtilities.invokeLater(() -> {
            screen.requestFocus();
        });
    }
}