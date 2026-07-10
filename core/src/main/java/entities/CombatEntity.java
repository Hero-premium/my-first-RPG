package entities;

import java.util.Random;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class CombatEntity extends Entity {
	
    public int hp;
    public int maxhp;
    public int poisonDuration;
    public boolean isDodging;
    public boolean isDefending;
    public boolean isfocused;
    

    public CombatEntity(int gold, String name, boolean facingLeft, boolean onGround, boolean isAlive,
                           float speed, Vector2 velocity, 
                         Rectangle hitbox, int hp, int maxhp, int poisonDuration, 
                         boolean isDodging, boolean isDefending, boolean isfocused,Texture texture) {
        super(gold, name, facingLeft, onGround, isAlive, speed, velocity, hitbox,texture);
        
        this.hp = hp;
        this.maxhp = maxhp;
        this.poisonDuration = poisonDuration;
        this.isDodging = isDodging;
        this.isDefending = isDefending;
        this.isfocused = isfocused;
    }

    public void resetHp() {
        this.hp = this.maxhp;
    }

    public void setPoisonDur(int duration) {
        poisonDuration = duration;
    }
    public void taketurn(CombatEntity player, Random rand) {
	}
}