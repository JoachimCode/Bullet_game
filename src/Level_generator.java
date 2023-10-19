import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.swing.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

import static java.lang.Thread.sleep;

public class Level_generator {
    Player_character player_character;
    Screen screen;
    int sleep = 8;
    boolean level_on = true;
    LinkedList<Enemy> list_of_enemies = new LinkedList<>();
    LinkedList<Bullet> list_of_bullets = new LinkedList<>();
    List<Bullet> list_of_player_bullets = new CopyOnWriteArrayList<>();
    Hit_checker hit_checker;


    Level_generator(int level, Screen screen, Player_character playerCharacter){
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
            while(level_on){
                for(Enemy current_enemy:list_of_enemies){
                    SwingUtilities.invokeLater(() -> {
                        current_enemy.movement_algoritm();
                    });
                    current_enemy.engage(current_enemy.get_id());
                }

                Iterator<Bullet> bullets = list_of_bullets.iterator();
                while(bullets.hasNext()){
                    Bullet current_bullet = bullets.next();
                    SwingUtilities.invokeLater(() -> {
                    current_bullet.fire();
                    });
                    if(hit_checker.check_bullet_collision(current_bullet)){
                        System.out.println("hit");
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
                            current_bullet.remove_sprite();
                            current_list_of_player_bullets.remove(i);
                            i--;
                        }
                    }
                }
                try {
                    sleep(sleep);
                } catch (InterruptedException e) {
                    continue;
                }
            }
        }
    });

    public void level_one(){
        reset_level();
        screen.clear_screen();
        screen.add_element("grass_full.png", 0, 0, 1920, 1080, "bg");
        screen.add_to_elements(player_character.get_sprite());
        add_enemy("player_character.png", 500, 200, 100);
        level_one_thread.start();
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
    }

}
