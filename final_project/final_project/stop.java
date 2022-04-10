package final_project;

public class stop {
	
	private int ID, code;
	private String name, desc, zoneID, url, loc_type, parent_station;
	private double lat, lon; 
		
	public void setID(int ID) {
		this.ID = ID;
	}
	public int getID() {
		return this.ID;
	}
	
	public void setCode(int code) {
		this.code = code;
	}
	public int getCode() {
		return this.code;
	}
	
	public void setName(String Name) {
		this.name = Name;
	}
	public String getName() {
		return this.name;
	}
	
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getDesc() {
		return this.desc;
	}
	
	public void setCoordinates(double latitude, double longtitude) {
		this.lat = latitude;
		this.lon = longtitude;
	}
	public double getLatitude() {
		return this.lat;
	}
	public double getLongtitude() {
		return this.lon;
	}
	
	public void setZoneID(String ID) {
		this.zoneID = ID;
	}
	public String getZoneID() {
		return this.zoneID;
	}
	
	public void setURL(String url) {
		this.url = url;
	}
	public String getURL() {
		return this.url;
	}
	
	public void setLocationType(String locType) {
		this.loc_type = locType;
	}
	public String getLocationType() {
		return this.loc_type;
	}
	
	public void setParentLocation(String parentLoc) {
		this.parent_station = parentLoc;
	}
	public String getParentLocation() {
		return this.parent_station;
	}
	
	public void displayInfo() {
		System.out.print("Stop ID: " + this.ID + 
				"\nStop code: " + this.code+
				"\nStop name: "+ this.name+
				"\nStop description: " +this.desc+
				"\nStop coordinates: " +this.lat+", " +this.lon+
				"\nZone ID: " +this.zoneID+
				"\nURL: " +this.url+
				"\nLocation type: " + this.loc_type+
				"\nParent station: "+this.parent_station+"\n\n");
	}
}
