package final_project;

public class stop {
	
	private int code;
	private Integer ID;
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
	
	public static void sortStops(stop[] a) {
		stop[] tmp = new stop[a.length];
		sort(a, tmp, 0, a.length-1);
	}
	private static void sort(stop[] a, stop[] tmp, int lo, int hi) {
		if(hi <= lo) 
			return;
		int mid = lo + (hi-lo) /2;
		sort(a, tmp, lo, mid);
		sort(a, tmp, mid+1, hi);
		merge(a, tmp, lo, mid, hi);
	}
	private static void merge(stop[] a, stop[] tmp, int lo, int mid, int hi) {
		for(int k = lo; k <= hi; k++)
			tmp[k] = a[k];
		
		int i = lo, j = mid+1;
		for(int k = lo; k <= hi; k++) {
			if(i > mid)
				a[k] = tmp[j++];
			else if(j > hi)
				a[k] = tmp[i++];
			else if(tmp[j].getID() < tmp[i].getID())
				a[k] = tmp[j++];
			else
				a[k] = tmp[i++];
		}
	}
	
	public static stop findStop(stop[] a, int id) {
		int lo = 0, hi = a.length-1;
		while(lo <= hi) {
			int mid = lo + (hi - lo) / 2;
			if(id < a[mid].getID())
				hi = mid - 1;
			else if(id > a[mid].getID())
				lo = mid + 1;
			else
				return a[mid];
		}
		return null;
	}
	
	public static stop findStop(stop[] a, String name) {
		String[] s = name.split(" ");
		if(s[s.length-1].equalsIgnoreCase("flagstop") || s[s.length-1].equalsIgnoreCase("eb") || 
				s[s.length-1].equalsIgnoreCase("wb") || s[s.length-1].equalsIgnoreCase("sb") || s[s.length-1].equalsIgnoreCase("nb")) {
			name = s[s.length-1];
			for(int i=0; i<s.length-1; i++) {
				name += " " + s[i];
			}
		}
		for(stop i : a) {
			if(i.getName().compareToIgnoreCase(name) == 0)
				return i;
		}
		return null;
	}
}
