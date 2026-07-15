package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;

public class Textures {

	public final static Texture player = new Texture("hollowKnight.png");
	public final static Texture backGround = new Texture("main_game_bg.jpeg");
	public final static Texture openedSpringTrap =  new Texture("spring_trap_opened.png");
	public final static Texture closedSpringTrap = new Texture("spring_trap_closed.png");
	public final static Texture PlaceHolder = new Texture("place_holder.png");
	
	
	public static void dispose() {
		player.dispose();
		backGround.dispose();
		closedSpringTrap.dispose();
		openedSpringTrap.dispose();
	    PlaceHolder.dispose();
	}
}
