import java.util.ArrayList;
import java.util.LinkedList;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Enemy {
    private int attack_cooldown = 80;
    private int long_attack_cd = 60;
    private boolean alive = true;
    private int hp;
    private int height = 100;
    private int width = 75;
    private int x_cord;
    private int y_cord;
    private int movementspeed = 3;
    Screen screen;
    private Entity_Image self_sprite;
    int bullet_speed = 1;
    String id;
    private int min_y = 10;
    private int min_x = 10;
    private int max_x = 1920-width- 10;
    private int max_y = 1080-height -80;
    private LinkedList<Bullet> list_of_bullets;
    private final ReadWriteLock rw_lock = new ReentrantReadWriteLock();
    String direction = "left";
    int bullet_counter = 0;


    public Enemy(Screen screen, LinkedList<Bullet> list_of_bullets, String filename, String id, int start_x, int start_y, int hp){
        this.list_of_bullets = list_of_bullets;
        this.hp = hp;
        this.x_cord = start_x;
        this.y_cord = start_y;
        this.screen = screen;
        this.id = id;
        self_sprite = new Entity_Image(filename, start_x, start_y, this.width, this.height, id);
    }

    public Entity_Image get_sprite(){
        return self_sprite;
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

    public void movement_algoritm(){
        if(!check_bounds("left")){
            direction = "right";
        }
        else if(!check_bounds("right")){
            direction = "left";
        }
        if(direction.equals("left")){
            move_left();
        }
        else{
            move_right();
        }

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
    public void move_up(){
        if(check_bounds("up")) {
            rw_lock.writeLock().lock();
            y_cord = y_cord - movementspeed;
            self_sprite.setX(x_cord);
            self_sprite.setY(y_cord);
            screen.refresh_frame();
            rw_lock.writeLock().unlock();
        }

    }

    public void move_down(){
        if(check_bounds("down")) {
            rw_lock.writeLock().lock();
            y_cord = y_cord + movementspeed;
            self_sprite.setX(x_cord);
            self_sprite.setY(y_cord);
            screen.refresh_frame();
            rw_lock.writeLock().unlock();
        }
    }

    public void move_left(){
        if(check_bounds("left")) {
            rw_lock.writeLock().lock();
            x_cord = x_cord - movementspeed;
            self_sprite.setX(x_cord);
            self_sprite.setY(y_cord);
            screen.refresh_frame();
            rw_lock.writeLock().unlock();
        }
    }

    public void move_right(){
        if(check_bounds("right")) {
            rw_lock.writeLock().lock();
            x_cord = x_cord + movementspeed;
            self_sprite.setX(x_cord);
            self_sprite.setY(y_cord);
            screen.refresh_frame();
            rw_lock.writeLock().unlock();
        }
    }


    public void shoot(String direction){
        Bullet bullet;
        switch (direction){
            case("down"):
                bullet = new Bullet(screen, "bullet_down.png", "bullet", x_cord+width/2, y_cord+height, direction,
                    bullet_speed);
                break;
            case("up"):
                bullet = new Bullet(screen, "bullet_up.png", "bullet", x_cord+width/2, y_cord, direction,
                    bullet_speed);
                break;
            case("left"):
                bullet = new Bullet(screen, "bullet_left.png", "bullet", x_cord, y_cord+(height)/2, direction,
                    bullet_speed);
                break;
            case("right"):
                bullet = new Bullet(screen, "bullet_right.png", "bullet", x_cord+width, y_cord+(height)/2, direction,
                    bullet_speed);
                break;
            default:
                bullet = new Bullet(screen, "bullet_down.png", "bullet", x_cord, y_cord+height, direction,
                    bullet_speed);
                break;
        }
        screen.add_to_elements(bullet.get_image());
        list_of_bullets.add(bullet);
    }

    public void attack(){
            if(bullet_counter == attack_cooldown) {
                shoot("down");
                bullet_counter = 0;
            }
            else {
                bullet_counter++;
            }
    }

    public void engage(String id){
        if(alive){
            switch(id){
                case("enemy"):
                    if(bullet_counter == attack_cooldown) {
                        shoot("down");
                        shoot("up");
                        bullet_counter = 0;
                    }
                    else {
                        bullet_counter ++;
                    }
                    break;
            }
        }
    }

    public int get_hp(){
        rw_lock.readLock().lock();
        int temp_hp = hp;
        rw_lock.readLock().unlock();
        return temp_hp;
    }

    public void remove_sprite(){
        rw_lock.writeLock().lock();
        screen.remove_from_element(self_sprite);
        rw_lock.writeLock().unlock();
    }

    public void lose_hp(){
        rw_lock.writeLock().lock();
        hp--;
        rw_lock.writeLock().unlock();
    }

    public String get_id(){
        return id;
    }
}
