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
    protected void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler eventScheduler)
    {
        long nextPeriod = getActionPeriod();
        eventScheduler.scheduleEvent(this, createActivityAction(world, imageStore), nextPeriod);
    }

    @Override
    protected boolean _nextPosition(WorldModel worldModel, Point newPos, Optional<Entity> occupant)
    {
        return true;
    }

    @Override
    protected void _moveTo(WorldModel world, Entity target, EventScheduler scheduler)
    {

    }

    @Override
    public List<Point> computePath(Point start, Point end, Predicate<Point> canPassThrough, BiPredicate<Point, Point> withinReach, Function<Point, Stream<Point>> potentialNeighbors) {
        return null;
    }
}
