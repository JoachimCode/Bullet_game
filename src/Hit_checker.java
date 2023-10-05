import java.util.ArrayList;

public class Hit_checker {
  Player_character player_character;
  ArrayList<Bullet> list_of_bullets;
  public Hit_checker(ArrayList<Bullet> list_of_bullets, Player_character player_character){
    this.player_character = player_character;
    this.list_of_bullets = list_of_bullets;
  }
}
