package com.padisDefense.game;



import com.badlogic.gdx.utils.Array;
import com.padisDefense.game.Towers.Tower;


/**
 * Every tower is just a tower. The stats and picture is changed accordingly.
 *
 * */
abstract public class TowerPool {

    public final int max;

    public int peak;

    private Array<Tower> freeObjects;

    /** Creates a pool with an initial capacity of 16 and no maximum. */
    public TowerPool () {
        this(16, Integer.MAX_VALUE);
    }

    /** Creates a pool with the specified initial capacity and no maximum. */
    public TowerPool (int initialCapacity) {
        this(initialCapacity, Integer.MAX_VALUE);
    }

    /** @param max The maximum number of free objects to store in this pool. */
    public TowerPool(int initialCapacity, int max) {
        freeObjects = new Array(false, initialCapacity);
        this.max = max;
    }

    abstract protected Tower newObject ();

    //Not sure why level is a parameter.
    //Everything should be level 1.
    public Tower obtain () {
        return freeObjects.size == 0 ? newObject() : freeObjects.pop();
    }


    public void free (Tower object) {
        if (object == null) throw new IllegalArgumentException("object cannot be null.");
        if (freeObjects.size < max) {
            freeObjects.add(object);
            peak = Math.max(peak, freeObjects.size);
        }
        if (object instanceof Poolable) (object).reset();
    }

    public void freeAll (Array<Tower> objects) {
        if (objects == null) throw new IllegalArgumentException("object cannot be null.");
        Array<Tower> freeObjects = this.freeObjects;
        int max = this.max;
        for (int i = 0; i < objects.size; i++) {
            Tower object = objects.get(i);
            if (object == null) continue;
            if (freeObjects.size < max) freeObjects.add(object);
            if (object instanceof Poolable) ((Poolable)object).reset();
        }
        peak = Math.max(peak, freeObjects.size);
    }

    /** Removes all free objects from this pool. */
    public void clear () {
        freeObjects.clear();
    }

    /** The number of objects available to be obtained. */
    public int getFree () {
        return freeObjects.size;
    }

    static public interface Poolable {
        /** Resets the object for reuse. Object references should be nulled and fields may be set to default values. */
        public void reset ();
    }
}
