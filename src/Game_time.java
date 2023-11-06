import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

public class Game_time
{
  private Timer timer;
  private Level_generator level;
  private Move move;
  private Player_character character;
  private Screen screen;
  public Game_time(Level_generator level, Move move, Player_character character, Screen screen)
  {
    this.screen = screen;
    this.character = character;
    this.level = level;
    this.move = move;
    move.set_game_time(this);
    character.setTimer(this);
    timer = new Timer();
    TimerTask task = new TimerTask() {

      @Override
      public void run() {
        run_all();
      }
    };
    //Wait 1 second before first execution and then
    //execute every second
    timer.schedule(task, 1000,5);

  }

  public void run_all(){
    if (level.get_level_on()) {
      screen.refresh_frame();
      move.get_thread().run();
      level.get_level_one_thread().run();
    }
  }

  /*
    * Sets a timer for a specific event
    * Boost for boost
   */
  public void set_timer(String event){
    Timer temp_timer = new Timer();
    switch (event){
      case("boost"):
        temp_timer.schedule(new TimerTask() {
          @Override
          public void run() {
            level.getPlayer_character().set_boosted(false);

            level.getPlayer_character().reset_boost();
          }
        }, 3000);

        break;
        case("boost_cd"):
          temp_timer.schedule(new TimerTask() {
            private int seconds_passed = 0;
            private int cd = character.get_boosted_base_cd();
            @Override
            public void run() {
              if(seconds_passed < cd){
                level.update_boost_cd();
                seconds_passed++;
                System.out.println("boost cd: " + character.get_boosted_cd());
                character.set_boosted_cd(character.get_boosted_cd() - 1);

              }
              else if(seconds_passed == cd){
                level.update_boost_cd();
                character.set_boosted_cd(0);
                System.out.println("boost done");
                cancel();
              }
            }
          }, 1000, 1000);
          break;
  }
}
}




