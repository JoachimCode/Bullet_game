import java.awt.*;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * This class represents an image that is to be drawn on the screen. It holds
 * an Image class that is needed to be painted by the Screen/Canvas and also the
 * details of the picture, like the coordinates it is to be painted and its dimensions.
 */
public class Entity_Image{
        private String id;
        private Image image;
        private int x;
        private int y;
        private int width;
        private int height;
        Toolkit t = Toolkit.getDefaultToolkit();
        private final ReadWriteLock rw_lock = new ReentrantReadWriteLock();
        public Entity_Image(String filename, int start_x, int start_y, int width, int height, String id){
            image = t.getImage(filename);
            x = start_x;
            y = start_y;
            this.width = width;
            this.height = height;
            this.id =id;

            // Load and resize the image
            Image originalImage = image;
            this.image = originalImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        }

        public Image getImage() {
            rw_lock.readLock().lock();
            Image current_image = image;
            rw_lock.readLock().unlock();
            return current_image;
        }

        public void setImage(Image image) {
            this.image = image;
        }

        public int getX() {
            rw_lock.readLock().lock();
            int current_x = x;
            rw_lock.readLock().unlock();
            return current_x;
        }

        public void setX(int x) {
            rw_lock.writeLock().lock();
            this.x = x;
            rw_lock.writeLock().unlock();
        }

        public int getY() {
            rw_lock.readLock().lock();
            int current_y = y;
            rw_lock.readLock().unlock();
            return current_y;
        }

        public void setY(int y) {
            rw_lock.writeLock().lock();
            this.y = y;
            rw_lock.writeLock().unlock();
        }

        public String getId() {
            rw_lock.readLock().lock();
            String current_id = id;
            rw_lock.readLock().unlock();
            return current_id;
        }

        public void setId(String id) {
            this.id = id;
        }
}
