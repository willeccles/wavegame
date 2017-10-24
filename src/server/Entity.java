package server;

import mainGame.ID;

/**
 * A helper class that just contains all the information about a given entity.
 * @author Will Eccles
 * @version 2017-10-24
 */
public class Entity {
	private double locX, locY;
	private ID entityID;
	private String side;
	private int option;

	/**
	 * Constructor.
	 * @param id The type (ID) of the entity.
	 * @param x The x-coordinate of the location of the entity.
	 * @param y The y-coordinate of the location of the entity.
	 * @param _option For enemies with different states (like EnemySweep)
	 * @param _side The side the enemy should come from (for EnemyBurst)
	 */
	public Entity(ID id, double x, double y, int _option, String _side) {
		entityID = id;
		locX = x;
		locY = y;
		option = _option;
		side = _side;
	}

	/**
	 * Constructor.
	 * @param id The type (ID) of the entity.
	 * @param loc The location {x, y} of the entity.
	 * @param _option The option number, i.e. 0-3 for EnemySweep
	 * @param _side The side of the screen (for EnemyBurst)
	 */
	public Entity(ID id, double[] loc, int _option, String _side) {
		this(id, loc[0], loc[1], _option, _side);
	}

	/**
	 * Get the type (ID) for the object. This can be converted to an int using .ordinal() which can be used with ID.values().
	 * @return The ID of the object.
	 */
	public ID getType() {
		return entityID;
	}

	/**
	 * Get the X coordinate of the object's location.
	 * @return the X coordinate
	 */
	public double getX() {
		return locX;
	}
	/**
	 * Get the Y coordinate of the object's location.
	 * @return the Y coordinate
	 */
	public double getY() {
		return locY;
	}
	
	/**
	 * Get the option value for EnemySweep.
	 * @return 0-3
	 */
	public int getOption() {
		return option;
	}

	/**
	 * Get the side the enemy should come from (only used for EnemyBurst).
	 * @return The side ("top", "bottom", "left", "right")
	 */
	public String getSide() {
		return side;
	}
}
