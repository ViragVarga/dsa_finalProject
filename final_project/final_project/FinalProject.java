package final_project;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

import edu.princeton.cs.algs4.*;

public class FinalProject {
	
	private static String[] stopArray;
	private static stop[] stops;
	private static String[] edgeArray;
	private static DirectedEdge[] edges;
	private static EdgeWeightedDigraph graph;
	private static String[] timeArray;
	private static times[] timetable;
	private static final String stopsTXT = "stops.txt";
	private static final String stop_timesTXT = "stop_times.txt";
	private static final String transfersTXT = "transfers.txt";
	static Scanner scanner = new Scanner(System.in);
	
	public static void main(String[] args) {
		busStopInfo();
		routeBetween();
		busesOnTime();
		
	}
	
	public static void busStopInfo() {
		ArrayList<String> stopLines = fileReader(stopsTXT);
		
		if(stopLines != null) {
			stopArray = new String[stopLines.size()-1];
			for(int i=1; i<stopLines.size(); i++) {
				stopArray[i-1] = stopLines.get(i);
			}
			
			stops = new stop[stopArray.length];
			
			try {
				for(int i=0; i<stopArray.length; i++) {
					stops[i] = new stop();
					String[] data = stopArray[i].split(",");
					int counter = 0;
					
					stops[i].setID(Integer.parseInt(data[counter++]));
					if(data[counter].equals(" "))
						stops[i].setCode(-1);
					else
						stops[i].setCode(Integer.parseInt(data[counter]));
					counter++;
					stops[i].setName(data[counter++]);
					stops[i].setDesc(data[counter++]);
					stops[i].setCoordinates(Double.parseDouble(data[counter++]), Double.parseDouble(data[counter++]));
					stops[i].setZoneID(data[counter++]);
					stops[i].setURL(data[counter++]);
					stops[i].setLocationType(data[counter++]);
					if(counter >= data.length) 
						stops[i].setParentLocation("");
					else
						stops[i].setParentLocation(data[counter]);
				}
			}
			catch (Exception e) {
					System.out.println("Couldn't set data of the stops. Error message: " + e);
			}
			
			TST<Integer> tst = new TST<Integer>();
			for(stop i : stops) {
				String name[] = i.getName().split(" ");
				String key = "";
				if(name[0].equals("FLAGSTOP") || name[0].equals("EB") || name[0].equals("NB")
						|| name[0].equals("SB") || name[0].equals("WB")) {
					for(int s = 1; s<name.length; s++) {
						key += name[s] + " ";
					}
					key += name[0];
				}
				else {
					key = i.getName();
				}
				
				int id = i.getID();
				
				tst.put(key, id);
			}

			boolean exit = false;
			stop.sortStops(stops);
			System.out.println("Get information of the bus stops by entering its name.");
			while(!exit) {
				System.out.println("Please enter stop name (or type \"exit\" to stop): ");
				String inSearch = scanner.nextLine();
				if(inSearch.equalsIgnoreCase("exit")) {
					exit = true;
				}
				else {
					Iterable<String> searchRes = tst.keysWithPrefix(inSearch.toUpperCase());
					for(String i : searchRes) {
						stop tmp = stop.findStop(stops, i);
						tmp.displayInfo();
					}
				}
			
			}
		}
	}


