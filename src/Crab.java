import processing.core.PImage;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Crab extends EntityMoves {
    private static final String QUAKE_ID = "quake";
    private static final int QUAKE_ACTION_PERIOD = 1100;
    private static final int QUAKE_ANIMATION_PERIOD = 100;
    public static final String QUAKE_KEY = "quake";

    public Crab(String id, Point position,
                  List<PImage> images, int actionPeriod, int animationPeriod)
    {
        super(id, position, images, actionPeriod, animationPeriod);
    }

    protected void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler eventScheduler)
    {
        Optional<Entity> crabTarget = world.findNearest(getPosition(), Sgrass.class);
        long nextPeriod = getActionPeriod();

        if (crabTarget.isPresent())
        {
            Point tgtPos = crabTarget.get().getPosition();

            if (moveTo(world, crabTarget.get(), eventScheduler))
            {
                Entity quake = new Quake(QUAKE_ID, tgtPos,
                        imageStore.getImageList(QUAKE_KEY),
                        QUAKE_ACTION_PERIOD, QUAKE_ANIMATION_PERIOD);

                world.addEntity(quake);
                nextPeriod += getActionPeriod();
                ((EntityAction)quake).scheduleActions(world, imageStore, eventScheduler);
            }
        }

        eventScheduler.scheduleEvent(this,
                createActivityAction(world, imageStore),
                nextPeriod);
    }

    protected void _moveTo(WorldModel world, Entity target, EventScheduler scheduler) {
        world.removeEntity(target);
        scheduler.unscheduleAllEvents(target);
    }

    protected boolean _nextPosition(WorldModel worldModel, Point newPos, Optional<Entity> occupant)
    {
        return occupant.isPresent() && !(occupant.get() instanceof Fish);
    }

    @Override
    public List<Point> computePath(Point start, Point end, Predicate<Point> canPassThrough,
                                   BiPredicate<Point, Point> withinReach, Function<Point,
            Stream<Point>> potentialNeighbors)
    {
        /* Does not check withinReach.  Since only a single step is taken
         * on each call, the caller will need to check if the destination
         * has been reached.
         */
        return potentialNeighbors.apply(start)
                .filter(canPassThrough)
                .filter(pt ->
                        !pt.equals(start)
                                && !pt.equals(end)
                                && Math.abs(end.x - pt.x) <= Math.abs(end.x - start.x)
                                && Math.abs(end.y - pt.y) <= Math.abs(end.y - start.y))
                .limit(1)
                .collect(Collectors.toList());
    }
}
