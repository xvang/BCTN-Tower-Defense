package com.padisDefense.game;



import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.padisDefense.game.Towers.Tower;


/**The reason for a TowerPool (instead of using CustomPool)
 * is so I can check the level of the tower. I tried implementing
 * the tower pool like the enemy pool, but the towers were all messed up,
 * and the levels were disregarded. For example, if I initially pool 3 of
 * each tower, there should be 3*[6 towers * 3 levels per] 24 total. But
 * only 6 was created. I don't know. I hope this way works.
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

    abstract protected Tower newObject (String type, int level, Vector2 spawnPosition);

    public Tower obtain (String type, int level, Vector2 spawnPosition) {

        if(freeObjects.size == 0){
            return newObject(type, level, spawnPosition);
        }
        else{//check to see if desired tower is in pool.


            int i = inPool(type, level);
            if(i >= 0){//if i > 0, then it is in pool.
                Tower temp = freeObjects.get(i);
                temp.setPosition(spawnPosition.x, spawnPosition.y);
                freeObjects.removeIndex(i);
                return temp;
            }
            else{//not in pool.
                return newObject(type, level, spawnPosition);
            }

        }
        // return freeObjects.size == 0 ? newObject(type, level, spawnPosition) : freeObjects.pop();
    }


    public void free (Tower object) {
        if (object == null) throw new IllegalArgumentException("object cannot be null.");
        if (freeObjects.size < max) {
            freeObjects.add(object);
            peak = Math.max(peak, freeObjects.size);
        }
        if (object instanceof Poolable) ((Poolable)object).reset();
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


    //checks if a requested tower is already in pool.
    //checks the name and level.
    //returns the position of the tower already in pool, or -1.
    private int inPool(String type, int level){

        for(int x = 0; x < freeObjects.size; x++){
            if(freeObjects.get(x).getID().equals(type)){
                return x;
                //for now the level is not tested.
                /*if(freeObjects.get(x).getLevel() == level){
                    return x;
                }*/
            }
        }
        return -1;
    }
}
