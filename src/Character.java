import processing.core.PImage;

import java.util.List;
import java.util.Optional;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class Character extends EntityMoves{

    public Character(String id, Point position, List<PImage> images, int actionPeriod, int animationPeriod)
    {
        super(id, position, images, actionPeriod, animationPeriod);
    }

    @Override
    protected Point nextPosition(Point destPos, WorldModel world) {
        return null;
    }

    @Override
    protected boolean generatePath(Point pos, Point goal, WorldModel world) {
        return false;
    }

    @Override
    protected void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler eventScheduler)
    {
        long nextPeriod = getActionPeriod();
        eventScheduler.scheduleEvent(this, createActivityAction(world, imageStore), nextPeriod);
    }

    @Override
    protected void _moveTo(WorldModel world, Entity target, EventScheduler scheduler)
    {

    }
}
