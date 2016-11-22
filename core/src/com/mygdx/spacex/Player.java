package com.mygdx.spacex;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;

public class Player extends Entity {
	
	// Declare what kind of data this player class will use.
	private float speed;
	boolean isLeft, isRight, isUp, isDown, isSpace;
	private SpriteBatch batch;
	//private Queue shots = new LinkedList<Shot>();
	private ArrayList<Shot> shots = new ArrayList<Shot>();
	private Boolean shotReady;
	private Timer shotDelay;

	public Player (SpriteBatch batch) {
		super(new Texture("PlayerShip.png"));
		this.batch = batch;
		shotReady = true;
		shotDelay = new Timer();
		setX(0);
		setY(0);
		setRotation(0f);
		speed = 20;
	}
	
	private void input () {
		
		// Start everything in this frame is false.
		isLeft = false;
		isRight = false;
		isUp = false;
		isDown = false;
		isSpace = false;
		
		// Only change if true this frame.
		if (Gdx.input.isKeyPressed(Keys.LEFT)) isLeft = true;
		if (Gdx.input.isKeyPressed(Keys.RIGHT)) isRight = true;
		if (Gdx.input.isKeyPressed(Keys.UP)) isUp = true;
		if (Gdx.input.isKeyPressed(Keys.DOWN)) isDown = true;
		if (Gdx.input.isKeyPressed(Keys.SPACE)) isSpace = true;
		
	}
	@Override
	public void movement () {
		// if right and left are pressed, they cancel out.
		if (isLeft) setX(getX() - speed);
		if (isRight) setX(getX() + speed);
		if (isUp) setY(getY() + speed);
		if (isDown) setY(getY() - speed);
		
		if (getX() < 0) {
			setX(Gdx.graphics.getWidth() - getWidth());
		}
		if (getX() + getWidth() > Gdx.graphics.getWidth()) {
			setX(0);
		}
		if (getY() < 0) {
			setY(Gdx.graphics.getHeight() - getHeight());
		}
		if (getY() + getHeight() > Gdx.graphics.getHeight()) {
			setY(0);
		}

		// If space was pressed this frame.
		if (isSpace) {
			// If shot is ready.
			if(shotReady) {
				System.out.println("Fired");
				// Add new shot to shots list, with given coord.
				shots.add(new Shot(new Vector2(getX() + getWidth() / 2, getY() + getHeight())));
				// Set shot ready to false to prevent spam.
				shotReady = false;
				System.out.println("Disabled.");
				
				// Wait 1 second before allowing next shot.
				shotDelay.scheduleTask(new Task() 
				{
					@Override
					public void run() 
					{
						shotReady = true;
						System.out.println("Ready");
					}
				}, .25f);
				
			}
		}
		for(int i = 0; i < shots.size(); i++){
			shots.get(i).update();
		}
	}
	
	public void update () {
		input();
		movement();
	}

	
	public void draw () {
		draw(batch); 
		for(int i = 0; i < shots.size(); i++){
			shots.get(i).draw(batch);
				
		}
	}
	
}
