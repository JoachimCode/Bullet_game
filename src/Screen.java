import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.VolatileImage;
import java.nio.DoubleBuffer;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * This class takes cares of painting all images on ONE canvas. It's main element is the arraylist "elements" which contains
 * all the elements that is to be painted on the Screen. The idea is that a level class will be constructed when a level is generated
 * and it will add all the elements that is to be painted in order in the constructor. After an image has been altered/added the refresh method
 * has to be called to update the canvas with the paint method.
 */
public class Screen extends Canvas {
    Toolkit t = Toolkit.getDefaultToolkit();
    private VolatileImage buffer;
    LinkedList<Entity_Image> elements = new LinkedList<>();
    private final ReadWriteLock rw_lock = new ReentrantReadWriteLock();
    LinkedList<Enemy> list_of_enemies;

    /**
     * This methods makes sure the image does not lag when it is moved.
     * It does that with a buffer that loads in as the new image
     * @param g the specified Graphics context
     */

    @Override
    public void update(Graphics g) {
        if (buffer == null || buffer.validate(getGraphicsConfiguration()) != VolatileImage.IMAGE_OK) {
            buffer = createVolatileImage(getWidth(), getHeight());
        }

        Graphics2D bufferGraphics = buffer.createGraphics();
        paint(bufferGraphics);  // Draw onto the off-screen buffer
        bufferGraphics.dispose();
        g.drawImage(buffer, 0, 0, this);  // Draw the buffer onto the screen
    }

    /**
     * The paint method goes through all the images in the arraylist elements and paints them in order
     * with the graphic tool g. This method itself is not called, but is called when refresh_frame is called.
     * @param g   the specified Graphics context
     */
    @Override
    public void paint(Graphics g){
        super.paint(g);
        rw_lock.readLock().lock();
        for(Entity_Image sprite: elements){
            g.drawImage(sprite.getImage(), sprite.getX(), sprite.getY(), this);
        }
        for(Enemy enemy: list_of_enemies){
            draw_healthbar(g, enemy.get_hp(),enemy.get_x(), enemy.get_y(), enemy.get_max_hp());
        }
        rw_lock.readLock().unlock();
    }
    public Screen(){
        repaint();
    }

    public void draw_healthbar(Graphics g, int health, int x_cord, int y_cord, int max_hp){
        int bar_x = x_cord - 10;
        int bar_y = y_cord - 20;
        int bar_width = 100;
        float current_hp_precentage = (float)health/(float)max_hp;
        int current_width = (int) (bar_width*current_hp_precentage);

        // Set a custom stroke with a thickness of 3
        Stroke customStroke = new BasicStroke(2.0f);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(customStroke);

        g.setColor(Color.RED);
        g.fillRect(bar_x, bar_y, current_width, 5);
        g.setColor(Color.BLACK);
        g.drawRect(bar_x, bar_y, bar_width, 5);

        g2d.setStroke(new BasicStroke(1.0f)); // Reset to default stroke
    }
    public void refresh_frame(){
        repaint();
    }

    public void add_element(String filename, int start_x, int start_y, int width, int height, String id){
        Entity_Image sprite = new Entity_Image(filename, start_x, start_y, width, height, id);
        rw_lock.writeLock().lock();
        elements.add(sprite);
        rw_lock.writeLock().unlock();
    }

//    public ArrayList<Entity_Image> get_elements(){
//        rw_lock.readLock().lock();
//        ArrayList<Entity_Image> temp_elements = elements;
//        rw_lock.readLock().unlock();
//        return elements;
//    }

    public void add_to_elements(Entity_Image element){
        rw_lock.writeLock().lock();
        elements.add(element);
        rw_lock.writeLock().unlock();
    }

    public void remove_from_element(Entity_Image element){
        rw_lock.writeLock().lock();
        elements.remove(element);
        rw_lock.writeLock().unlock();
    }
    public void clear_screen(){
        rw_lock.writeLock().lock();
        elements.clear();
        rw_lock.writeLock().unlock();
        refresh_frame();
    }

    public void get_enemies(LinkedList<Enemy> list_of_enemies)
    {
        this.list_of_enemies = list_of_enemies;
    }



    public Entity_Image get_sprite(String id){
        for (Entity_Image sprite:elements){
            if(id == sprite.getId()){
                return sprite;
            }
        }
        return null;
    }
}
