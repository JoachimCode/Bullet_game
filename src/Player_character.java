import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.swing.*;
import java.io.IOException;
import java.util.Iterator;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;


public class Player_character
{
    Game_time timer;
    int attack_damage = 1;
    int base_ad = 1;
    int base_bullet_speed = 5;
    int bullet_speed = 5;
    int attack_cooldown = 30;
    int base_ms = 2;
    private int current_health = 5;
    private int max_health = 5;
    int height = 80;
    int width = 60;
    final int start_x = 500;
    final int start_y = 500;
    int x_cord = 500;
    int y_cord = 500;
    private int min_y = 10;
    private int min_x = 10;
    private int max_x = 1920-width- 10;
    private int max_y = 1080-height -80;
    private int max_y_hud = 880 - height;
    private Entity_Image hp_bar;
    private List<Bullet> list_of_player_bullets = new CopyOnWriteArrayList<>();
    private boolean is_boosted = false;
    private boolean is_boosted_cd = false;
    private int boosted_cd;
    private int boosted_base_cd = 10;


    int movementspeed = 2;
    JFrame root;
    Screen screen;
    Entity_Image player_character;

    private final ReadWriteLock rw_lock = new ReentrantReadWriteLock();

    public Player_character(Screen screen) throws IOException {
        this.screen = screen;
        player_character = new Entity_Image("player_character.png", x_cord, y_cord, width, height, "mc");
        hp_bar = new Entity_Image("hp5.png",460,980, 1000, 35, "hp_bar");

    }

    public void shoot(String direction){
        rw_lock.readLock().lock();
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
        list_of_player_bullets.add(bullet);
        rw_lock.readLock().unlock();
    }

    public Iterator<Bullet> get_player_bullet_list(){
        rw_lock.readLock().lock();
        Iterator<Bullet> player_bullet_list = list_of_player_bullets.iterator();
        rw_lock.readLock().unlock();
        return player_bullet_list;
    }

    public List<Bullet> get_player_bullet_array_list(){
        rw_lock.readLock().lock();
        List<Bullet> temp_list = list_of_player_bullets;
        rw_lock.readLock().unlock();
        return temp_list;
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
        else if (direction.equals("down") && y_cord > max_y_hud){
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

    public int get_start_x(){
        return start_x;
    }

    public int get_start_y(){
        return start_y;
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
    public void swing_slow(){
        movementspeed = movementspeed ;
    }

    public void swing_reset(){
        movementspeed = base_ms;
    }

    public void reset_ms(){
        movementspeed = base_ms;
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
    public int get_health(){
        rw_lock.readLock().lock();
        int hp = current_health;
        rw_lock.readLock().unlock();
        return hp;
    }

    public void lose_hp(){
        rw_lock.writeLock().lock();
        current_health = current_health - 1;
        rw_lock.writeLock().unlock();
        screen.refresh_frame();
    }
    public Entity_Image get_hp_bar(){
        rw_lock.readLock().lock();
        Entity_Image hp_bar_temp = hp_bar;
        rw_lock.readLock().unlock();
        return hp_bar_temp;
    }

    public Entity_Image get_sprite(){
        return player_character;
    }
    public void set_hit_image(){
        rw_lock.writeLock().lock();
        player_character.change_image("player_character_hit.png");
        rw_lock.writeLock().unlock();
    }

    public void set_normal_image(){
        rw_lock.writeLock().lock();
        player_character.change_image("player_character.png");
        rw_lock.writeLock().unlock();
    }

    public void reset_hp(){
        rw_lock.writeLock().lock();
        current_health = max_health;
        rw_lock.writeLock().unlock();
    }
    public void set_x(int x){
        rw_lock.writeLock().lock();
        x_cord = x;
        player_character.setX(x_cord);
        rw_lock.writeLock().unlock();
    }

    public void set_y(int y){
        rw_lock.writeLock().lock();
        y_cord = y;
        player_character.setY(y_cord);
        rw_lock.writeLock().unlock();
    }
    public int get_attack_speed(){
        return attack_cooldown;
    }

    public int get_attack_damage(){
        return attack_damage;
    }
    public void set_attack_damage(int damage){
        attack_damage = damage;
    }

    public void boost(){
        rw_lock.writeLock().lock();
        set_boosted(true);
        movementspeed += 2;
        base_ms += 2;
        bullet_speed += 2;
        attack_damage += 1;
        set_hit_image();
        rw_lock.writeLock().unlock();
    }

    public void reset_boost(){
        rw_lock.writeLock().lock();
        base_ms -= 2;
        set_boosted(false);
        movementspeed -= 2;
        bullet_speed -= 2;
        attack_damage -= 1;
        set_normal_image();
        set_boosted_cd(boosted_base_cd);
        timer.set_timer("boost_cd");
        rw_lock.writeLock().unlock();
    }

    public void set_boosted(boolean is_boosted){
        rw_lock.writeLock().lock();
        this.is_boosted = is_boosted;
        rw_lock.writeLock().unlock();
    }
    public boolean get_boosted(){
        boolean temp_is_boosted = is_boosted;
        return temp_is_boosted;

    }

    public void set_boosted_image(){
        rw_lock.writeLock().lock();
        player_character.change_image("player_character_hit.png");
        rw_lock.writeLock().unlock();
    }

    public void set_boosted_cd(int boosted_cd){
        rw_lock.writeLock().lock();
        this.boosted_cd = boosted_cd;
        rw_lock.writeLock().unlock();
    }

    public int get_boosted_cd(){
        int temp_boosted_cd = boosted_cd;
        return temp_boosted_cd;
    }
    public boolean is_on_boosted_cd(){
        boolean temp_bool = false;
        if((boosted_cd > 0)){
            temp_bool = true;
        }
        else{
            temp_bool = false;
        }
        return temp_bool;
    }

    public void setTimer(Game_time timer){
        this.timer = timer;
    }

    public int get_boosted_base_cd(){
        int temp = boosted_base_cd;
        return temp;
    }
}
