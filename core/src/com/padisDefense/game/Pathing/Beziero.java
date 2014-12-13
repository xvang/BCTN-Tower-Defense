package com.padisDefense.game.Pathing;



import com.badlogic.gdx.math.Bezier;
import com.badlogic.gdx.math.Vector;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.GdxRuntimeException;

/**
 * I may have planned on doing this, but I've realized why
 * they stopped at the third degree. Maybe in the future
 * if I have too much time on my hands, I'll try it out.
 * But for now, the only reason it's not being deleted is because
 * I already included it in the reports. It will be gone soon.
 *
 *
 *
 * **/
public class Beziero<T extends Vector<T>> extends Bezier {

    public Array<T> points = new Array<T>();
    public T tmp, tmp2, tmp3, tmp4, tmp5, tmp6;


    /** CONSTRUCTOR **/
    //Will only pass in Vector2()'s, so no need to have
    //multiple constructors like in Bezier.
    public Beziero(final T... points){
        System.out.println("IN CONST BZO");
        set2(points, 0, points.length);
    }

    public Bezier set2(final T[] points, final int offset, final int length){

        System.out.println("IN SET BZO");
        if (length < 2 || length > 7)
            throw new GdxRuntimeException("You messed up somewhere. Try again.");
        if (tmp == null) tmp = points[0].cpy();
        if (tmp2 == null) tmp2 = points[0].cpy();
        if (tmp3 == null) tmp3 = points[0].cpy();
        if (tmp4 == null) tmp4 = points[0].cpy();
        if (tmp5 == null) tmp5 = points[0].cpy();
        if (tmp6 == null) tmp6 = points[0].cpy();

        this.points.clear();
        this.points.addAll(points, offset, length);
        return this;


    }

    public Bezier set2(final Array<T> points, final int offset, final int length){

        if (length < 5 || length > 7)
            throw new GdxRuntimeException("You messed up somewhere. Try again.");
        if (tmp == null) tmp = points.get(0).cpy();
        /*if (tmp2 == null) tmp2 = points.get(0).cpy();
        if (tmp3 == null) tmp3 = points.get(0).cpy();
        if (tmp4 == null) tmp4 = points.get(0).cpy();
        if (tmp5 == null) tmp5 = points.get(0).cpy();
        if (tmp6 == null) tmp6 = points.get(0).cpy();*/

        this.points.clear();
        this.points.addAll(points, offset, length);
        return this;


    }






    public T valueAt2(final T out, final float t){
        final int n = points.size;

        if (n == 2) {
            System.out.println("Linear EQ.");
            linear(out, t, points.get(0), points.get(1), tmp);
        }
        else if (n == 3)
            quadratic(out, t, points.get(0), points.get(1), points.get(2), tmp);
        else if (n == 4) cubic(out, t, points.get(0), points.get(1), points.get(2), points.get(3), tmp);

        else if(n == 5)
            quartic(out, t, points.get(0), points.get(1), points.get(2),
                    points.get(3), points.get(4), tmp);

        else if(n == 6)
            quintic(out, t, points.get(0), points.get(1), points.get(2),
                    points.get(3), points.get(4), points.get(5), tmp);


        else if(n == 7)
            sextic(out, t, points.get(0), points.get(1), points.get(2),
                    points.get(3), points.get(4),points.get(5), points.get(6),tmp);


        return out;
    }


    public static<T extends Vector<T>> T quartic(final T out, final  float t,  final T p0,
                                                 final T p1,  final T p2,  final T p3,  final T p4,
                                                 final T tmp){

        System.out.println("IN QUARTIC");
        return out.set(p0).scl(1f - t).add(tmp.set(p1).scl(t));
        //return null;
    }

    public static<T extends Vector<T>> T quintic(final T out, final float t,  final T p0,
                                                 final T p1,  final T p2,  final T p3, final T p4,
                                                 final T p5, final T tmp){

        return null;
    }

    public static <T extends Vector<T>> T sextic(final T out, final float t,  final T p0,
                                                 final T p1,  final T p2,  final T p3, final T p4,
                                                 final T p5,  final T p6,final T tmp){

        return null;
    }
}
