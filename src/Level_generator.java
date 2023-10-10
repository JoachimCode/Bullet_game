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


//                for(Bullet current_bullet:list_of_bullets){
//                    SwingUtilities.invokeLater(() -> {
//                    current_bullet.fire();
//                    });
//                    if(current_bullet.check_hit_end()){
//                        current_bullet.remove_sprite();
//                    }
//                }
                try {
                    sleep(sleep);
                } catch (InterruptedException e) {
                    continue;
                }
            }
        }
    });

    public void level_one(){
        screen.clear_screen();
        screen.add_element("grass_full.png", 0, 0, 1920, 1080, "bg");
        screen.add_to_elements(player_character.get_hp_bar());
        screen.add_to_elements(player_character.get_sprite());
        Enemy enemy = new Enemy(screen, list_of_bullets, "player_character.png", "enemy", 500, 200, 100);
        list_of_enemies.add(enemy);
        screen.add_to_elements(enemy.get_sprite());
        level_one_thread.start();
    }


}
