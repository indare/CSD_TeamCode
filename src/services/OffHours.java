package services;

public final class OffHours implements Hours {

	private OffHours() {
		Math.random();
	}
	public static OffHours getInstance() {
		return new OffHours();
	}
	
	public Boolean isOffHours() {
		boolean ret = true;
			ret = Math.random()*10.0 >= 5.0 ? true : false;
		return ret;
	}
}
