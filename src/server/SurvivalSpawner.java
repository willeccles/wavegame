package server;

import mainGame.ID;
import java.util.Random;

/**
 * Server class that handles spawning of random enemies for survival.
 * @author Will Eccles
 */
public class SurvivalSpawner {
	private Random rand;
	// Valid usable IDs for things to spawn (does not include health pickups, that's different)
	private static final ID[] validIDs = {
		ID.EnemyBurst,
		ID.EnemySweep,
		ID.EnemyShooter,
		ID.EnemyBasic,
		ID.EnemyExpand,
		ID.EnemyMiniShooter,
	};
	private static final String[] sides = {"left", "right", "top", "bottom"};
	private ID lastEnemy = ID.PickupHealth;
	private int healthCountDown = 0; // once this reaches 5 we spawn a health pickup and reset it

	public SurvivalSpawner() {
		rand = new Random();
	}

	/**
	 * Gets the next entity to spawn.
	 * @return The next entity.
	 */
	public Entity getNext() {
		return new Entity(getRandID(), getRandLocation(), rand.nextInt(4), sides[rand.nextInt(4)]);
	}

	/**
	 * Gets a random enemy ID.
	 * Will never give ID.Player, Trail, etc., nor will it repeat two in a row.
	 * @return entityID
	 */
	public ID getRandID() {
		// once we've spawned 5 enemies, we spawn a health pickup and reset the counter to 0
		if (healthCountDown == 5) {
			healthCountDown = 0;
			return ID.PickupHealth;
		}

		healthCountDown++;

		ID rval;
		do {
			rval = validIDs[rand.nextInt(validIDs.length)];
		} while (rval == lastEnemy);
		lastEnemy = rval;

		return rval;
	}

	/**
	 * Get a random location for the entity.
	 * @return 
	 */
	public double[] getRandLocation() {
		double loc[] = {0.0, 0.0};
		loc[0] = rand.nextDouble() * 1280.0;
		loc[1] = rand.nextDouble() * 720.0;
		return loc;
	}
}
