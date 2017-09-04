package ua.nure.dzhafarov.hotel.database.entities;

import java.io.Serializable;

/**
 * This class describes all entities which have identifier
 */

public abstract class AbstractEntity implements Serializable {

	private static final long serialVersionUID = 8466257860808346236L;

	private long id;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
}
