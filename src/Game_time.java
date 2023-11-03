import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

public class Game_time
{
  private Timer timer;
  private Level_generator level;
  private Move move;
  public Game_time(Level_generator level, Move move)
  {
    this.level = level;
    this.move = move;
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
      move.get_thread().run();
      level.get_level_one_thread().run();
    }
  }
}
