package event;

import entity.Planet;

public class LandEvent extends Event {
	private Planet planet;

	public Planet getPlanet() {
		return planet;
	}

	public LandEvent(Planet planet) {
		this.planet = planet;
	}
	
}
