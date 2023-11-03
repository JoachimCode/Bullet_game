import javax.swing.JButton;
import javax.swing.SwingUtilities;

public class Buttons {
  Hud hud;
  Level_generator levelGenerator;
  Game_time game_time;
  JButton restart_button;
  Screen screen;
  public Buttons(Hud hud, Level_generator levelGenerator, Game_time game_time, Screen screen) {
    this.screen = screen;
    this.hud = hud;
    this.levelGenerator = levelGenerator;
    this.game_time = game_time;
    restart_button = hud.get_restart_button();
    restart_button.addActionListener(e -> {
      SwingUtilities.invokeLater(() -> {
        hud.hide_death_screen();
      });
      levelGenerator.set_level_on();
      levelGenerator.level_one();
      screen.requestFocus();
    });
  }


}
