package com.mygdx.spacex;

import java.util.ArrayList;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class EnemyShip extends Ship {

	public EnemyShip(Vector2 start, SpriteBatch batch, ArrayList<Entity> world) {
		super(new Texture("enemy_spaceship.png"), start, batch, world);
		horizontalSpeed = 2f;
		verticalSpeed = 2f;
		shotDelay = 1f;
	}
	
	// AI logic to set movement data, almost like Player's input.
	// This basically follows the player right here.
	private void ai () {
		if (world.get(0).getY() > getY())
			velocity.y = 1;
		if (world.get(0).getY() < getY())
			velocity.y = -1;
		if (world.get(0).getX() > getX())
			velocity.x = 1;
		if (world.get(0).getX() < getX()) 
			velocity.x = -1;
		fire();
	}
	
	private void collision () {
		if (bounds.overlaps(world.get(0).bounds)) {
			alive = false;
			world.get(0).dispose();
			dispose();
		}
	}
	
	@Override
	protected void update () {
		// Process ai logic.
		ai();
		// Run ship update.
		super.update();
		// Run collision update.
		collision();
	}

}