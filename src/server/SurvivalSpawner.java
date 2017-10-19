package server;

import mainGame.ID;
import java.util.Random;

/**
 * Server class that handles spawning of random enemies for survival.
 * @author Will Eccles
 */
public class SurvivalSpawner {
	private Random rand;
	// Valid usable IDs for things to spawn, should also include health once that's a thing
	private static final ID[] validIDs = {
		ID.EnemyFast,
		ID.EnemySmart,
		ID.EnemyBurst,
		ID.EnemySweep,
		ID.EnemyShooter,
		ID.EnemyBasic,
		ID.EnemyTracker,
		ID.EnemyExpand,
		ID.EnemyMiniShooter,
		ID.EnemyMiniBullet,
		ID.PickupHealth
	};
	private int lastEnemy = 0; // since 0 is an invalid ordinal (it's ID.Player) it's a good dummy value

	public SurvivalSpawner() {
		rand = new Random();
	}

	/**
	 * Gets a random enemy ID ordinal.
	 * Will never give ID.Player, Trail, etc., nor will it repeat two in a row.
	 * @return ordinal int, can be used with ID.values()[_]
	 */
	public int getRandEnemy() {
		// .ordinal() returns the index of the value.
		int rval;
		do {
			rval = validIDs[rand.nextInt(validIDs.length)].ordinal();
		} while (rval == lastEnemy);
		lastEnemy = rval;
		return rval;
	}
}
