package entities;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
public class Entity {
    
	
	public int gold;
	public String name ;
	public boolean facingLeft;
	public boolean onGround ;
	public boolean isAlive;
	public Vector2 position;
	public Vector2 velocity;
	public Rectangle hitBox;
	public float speed;
	 
	protected Entity(int gold, String name , boolean facingLeft, boolean onGround, boolean isAlive, Vector2 position, float speed, 
	       Vector2 velocity, Rectangle hitBox) {  

		
		this.gold = gold; 
		this.name = name ;
        this.facingLeft = facingLeft;
        this.onGround = onGround;
        this.isAlive = isAlive;
        this.position = position;
        this.velocity = velocity;
        this.speed = speed;
        this.hitBox = hitBox;
	}
	
	
	
	 public void syncHitBox(Rectangle hitBox) {
		 hitBox.set(this.position.x,this.position.y,this.hitBox.width,this.hitBox.height);
	 }
}