	public static void routeBetween() {
		ArrayList<String> edgeLines = fileReader(transfersTXT);
		ArrayList<String> timeLines = fileReader(stop_timesTXT);
		
		if(edgeLines != null || timeLines != null) {
			edgeArray = new String[edgeLines.size()-1];
			timeArray = new String[timeLines.size()-1];
			for(int i=1; i<edgeLines.size(); i++)
				edgeArray[i-1] = edgeLines.get(i);
			for(int i=1; i<timeLines.size(); i++)
				timeArray[i-1] = timeLines.get(i);
			
			edges = new DirectedEdge[edgeArray.length + timeArray.length];
			int numEdges = 0;
			
			try {
				for(int i=0; i<edgeArray.length; i++) {
					String[] data = edgeArray[i].split(",");
					int counter = 0;
					
					int v = Integer.parseInt(data[counter++]);
					int w = Integer.parseInt(data[counter++]);
					int type = Integer.parseInt(data[counter++]);
					double weight;
					if(type == 0)
						weight = 2;
					else
						weight = Double.parseDouble(data[counter++]) / 100;
					
					edges[i] = new DirectedEdge(v, w, weight);
					numEdges++;
				}
				
				int preRoute = 0, preStop = 0, preRank = 0;
				
				for(int i=0; i<timeArray.length; i++) {
					String data[] = timeArray[i].split(",");
					int counter = 0;
					int route = Integer.parseInt(data[counter++]);
					String time = data[counter++];
					time = data[counter++];
					int stop = Integer.parseInt(data[counter++]);
					int rank = Integer.parseInt(data[counter++]);
					
					if(preRoute == route && preRank+1 == rank)
						edges[numEdges++] = new DirectedEdge(preStop, stop, 1);
					
					preRoute = route;
					preStop = stop;
					preRank = rank;
				}
			}
			catch(Exception e) {
				System.out.println("Couldn't set data of the edges! Error message: " + e);
			}
			
			graph = new EdgeWeightedDigraph(numEdges);
			for(int i=0; i<numEdges; i++)
				graph.addEdge(edges[i]);
			
			boolean exit = false;
			System.out.println("Get information about the shortest route between stops.");
			while(!exit) {
				int from = -1, to = -1;
				System.out.println("Please enter the departure stop ID (or enter \"exit\"): ");
				String input = scanner.nextLine();
				while(input.contains(" ")) {
					System.out.println("Please enter a valid ID or the word exit!");
					input = scanner.nextLine();
				}
				if(input.equalsIgnoreCase("exit"))
					break;
				
				do{
					try {
						from = Integer.parseInt(input);
					}
					catch (Exception e){
						from = -1;
						System.out.println("Please enter a valid ID!");
						input = scanner.nextLine();
					}
				}while(from < 0);
				
				System.out.println("Please enter the arrival stop ID: ");
				input = scanner.nextLine();
				while(input.contains(" ")) {
					System.out.println("Please enter a valid ID or the word exit!");
					input = scanner.nextLine();
				}
				if(input.equalsIgnoreCase("exit")) 
					break;
				
				do {
					try {
						to = Integer.parseInt(input);
					}
					catch (Exception e) {
						to = -1;
						System.out.println("Please enter a valid ID!");
						input = scanner.nextLine();
					}
				}while(to < 0);
				
				DijkstraSP path = new DijkstraSP(graph, from);
				
				if(path.hasPathTo(to)) {
					System.out.println("Route from "+from+", to "+to+":");
					for(DirectedEdge i : path.pathTo(to)) {
						System.out.println("Stop ID: "+i.to()+", cost there: "+i.weight());
					}
				}
				else
					System.out.println("There's no route between " + from +" and " + to);
			}
			
		}
	}
	
	
	public static void busesOnTime() {
		try {
			timetable = new times[timeArray.length];
			for(int i=0; i<timeArray.length; i++) {
				String[] data = timeArray[i].split(",");
				int counter = 0;
				int trip = Integer.parseInt(data[counter++]);
				String arr = data[counter++];
				if(!times.validTime(arr))
					arr = null;
				String depart = data[counter++];
				if(!times.validTime(depart))
					depart = null;
				int stop = Integer.parseInt(data[counter++]);
				int rank = Integer.parseInt(data[counter++]);
				timetable[i] = new times(trip, arr, depart, stop, rank);
			}
		}
		catch(Exception e) {
			System.out.println(e);
		}
		
		boolean exit = false;
		System.out.println("Get the aviable buses before a given time.");
		while(!exit) {
			System.out.println("Please enter time(hh:mm:ss) (or type \"exit\"):");
			String input = scanner.nextLine();
			int counter = 0;
			if(input.equalsIgnoreCase("exit"))
				exit = true;
			else if(!times.validTime(input))
				System.out.println("It's not a valid time or the word exit!");
			else
				for(times i : timetable) {
					if(i.getArrival() != null)
						if(i.getArrival().trim().equalsIgnoreCase(input.trim())) {
							i.displayInfo();
							counter++;
						}
				}
			if(counter == 0)
				System.out.println("There's no bus arrival @ that time.\n");
		}
	}
	
	
	private static ArrayList<String> fileReader(String file){
		ArrayList<String> lines = new ArrayList<String>();
		try(BufferedReader in = new BufferedReader(new FileReader(file))) {
		    String str;
		    while ((str = in.readLine()) != null) {
		    	lines.add(str);
		    }
		}
		catch (Exception e) {
		    System.out.println("File read-in failed with " + file + "! Error message: " + e);
		    lines = null;
		}
		return lines;
	}

	
}