package final_project;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

import edu.princeton.cs.algs4.*;

public class FinalProject {
	
	public static void main(String[] args) {
		ArrayList<String> stopLines = new ArrayList<String>();
		boolean fileRead = false;
		try(BufferedReader in = new BufferedReader(new FileReader("stops.txt"))) {
		    String str;
		    while ((str = in.readLine()) != null) {
		    	stopLines.add(str);
		    }
		    fileRead = true;
		}
		catch (Exception e) {
		    System.out.println("File read-in failed! Error message: " + e);
		    fileRead = false;
		}
		String[] stopArray = new String[stopLines.size()-1];
		for(int i=1; i<stopLines.size(); i++)
			stopArray[i-1] = stopLines.get(i);
		
		if(fileRead) {
			stop stops[] = new stop[stopArray.length];
			try {
				for(int i=0; i<stopArray.length; i++) {
					stops[i] = new stop();
					String[] data = stopArray[i].split(",");
					int counter = 0;
					
					stops[i].setID(Integer.parseInt(data[counter]));
					counter++;
					if(data[counter].equals(" "))
						stops[i].setCode(-1);
					else
						stops[i].setCode(Integer.parseInt(data[counter]));
					counter++;
					stops[i].setName(data[counter]);
					counter++;
					stops[i].setDesc(data[counter]);
					counter++;
					stops[i].setCoordinates(Double.parseDouble(data[counter]), Double.parseDouble(data[counter+1]));
					counter += 2;
					stops[i].setZoneID(data[counter]);
					counter++;
					stops[i].setURL(data[counter]);
					counter++;
					stops[i].setLocationType(data[counter]);
					counter++;
					if(counter >= data.length) 
						stops[i].setParentLocation("");
					else
						stops[i].setParentLocation(data[counter]);
				}
			}
			catch (Exception e) {
					System.out.println("Couldn't set data of the stops. Error message: " + e);
			}
			
				boolean exit = false;
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
//					System.out.println(key);
					
					int id = i.getID();
					
					tst.put(key, id);
//					trie.add(key, id);
				
				}

				stop.sortStops(stops);
				Scanner scanner = new Scanner(System.in);
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
				scanner.close();
		}
		
		
	}

}