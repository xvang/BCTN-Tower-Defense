package com.padisDefense.game;


import com.badlogic.gdx.utils.Array;
import com.padisDefense.game.Enemies.Enemy;



//TODO: No need for two pools. Get rid of them and use the regular pool.
abstract public class EnemyPool {
    /** The maximum number of objects that will be pooled. */
    public final int max;
    /** The highest number of free objects. Can be reset any time. */
    public int peak;

    private final Array<Enemy> freeObjects;

    public Array<Enemy> returnPool(){return freeObjects;}

    /** Creates a pool with an initial capacity of 16 and no maximum. */
    public EnemyPool() {
        this(16, Integer.MAX_VALUE);
    }

    /** Creates a pool with the specified initial capacity and no maximum. */
    public EnemyPool(int initialCapacity) {
        this(initialCapacity, Integer.MAX_VALUE);
    }

    /** @param max The maximum number of free objects to store in this pool. */
    public EnemyPool(int initialCapacity, int max) {
        freeObjects = new Array(false, initialCapacity);
        this.max = max;
    }

    abstract protected Enemy newObject ();

    public Enemy obtain () {

        return freeObjects.size == 0 ? newObject() : freeObjects.pop();
    }


    public void free (Enemy object) {
        if (object == null) throw new IllegalArgumentException("object cannot be null.");
        if (freeObjects.size < max) {
            freeObjects.add(object);
            peak = Math.max(peak, freeObjects.size);
        }
        if (object instanceof Poolable) (object).reset();
    }


    public void freeAll (Array<Enemy> objects) {
        if (objects == null) throw new IllegalArgumentException("object cannot be null.");
        Array<Enemy> freeObjects = this.freeObjects;
        int max = this.max;
        for (int i = 0; i < objects.size; i++) {
            Enemy object = objects.get(i);
            if (object == null) continue;
            if (freeObjects.size < max) freeObjects.add(object);
            if (object instanceof Poolable) (object).reset();
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
