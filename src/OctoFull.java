import processing.core.PImage;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class OctoFull extends EntityOcto {


    public OctoFull( String id, Point position, List<PImage> images,
                     int resourceLimit, int actionPeriod, int animationPeriod)
    {
        super(id, position, images, resourceLimit, actionPeriod, animationPeriod);
    }

    protected void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler eventScheduler)
    {
        Optional<Entity> fullTarget = world.findNearest(getPosition(), Atlantis.class);

        if (fullTarget.isPresent() &&
                moveTo(world, fullTarget.get(), eventScheduler))
        {
            //at atlantis trigger animation
            ((EntityAction)fullTarget.get()).scheduleActions(world, imageStore, eventScheduler);

            //transform to unfull
            transform(world, eventScheduler, imageStore);
        }
        else
        {
            eventScheduler.scheduleEvent(this,
                    createActivityAction(world, imageStore),
                    getActionPeriod());
        }
    }

    @Override
    protected Entity _transform(WorldModel world, EventScheduler scheduler, ImageStore imageStore) {
        return new OctoNotFull(this.getId(), this.getPosition(), this.getImages(),
                this.getResourceLimit(), 0, this.getActionPeriod(), this.getAnimationPeriod());
    }

    @Override
    protected void _moveTo(WorldModel world, Entity target, EventScheduler scheduler)
    {
    }

    @Override
    protected boolean _nextPosition(WorldModel worldModel, Point newPos, Optional<Entity> occupant)
    {
        return worldModel.isOccupied(newPos);
    }

    @Override
    public List<Point> computePath(Point start, Point end, Predicate<Point> canPassThrough, BiPredicate<Point, Point> withinReach, Function<Point, Stream<Point>> potentialNeighbors) {
        return null;
    }
}
