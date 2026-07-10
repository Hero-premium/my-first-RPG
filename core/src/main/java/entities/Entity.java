package entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
public abstract class Entity {
    
	
	public int gold;
	public String name ;
	public boolean facingLeft;
	public boolean onGround ;
	public boolean isAlive;
	public Vector2 velocity;
	public Rectangle hitBox;
	public float speed;
    public Texture texture;
	protected Entity(int gold, String name , boolean facingLeft, boolean onGround, boolean isAlive, float speed, 
	       Vector2 velocity, Rectangle hitBox, Texture texture) {  

		
		this.gold = gold; 
		this.name = name ;
        this.facingLeft = facingLeft;
        this.onGround = onGround;
        this.isAlive = isAlive;
        this.velocity = velocity;
        this.speed = speed;
        this.hitBox = hitBox;
        this.texture = texture;
	}
}
