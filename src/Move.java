import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Move extends Thread {
    volatile private boolean is_up = false;
    volatile private boolean is_down = false;
    volatile private boolean is_left = false;
    volatile private boolean is_right = false;

    private final ReadWriteLock up_lock= new ReentrantReadWriteLock();
    private final ReadWriteLock left_lock = new ReentrantReadWriteLock();
    private final ReadWriteLock right_lock = new ReentrantReadWriteLock();
    private final ReadWriteLock down_lock = new ReentrantReadWriteLock();
    int sleep = 5;
    Player_character character;

    private Thread thread = new Thread(new Runnable() {
        @Override
        public void run() {
            while(true){
                if (get_is_up() && get_is_right()) {
                    SwingUtilities.invokeLater(() -> {
                        character.half_ms();
                        character.move_up_right();
                        character.reset_ms();
                    });
                    try {
                        sleep(sleep);
                    } catch (InterruptedException e) {
                        continue;
                    }
                } else if (get_is_up() && get_is_left()) {
                    SwingUtilities.invokeLater(() -> {
                        character.half_ms();
                        character.move_up_left();
                        character.reset_ms();
                    });
                    try {
                        sleep(sleep);

                    } catch (InterruptedException e) {
                    } finally {
                        continue;
                    }
                } else if (get_is_down() && get_is_right()) {
                    SwingUtilities.invokeLater(() -> {
                        character.half_ms();
                        character.move_down_right();
                        character.reset_ms();
                    });
                    try {
                        sleep(sleep);

                    } catch (InterruptedException e) {
                    } finally {
                        continue;
                    }
                } else if (get_is_down() && get_is_left()) {
                    SwingUtilities.invokeLater(() -> {
                        character.half_ms();
                        character.move_down_left();
                        character.reset_ms();
                    });
                    try {
                        sleep(sleep);

                    } catch (InterruptedException e) {
                    } finally {
                        continue;
                    }
                } else if (get_is_up()) {
                    SwingUtilities.invokeLater(() -> {
                        character.move_up();
                    });
                    try {
                        sleep(sleep);

                    } catch (InterruptedException e) {
                    } finally {
                        continue;
                    }
                } else if (get_is_down()) {
                    SwingUtilities.invokeLater(() -> {
                        character.move_down();
                    });
                    try {
                        sleep(sleep);
                    } catch (InterruptedException e) {
                        continue;
                    }
                } else if (get_is_right()) {
                    SwingUtilities.invokeLater(() -> {
                        character.move_right();
                    });
                    try {
                        sleep(sleep);
                    } catch (InterruptedException e) {
                        continue;
                    }
                } else if (get_is_left()) {
                    SwingUtilities.invokeLater(() -> {
                        character.move_left();
                    });
                    try {
                        sleep(sleep);
                    } catch (InterruptedException e) {
                        continue;
                    }
                }

            }
        }
    });



    public Move(Player_character character, JFrame root){
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
            }
        };
        thread.start();
        root.addKeyListener(listener);
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
