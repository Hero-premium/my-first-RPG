package com.mygdx.game;

import entities.Entity;

public class Physics {
	float gravity = -980f;
    float air = -980 ;
	
	public void gravity(Entity entity, float deltaTime, float floorLevel) {
			
		entity.velocity.y += gravity * deltaTime;

		
		entity.position.y += entity.velocity.y * deltaTime;

		if (entity.position.y < floorLevel) {
		    entity.position.y = floorLevel; 
		    entity.velocity.y = 0f;  
		    entity.onGround = true;
		}
	} 
	
	public void airRis(Entity entity, float deltaTime) {
		if (!entity.facingLeft)  {
		if (entity.velocity.x <= 0) {
			entity.velocity.x = 0;
            return;
		}
		 entity.velocity.x += air * deltaTime;
	     
		} else {
			
		if (entity.velocity.x >= 0) {
			entity.velocity.x = 0;
	        return;
	    }
	       entity.velocity.x -= air * deltaTime ;
		}
		   entity.position.x += entity.velocity.x * deltaTime;

	}
	
	
}
