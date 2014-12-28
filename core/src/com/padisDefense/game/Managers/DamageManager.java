package com.padisDefense.game.Managers;

import com.badlogic.gdx.math.Vector2;
import com.padisDefense.game.Enemies.Enemy;
import com.padisDefense.game.GameScreen;
import com.padisDefense.game.Towers.AOETower;
import com.padisDefense.game.Towers.IceTower;
import com.padisDefense.game.Towers.MainTower;

public class DamageManager {

    GameScreen game;


    public DamageManager(GameScreen g){
        game = g;
    }

    public void hit(MainTower t, Enemy e){

        e.updateHealth(t.getAttack());//no matter the tower, targeted enemy will take SOME damage.


        if(t.getID().equals("ice")){
            iceHit((IceTower) t, e);
        }

        else if(t.getID().equals("aoe")){
            aoeHit((AOETower) t, e);
        }
        for(int x = 0; x < game.enemy.getActiveEnemy().size; x++)
            game.enemy.getActiveEnemy().get(x).updateAlteredStats();
    }


    //All enemies within a certain range will be slowed for a certain amount of time.
    //effect proportional to distances from targeted enemy.
    public void iceHit(IceTower t, Enemy e){
        e.affectRate(e.getRate()*0.6f, 3f);

        double distance;
        Enemy temp;
        int fourLimit = 0;//Limits the numbe of enemies affected to four.
        for(int x = 0;x < game.enemy.getActiveEnemy().size; x++){
            temp = game.enemy.getActiveEnemy().get(x);

            if(!temp.equals(e) && fourLimit < 4){
                distance = findDistance(new Vector2(e.getX(), e.getY()),
                        new Vector2(temp.getX(),temp.getY()));

                //the further away, the less effects.
                if (distance < t.getRangeAOE()){
                    float z = (float)distance / t.getRangeAOE();
                    if(z >= 0.75f)
                    z = 0.75f;
                    temp.affectRate(temp.getRate()*z, 3f);
                    fourLimit++;
                }
            }
        }


    }




    //All enemies within a certain range will take damage.
    //damage proportional to distance from targeted enemy.
    //Pretty much the same as iceHit(), except damage is taken instead of rate changes.
    //Will keep separate for now. Might be useful later.
    public void aoeHit(AOETower t, Enemy e) {
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

                    temp.updateHealth(damage);
                }
            }//end if(!temp.equals(e)).

        }//end for-loop.
    }//end aoeHit()

    public double findDistance(Vector2 a, Vector2 b){

        double x2x1 = a.x - b.x;
        double y2y1 = a.y - b.y;
        return Math.sqrt((x2x1 * x2x1) + (y2y1 * y2y1));
    }

}
