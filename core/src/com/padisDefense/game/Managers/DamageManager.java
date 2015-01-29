package com.padisDefense.game.Managers;

import com.badlogic.gdx.math.Vector2;
import com.padisDefense.game.Enemies.Enemy;
import com.padisDefense.game.GameScreen;
import com.padisDefense.game.Padi;
import com.padisDefense.game.Towers.GreenTower;
import com.padisDefense.game.Towers.YellowTower;
import com.padisDefense.game.Towers.Tower;

public class DamageManager {

    Padi padi;
    GameScreen game;


    public DamageManager(GameScreen g, Padi p){
        game = g;
        padi = p;
    }

    public void hit(Tower t, Enemy e){

        float attack;

        //if tower is strong against enemy, its attack is increased 100%
        if(t.isStrongAgainst(e.getName())){
            attack = t.getAttack()*5f;
            e.originalHit(attack);//bypasses increased armor.
        }
        //if tower is weak against enemy, its attack is reduced to 10%
        else if(t.isWeakAgainst(e.getName())){
            attack = t.getAttack()*0.1f;
            e.hit(attack);
        }
        else{
            attack = t.getAttack();
            e.hit(attack);
        }

        //no matter the tower, targeted enemy will take SOME damage.


        /*if(t.getID().equals("BLUE")){
            iceHit((YellowTower) t, e);
        }*/

        if(t.getID().equals("GREEN")){
            aoeHit((GreenTower) t, e);
        }

    }


    //All enemies within a certain range will be slowed for a certain amount of time.
    //effect proportional to distances from targeted enemy.
    public void iceHit(YellowTower t, Enemy e){
        e.affectRate(0, 3f);

        double distance;
        Enemy temp;
        int fourLimit = 0;//Limits the number of enemies affected to four.
        for(int x = 0;x < game.enemy.getActiveEnemy().size; x++){
            temp = game.enemy.getActiveEnemy().get(x);

            if(!temp.equals(e) && fourLimit < 4){
                distance = findDistance(new Vector2(e.getX(), e.getY()),
                        new Vector2(temp.getX(),temp.getY()));


                //the further away, the less effects.
                if (distance < t.getRangeAOE()){
                   // temp.affectRate(0, 5f);
                    fourLimit++;
                }


            }
        }


    }




    //All enemies within a certain range will take damage.
    //damage proportional to distance from targeted enemy.
    //Pretty much the same as iceHit(), except damage is taken instead of rate changes.
    //Will keep separate for now. Might be useful later.
    public void aoeHit(GreenTower t, Enemy e) {
        double distance;
        Enemy temp;
        for (int x = 0; x < game.enemy.getActiveEnemy().size; x++) {
            temp = game.enemy.getActiveEnemy().get(x);

            if(!temp.equals(e)){//Don't want to apply damage twice to target enemy.
                distance = findDistance(new Vector2(e.getX(), e.getY()),
                        new Vector2(temp.getX(), temp.getY()));

                //the further away, the less effects.
                //Example: enemyX was 15f away from targeted enemy.
                //z = 15f / 20f; z = 0.75f
                //damage = (tower's attack)*(1-0.75), enemyX takes 25% of damage from tower.
                //if enemyX was closer, 'z' decreases, 'damage' increases.
                if (distance < t.getRangeAOE()) {
                    float z = (float) distance / t.getRangeAOE();
                    float damage = (1 - z)*t.getAttack();

                    if(t.isStrongAgainst(e.getName())){
                        e.originalHit(damage);//bypasses increased armor.
                    }
                    //if tower is weak against enemy, its attack is reduced to 10%
                    else if(t.isWeakAgainst(e.getName())){
                        e.hit(damage);
                    }
                    else{
                        e.hit(damage);
                    }
                }
            }//end if(!temp.equals(e)).

        }//end for-loop.
    }//end aoeHit()

    public double findDistance(Vector2 a, Vector2 b){

        double x2x1 = a.x - b.x;
        double y2y1 = a.y - b.y;
        return Math.sqrt((x2x1 * x2x1) + (y2y1 * y2y1));
    }


    public void dispose(){
        //nothing yet.
    }

    public void reset(){
        //nothing yet.
    }
}
