import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class Game_time extends TimerTask
{
  private int time_out = 10;
  @Override
  public void run() {

    try {
      TimeUnit.SECONDS.sleep(time_out);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

}
