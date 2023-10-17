import javax.swing.*;
import java.util.ArrayList;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Bullet
{
    private int x;
    private int y;
    private int height;
    private int width;
    private int speed;
    private boolean active;
    private int min_y = 10;
    private int min_x = 10;
    private int max_x = 1920-width- 10;
    private int max_y = 1080-height -80;
    private final ReadWriteLock rw_lock = new ReentrantReadWriteLock();
    private String direction;
    Screen screen;
    private Entity_Image self_sprite;
    public Bullet(Screen screen, String filename, String id, int start_x, int start_y, String direction, int speed){
        this.speed = speed;
        if(direction == "up" || direction == "down"){
            this.height = 40;
            this.width = 20;
        }
        else if(direction == "left" || direction == "right"){
            this.height = 20;
            this.width = 40;
        }
        this.direction = direction;
        this.x = start_x;
        this.y = start_y;
        this.screen = screen;
        self_sprite = new Entity_Image(filename, start_x, start_y, this.width, this.height, id);
    }

    public boolean check_bounds(String direction){
        boolean in_bounds = true;
        if(direction.equals("left") && x < min_x){
            in_bounds = false;
        }
        else if(direction.equals("right") && x > max_x){
            in_bounds = false;
        }
        else if(direction.equals("up") && y < min_y){
            in_bounds = false;
        }
        else if (direction.equals("down") && y > max_y){
            in_bounds = false;
        }
        return in_bounds;
    }

    public void move_up(){
        if(check_bounds("up")) {
            rw_lock.writeLock().lock();
            y = y - speed;
            self_sprite.setX(x);
            self_sprite.setY(y);
            screen.refresh_frame();
            rw_lock.writeLock().unlock();
        }

    }

    public void move_down(){
        if(check_bounds("down")) {
            rw_lock.writeLock().lock();
            y = y + speed;
            self_sprite.setX(x);
            self_sprite.setY(y);
            screen.refresh_frame();
            rw_lock.writeLock().unlock();
        }

    }

    public void move_left(){
        if(check_bounds("left")) {
            rw_lock.writeLock().lock();
            x = x - speed;
            self_sprite.setX(x);
            self_sprite.setY(y);
            screen.refresh_frame();
            rw_lock.writeLock().unlock();
        }
    }

    public void move_right(){
        if(check_bounds("right")) {
            rw_lock.writeLock().lock();
            x = x + speed;
            self_sprite.setX(x);
            self_sprite.setY(y);
            screen.refresh_frame();
            rw_lock.writeLock().unlock();
        }
    }
    public void fire(){
        rw_lock.writeLock().lock();
        if(direction == "down"){
            move_down();
        }
        else if(direction == "left"){
            move_left();
        }
        else if(direction == "up"){
            move_up();
        }
        else if(direction == "right"){
            move_right();
        }
        rw_lock.writeLock().unlock();
    }

    public Entity_Image get_image(){
        return self_sprite;
    }

    public boolean check_hit_end(){
        rw_lock.readLock().lock();
        boolean is_hit = false;
        if (x < min_x || x > max_x || y < min_y || y > max_y-100){
            is_hit = true;
        }
        rw_lock.readLock().unlock();
        return is_hit;
    }

    public void remove_sprite(){
        SwingUtilities.invokeLater(() -> {
        screen.remove_from_element(self_sprite);
        });
    }

    public int get_x(){
        rw_lock.readLock().lock();
        int tempx = x;
        rw_lock.readLock().unlock();
        return tempx;
    }
    public int get_y(){
        rw_lock.readLock().lock();
        int tempy = y;
        rw_lock.readLock().unlock();
        return tempy;
    }

    public int get_height(){
        rw_lock.readLock().lock();
        int temp_height = height;
        rw_lock.readLock().unlock();
        return temp_height;
    }

    public int get_width(){
        rw_lock.readLock().lock();
        int temp_width = width;
        rw_lock.readLock().unlock();
        return temp_width;
    }


}
