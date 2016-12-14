import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;

public class FotballFil {
	public static void main(String[] args) throws IOException {
		//initFile();
		ArrayList<String> al = new ArrayList<>();
		HashMap<String, Integer> attendance = new HashMap<>();
		al = getUnqiueNamesList("fotballAttendance.txt");
		attendance = mapAttendance("fotballAttendance.txt", al);
		
		writeMapToFile(attendance);
		//PRINT ATTENDANCE
		for (HashMap.Entry<String, Integer> entry : attendance.entrySet()) {
		    System.out.println(entry.getKey()+" : "+entry.getValue());
		}
		
	
	}
	
	public static void initFile(String filename){
		FileWriter fw;
		try{
			fw = new FileWriter(new File(filename));
			
			for (int i = 0; i < 51; i++) {
				fw.write(String.format("Uke %d", i));
				fw.write(System.lineSeparator());
			}
			
			fw.close();
		}catch(IOException e){
			e.printStackTrace();
		}
		System.out.println("Done");
	}
	
	
	public static void writeMapToFile(HashMap<String, Integer> attendance){
		
		try{
		    PrintWriter writer = new PrintWriter("attendanceComplete.txt", "UTF-8");
		    
		    for (HashMap.Entry<String, Integer> entry : attendance.entrySet()) {
			    writer.println(entry.getKey()+" : "+entry.getValue());
			}
		    writer.close();
		} catch (IOException e) {
		   e.printStackTrace();
		}
		
	}
	
	
	//Creates a hashmap of attendees and their attendance
	public static HashMap<String, Integer> mapAttendance(String filename, ArrayList<String> attendees){
		String inputLine;
		
		HashMap<String, Integer> attendance = new HashMap<String, Integer>();
		
		for (int i = 0; i < attendees.size(); i++) {
			attendance.put(attendees.get(i), 0);
		}
		try{
			InputStream fis = new FileInputStream(filename);
		    InputStreamReader isr = new InputStreamReader(fis, Charset.forName("UTF-8"));
		    BufferedReader br = new BufferedReader(isr);
		    while ((inputLine = br.readLine()) != null) {
		    	if(!isWeekCount(inputLine))
		    		attendance.computeIfPresent(inputLine.replace("\"", ""), (k, v) -> v + 1);
		    }
	    	br.close();
		}catch(IOException e){
				e.printStackTrace();
		}

	
		
		
		return attendance;
	}
	
	public static ArrayList<String> getUnqiueNamesList(String filename){
		String inputLine; 
		ArrayList<String> uniqueList = new ArrayList<>();
	
		
		try{
			InputStream fis = new FileInputStream(filename);
		    InputStreamReader isr = new InputStreamReader(fis, Charset.forName("UTF-8"));
		    BufferedReader br = new BufferedReader(isr);
		    while((inputLine = br.readLine()) != null){
		    	if(!existsInArray(uniqueList, inputLine) && !isWeekCount(inputLine)){
		    		uniqueList.add(inputLine.replace("\"", ""));
		    	}
		    }
		    br.close();
		}catch(IOException e){
			e.printStackTrace();
		}
		
		return uniqueList;
	}
	
	public static boolean existsInArray(ArrayList<String> list, String element){
		if(list.contains(element.replace("\"", "")))
			return true;
		return false;
	}
	
	public static boolean isWeekCount(String inputList){		
		return inputList.toLowerCase().substring(0,3).equals("uke") ?  true : false;
	}
	
	


}
