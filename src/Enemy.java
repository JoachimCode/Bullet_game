import java.util.ArrayList;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Enemy {
    private boolean alive = true;
    private int hp;
    private int height = 100;
    private int width = 75;
    private int x_cord;
    private int y_cord;
    private int movementspeed = 4;
    Screen screen;
    private Entity_Image self_sprite;
    String id;
    private int min_y = 10;
    private int min_x = 10;
    private int max_x = 1920-width- 10;
    private int max_y = 1080-height -80;
    private ArrayList<Bullet> list_of_bullets;
    private final ReadWriteLock rw_lock = new ReentrantReadWriteLock();
    String direction = "left";
    int bullet_counter = 0;


    public Enemy(Screen screen, ArrayList<Bullet> list_of_bullets, String filename, String id, int start_x, int start_y, int hp){
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


    public void shoot(){
        Bullet bullet = new Bullet(screen, "bullet.png", "bullet", x_cord, y_cord+height, "down");
        screen.add_to_elements(bullet.get_image());
        list_of_bullets.add(bullet);
    }


    public void engage(String id){
        if(alive){
            switch(id){
                case("enemy"):
                    if(bullet_counter == 20) {
                        shoot();
                        bullet_counter = 0;
                    }
                    else {
                        bullet_counter ++;
                    }
                    break;
            }
        }
    }

    public String get_id(){
        return id;
    }
}
