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


    Level_generator(int level, Screen screen, Player_character playerCharacter){
        this.player_character = playerCharacter;
        this.screen = screen;
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
                    if(current_bullet.check_hit_end()){
                        current_bullet.remove_sprite();
                        bullets.remove();
                    }
                }
                if(check_bullet_hit()){
                    System.out.println("HIT");
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
    public boolean check_bullet_hit(){
        boolean is_hit = false;
        for(Bullet current_bullet:list_of_bullets){
            if (
                    current_bullet.get_x() < player_character.get_x() + player_character.get_width() &&
                    current_bullet.get_x() + current_bullet.get_width() > player_character.get_x() &&
                    current_bullet.get_y() < player_character.get_y() + player_character.get_height() &&
                    current_bullet.get_y() + current_bullet.get_height() > player_character.get_y()
             )
            {
                is_hit = true;
            }
        }
        return is_hit;
    }
    public void level_one(){
        screen.clear_screen();
        screen.add_element("grass_full.png", 0, 0, 1920, 1080, "bg");
        screen.add_to_elements(player_character.get_hp_bar());
        screen.add_to_elements(player_character.get_sprite());
        Enemy enemy = new Enemy(screen, list_of_bullets, "enemy.png", "enemy", 500, 200, 100);
        list_of_enemies.add(enemy);
        screen.add_to_elements(enemy.get_sprite());
        level_one_thread.start();
    }


}
