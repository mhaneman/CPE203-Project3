import java.util.List;
import processing.core.PImage;

/*
Entity ideally would includes functions for how all the entities in our virtual world might act...
 */


public abstract class Entity
{
    private String id;
    private Point position;
    private List<PImage> images;
    private int imageIndex;

    public Entity(String id, Point position, List<PImage> images)
    {
        this.id = id;
        this.position = position;
        this.images = images;
    }

    protected void nextImage()
    {
        this.imageIndex = ((this.imageIndex + 1) % this.images.size());
    }
    protected PImage getCurrentImage()
    {
        return this.images.get(this.imageIndex);
    }
    protected String getId() {
        return id;
    }
    protected Point getPosition() {
        return position;
    }
    protected void setPosition(Point position) {
        this.position = position;
    }
    public List<PImage> getImages() {
        return images;
    }
}
