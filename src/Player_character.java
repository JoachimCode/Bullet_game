import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Player_character
{
    int base_ms = 2;
    int height = 100;
    int width = 75;
    int x_cord = 500;
    int y_cord = 500;
    private int min_y = 10;
    private int min_x = 10;
    private int max_x = 1920-width- 10;
    private int max_y = 1080-height -80;

    int movementspeed = 2;
    JFrame root;
    Screen screen;
    Entity_Image player_character;

    private final ReadWriteLock rw_lock = new ReentrantReadWriteLock();

    public Player_character(Screen screen) throws IOException {
        this.screen = screen;
        player_character = new Entity_Image("player_character.png", x_cord, y_cord, width, height, "mc");
    }

    public boolean check_bounds(String direction){
        boolean in_bounds = true;
        if(direction.equals("left") && x_cord < min_x){
            in_bounds = false;
        }
        else if(direction.equals("right") && x_cord > max_x){
            in_bounds = false;
        }
        else if(direction.equals("up") && y_cord < min_y){
            in_bounds = false;
        }
        else if (direction.equals("down") && y_cord > max_y){
            in_bounds = false;
        }
        return in_bounds;
    }

    public void move_up(){
        if(check_bounds("up")) {
            rw_lock.writeLock().lock();
            y_cord = y_cord - movementspeed;
            player_character.setX(x_cord);
            player_character.setY(y_cord);
            screen.refresh_frame();
            rw_lock.writeLock().unlock();
        }

    }

    public void move_down(){
        if(check_bounds("down")) {
            rw_lock.writeLock().lock();
            y_cord = y_cord + movementspeed;
            player_character.setX(x_cord);
            player_character.setY(y_cord);
            screen.refresh_frame();
            rw_lock.writeLock().unlock();
        }
    }

    public void move_left(){
        if(check_bounds("left")) {
            rw_lock.writeLock().lock();
            x_cord = x_cord - movementspeed;
            player_character.setX(x_cord);
            player_character.setY(y_cord);
            screen.refresh_frame();
            rw_lock.writeLock().unlock();
        }
    }

    public void move_right(){
        if(check_bounds("right")) {
            rw_lock.writeLock().lock();
            x_cord = x_cord + movementspeed;
            player_character.setX(x_cord);
            player_character.setY(y_cord);
            screen.refresh_frame();
            rw_lock.writeLock().unlock();
        }
    }

    public void move_up_right(){
        move_right();
        move_up();
    }

    public void move_up_left(){
        move_up();
        move_left();
    }
    public void move_down_left(){
        move_down();
        move_left();
    }
    public void move_down_right(){
        move_down();
        move_right();
    }

    public int get_x(){
        rw_lock.readLock().lock();
        int x = x_cord;
        rw_lock.readLock().unlock();
        return x;
    }
    public int get_y(){
        rw_lock.readLock().lock();
        int y = y_cord;
        rw_lock.readLock().unlock();
        return y;
    }
    public void half_ms(){
        movementspeed = 3;
    }

    public void reset_ms(){
        movementspeed = base_ms;
    }

    public int get_height(){
        return height;
    }

    public int get_width(){
        return width;
    }
    public Entity_Image get_sprite(){
        return player_character;
    }
}
