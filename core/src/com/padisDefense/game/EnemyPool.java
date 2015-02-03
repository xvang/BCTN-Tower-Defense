package com.padisDefense.game;



import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.padisDefense.game.Enemies.Enemy;

/** A pool of objects that can be reused to avoid allocation.
 * @author Nathan Sweet */
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

    abstract protected Enemy newObject (String type);
    abstract protected Enemy newObject (String type, int level, Vector2 spawnPosition);


    public Enemy obtain (String type) {

        for(int x = 0; x < freeObjects.size; x++){
            Enemy e = freeObjects.get(x);

            if( e.getName().equals(type)){
                freeObjects.removeIndex(x);

                return e;
            }

        }

        //if no balloon of the requested type was found, create new one.
        return newObject(type);


       //return freeObjects.pop();//pop() always returns the last. we don't want the last.
       // return freeObjects.size == 0 ? newObject(type) : freeObjects.pop();
    }

    public Enemy obtain (String type, int level, Vector2 spawnPosition) {

        if(freeObjects.size == 0){
            return newObject(type, level, spawnPosition);
        }
        else{
            return freeObjects.pop();
        }
       // return freeObjects.size == 0 ? newObject(type, level, spawnPosition) : freeObjects.pop();
    }

    /** Puts the specified object in the pool, making it eligible to be returned by {@link #obtain(String type)}. If the pool already contains
     * {@link #max} free objects, the specified object is reset but not added to the pool. */
    public void free (Enemy object) {
        if (object == null) throw new IllegalArgumentException("object cannot be null.");
        if (freeObjects.size < max) {
            freeObjects.add(object);
            peak = Math.max(peak, freeObjects.size);
        }
        if (object instanceof Poolable) ((Poolable)object).reset();
    }


    public void freeAll (Array<Enemy> objects) {
        if (objects == null) throw new IllegalArgumentException("object cannot be null.");
        Array<Enemy> freeObjects = this.freeObjects;
        int max = this.max;
        for (int i = 0; i < objects.size; i++) {
            Enemy object = objects.get(i);
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
