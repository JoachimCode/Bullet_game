import java.util.ArrayList;
import java.util.LinkedList;

public class Hit_checker {
  Player_character player_character;
  LinkedList<Bullet> list_of_bullets;
  public Hit_checker(LinkedList<Bullet> list_of_bullets, Player_character player_character){
    this.player_character = player_character;
    this.list_of_bullets = list_of_bullets;
  }
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
  public boolean check_bullet_collision(Bullet current_bullet){
      return
              current_bullet.get_x() < player_character.get_x() + player_character.get_width() &&
                      current_bullet.get_x() + current_bullet.get_width() > player_character.get_x() &&
                      current_bullet.get_y() < player_character.get_y() + player_character.get_height() &&
                      current_bullet.get_y() + current_bullet.get_height() > player_character.get_y();


  }
}
