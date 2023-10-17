import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Move extends Thread {
    int bullet_counter = 0;

    volatile private boolean is_up = false;
    volatile private boolean is_down = false;
    volatile private boolean is_left = false;
    volatile private boolean is_right = false;

    private final ReadWriteLock up_lock= new ReentrantReadWriteLock();
    private final ReadWriteLock left_lock = new ReentrantReadWriteLock();
    private final ReadWriteLock right_lock = new ReentrantReadWriteLock();
    private final ReadWriteLock down_lock = new ReentrantReadWriteLock();
    volatile private boolean is_w = false;
    volatile private boolean is_a = false;
    volatile private boolean is_s = false;
    volatile private boolean is_d = false;

    private final ReadWriteLock w_lock= new ReentrantReadWriteLock();
    private final ReadWriteLock a_lock = new ReentrantReadWriteLock();
    private final ReadWriteLock s_lock = new ReentrantReadWriteLock();
    private final ReadWriteLock d_lock = new ReentrantReadWriteLock();
    int sleep = 5;
    Player_character character;

    private Thread thread = new Thread(new Runnable() {
        @Override
        public void run() {
            while(true){
                if (is_w){
                    if(bullet_counter == character.get_attack_speed()) {
                        SwingUtilities.invokeLater(() -> {
                            character.shoot("up");
                        });
                        bullet_counter = 0;
                    }
                    else {
                        bullet_counter++;
                    }
                }
                else if(is_d){
                    if(bullet_counter == character.get_attack_speed()) {
                        SwingUtilities.invokeLater(() -> {
                            character.shoot("right");
                        });
                        bullet_counter = 0;
                    }
                    else {
                        bullet_counter++;
                    }
                }
                else if(is_a){
                    if(bullet_counter == character.get_attack_speed()) {
                        SwingUtilities.invokeLater(() -> {
                            character.shoot("left");
                        });
                        bullet_counter = 0;
                    }
                    else {
                        bullet_counter++;
                    }
                }
                else if(is_s){
                    if(bullet_counter == character.get_attack_speed()) {
                        SwingUtilities.invokeLater(() -> {
                            character.shoot("down");
                        });
                        bullet_counter = 0;
                    }
                    else {
                        bullet_counter++;
                    }
                }
                 if (get_is_up() && get_is_right()) {
                    SwingUtilities.invokeLater(() -> {
                        character.half_ms();
                        character.move_up_right();
                        character.reset_ms();
                    });
                } else if (get_is_up() && get_is_left()) {
                    SwingUtilities.invokeLater(() -> {
                        character.half_ms();
                        character.move_up_left();
                        character.reset_ms();
                    });
                } else if (get_is_down() && get_is_right()) {
                    SwingUtilities.invokeLater(() -> {
                        character.half_ms();
                        character.move_down_right();
                        character.reset_ms();
                    });
                } else if (get_is_down() && get_is_left()) {
                    SwingUtilities.invokeLater(() -> {
                        character.half_ms();
                        character.move_down_left();
                        character.reset_ms();
                    });
                } else if (get_is_up()) {
                    SwingUtilities.invokeLater(() -> {
                        character.move_up();
                    });
                } else if (get_is_down()) {
                    SwingUtilities.invokeLater(() -> {
                        character.move_down();
                    });
                } else if (get_is_right()) {
                    SwingUtilities.invokeLater(() -> {
                        character.move_right();
                    });
                } else if (get_is_left()) {
                    SwingUtilities.invokeLater(() -> {
                        character.move_left();
                    });
                }
                try {
                    sleep(sleep);

                } catch (InterruptedException e) {
                }
            }
        }
    });

    public Move(Player_character character, JFrame root, Screen screen){
        this.character = character;
        KeyListener listener;
        listener = new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_UP) {
                    is_up = true;
                }
                else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    is_down = true;
                }
                else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    is_left = true;
                }
                else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    is_right = true;
                }
                else if (e.getKeyCode() == KeyEvent.VK_W) {
                    is_w = true;
                }
                else if (e.getKeyCode() == KeyEvent.VK_A) {
                    is_a = true;
                }
                else if (e.getKeyCode() == KeyEvent.VK_S) {
                    is_s = true;
                }
                else if (e.getKeyCode() == KeyEvent.VK_D) {
                    is_d = true;
                }

            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_UP) {
                    is_up = false;
                }
                else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    is_down = false;
                }
                else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    is_left = false;
                }
                else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    is_right = false;
                }
                else if (e.getKeyCode() == KeyEvent.VK_W) {
                    is_w = false;
                }
                else if (e.getKeyCode() == KeyEvent.VK_A) {
                    is_a = false;
                }
                else if (e.getKeyCode() == KeyEvent.VK_S) {
                    is_s = false;
                }
                else if (e.getKeyCode() == KeyEvent.VK_D) {
                    is_d = false;
                }
            }
        };
        thread.start();
        screen.addKeyListener(listener);

    }


    private boolean get_is_up(){
        up_lock.readLock().lock();
        boolean temp = is_up;
        up_lock.readLock().unlock();
        return temp;
    }

    private boolean get_is_down(){
        down_lock.readLock().lock();
        boolean temp = is_down;
        down_lock.readLock().unlock();
        return temp;
    }
    private boolean get_is_left(){
        left_lock.readLock().lock();
        boolean temp = is_left;
        left_lock.readLock().unlock();
        return temp;
    }
    private boolean get_is_right(){
        right_lock.readLock().lock();
        boolean temp = is_right;
        right_lock.readLock().unlock();
        return temp;
    }




}
