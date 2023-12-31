import java.util.*;
import java.util.Timer;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.swing.*;
import java.util.ArrayList;


import static java.lang.Thread.sleep;

public class Level_generator {
    Hud hud;
    Player_character player_character;
    Screen screen;
    int sleep = 8;
    boolean level_on = true;
    LinkedList<Enemy> list_of_enemies = new LinkedList<>();
    LinkedList<Bullet> list_of_bullets = new LinkedList<>();
    List<Bullet> list_of_player_bullets = new CopyOnWriteArrayList<>();
    Hit_checker hit_checker;
    private int invurnable_time = 150;
    private int invurnable_counter = 0;
    private boolean is_boosted = false;
    private int boosted_time = 450;
    private int boosted_counter = 0;
    private boolean is_hit = false;
    Timer timer = new Timer();



    Level_generator(int level, Screen screen, Player_character playerCharacter, Hud hud){
        this.hud = hud;
        this.player_character = playerCharacter;
        this.screen = screen;
        this.hit_checker = new Hit_checker(list_of_bullets, player_character);
        switch(level){
            case(1):
                level_one();
                break;
        }
    }

    Thread level_one_thread = new Thread(new Runnable() {
        @Override
        public void run() {
                if(check_dead()){
                    set_level_of();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            hud.generate_death_screen();
                        }
                    }, 500);


                }
                is_invurnable();
                Iterator<Enemy> enemies = list_of_enemies.iterator();
                while(enemies.hasNext()){
                    Enemy current_enemy = enemies.next();
                    if(current_enemy.get_hp() <= 0){
                        current_enemy.remove_sprite();
                        enemies.remove();
                    }
                }

                for(Enemy current_enemy:list_of_enemies){
                    SwingUtilities.invokeLater(() -> {
                        current_enemy.movement_algoritm();
                    });
                    current_enemy.engage(current_enemy.get_id());
                    if(hit_checker.check_enemy_collision(current_enemy) && !is_hit){
                        get_hit();
                    }
                }

                Iterator<Bullet> bullets = list_of_bullets.iterator();
                while(bullets.hasNext()){
                    Bullet current_bullet = bullets.next();
                    SwingUtilities.invokeLater(() -> {
                    current_bullet.fire();
                    });
                    if(hit_checker.check_bullet_collision(current_bullet) && !is_hit){
                        get_hit();
                        current_bullet.remove_sprite();
                        bullets.remove();
                    }
                    if(current_bullet.check_hit_end()){
                        current_bullet.remove_sprite();
                        bullets.remove();
                    }
                }
                Iterator<Bullet> player_bullets = player_character.get_player_bullet_list();
                while(player_bullets.hasNext()){
                    Bullet current_bullet = player_bullets.next();
                    SwingUtilities.invokeLater(() -> {
                    current_bullet.fire();
                    });
                }
                List<Bullet> current_list_of_player_bullets = player_character.get_player_bullet_array_list();
                for (int i = 0; i < current_list_of_player_bullets.size(); i++) {
                    Bullet current_bullet = current_list_of_player_bullets.get(i);
                    // Perform any necessary checks and remove the bullet conditionally
                    if (current_bullet.check_hit_end()) {
                        current_bullet.remove_sprite();
                        current_list_of_player_bullets.remove(i);
                        i--; // Decrement i to account for the removed element
                    }
                    for(Enemy enemy:list_of_enemies){
                        if(hit_checker.check_player_bullet_collision(current_bullet, enemy)){
                            System.out.println("Enemy hit");
                            enemy.lose_hp(player_character.get_attack_damage());
                            current_bullet.remove_sprite();
                            current_list_of_player_bullets.remove(i);
                            i--;
                        }
                    }
                }
        }
    });

    public void level_one(){
        reset_level();
        screen.clear_screen();
        screen.add_element("grass_full.png", 0, 0, 1920, 1080, "bg");
        screen.add_to_elements(player_character.get_sprite());
        screen.get_enemies(list_of_enemies);
        add_enemy("player_character.png", 500, 200, 50);
        add_enemy("player_character.png", 200, 600, 50);
        //level_one_thread.start();
    }
    public void add_player_bullet(Bullet bullet){
        list_of_player_bullets.add(bullet);
    }

    public void add_enemy(String image_file_name, int start_x, int start_y, int hp){
        Enemy enemy = new Enemy(screen, list_of_bullets, image_file_name, "enemy", start_x, start_y, hp);
        list_of_enemies.add(enemy);
        screen.add_to_elements(enemy.get_sprite());
    }

    public void reset_level(){
        screen.clear_screen();
        list_of_enemies.clear();
        list_of_player_bullets.clear();
        list_of_bullets.clear();
        player_character.reset_hp();
        player_character.set_x(player_character.get_start_x());
        player_character.set_y(player_character.get_start_y());
        player_character.set_normal_image();
    }

    public void get_hit(){
        player_character.lose_hp();
        SwingUtilities.invokeLater(() -> {
            hud.update_health();
        });
        is_hit = true;
        player_character.set_hit_image();
    }
    public boolean is_invurnable(){
        if (is_hit){
            invurnable_counter++;
            if(invurnable_counter > invurnable_time){
                if(!player_character.get_boosted())
                {
                    player_character.set_normal_image();
                }
                else{
                    player_character.set_boosted_image();
                }
                invurnable_counter = 0;
                is_hit = false;
            }
            return true;
        }
        else{
            return false;
        }
    }



    
    private boolean check_dead(){
        if(player_character.get_health() <= 0){
            return true;
        }
        else{
            return false;
        }
    }

    public boolean get_level_on(){
        return level_on;
    }

    public void set_level_of(){
        level_on = false;
    }
    public void set_level_on(){
        level_on = true;
    }
    public Thread get_level_one_thread(){
        return level_one_thread;
    }
    public Player_character getPlayer_character(){
        return player_character;
    }

    public void update_healt(){
        hud.update_health();
    }

    public void update_boost_cd(){
        hud.update_boost_cd();
    }
}
