package final_project;

public class times {
	private int tripID, stopID, rank;
	private String arrival;
	private String departure;
	
	public times(int tripID, String arr, String depa, int stopID, int rank) {
		this.tripID = tripID;
		this.arrival = arr;
		this.departure = depa;
		this.stopID = stopID;
		this.rank = rank;
	}
	
	public static boolean validTime(String time) {
		try {
			String[] split = time.split(":");
			if(Integer.parseInt(split[0].trim()) < 24 && 
					Integer.parseInt(split[1].trim()) < 60 &&
					Integer.parseInt(split[2].trim()) < 60)
			return true;
		}
		catch(Exception e) {
			System.out.println(e);
		}
		return false;
	}
	
	public void tripIDSet(int id) {
		this.tripID = id;
	}
	public int tripIDGet() {
		return tripID;
	}
	
	public void stopIDSet(int id) {
		this.stopID = id;
	}
	public int stopIDGet() {
		return stopID;
	}
	
	public void rankSet(int rank) {
		this.rank = rank;
	}
	public int rankGet() {
		return rank;
	}
	
	public void setTime(String arr, String depa) {
		this.arrival = arr;
		this.departure = depa;
	}
	public void setArrival(String arr) {
		this.arrival = arr;
	}
	public void setDeparture(String depa) {
		this.departure = depa;
	}
	public String getArrival() {
		return arrival;
	}
	public String getDeparture() {
		return departure;
	}
	

	public void displayInfo() {
		System.out.println("Route ID: " + tripID +
				"\nArriaval time: " + arrival +
				"\nStop ID: " + stopID + "\n");
	}
}
