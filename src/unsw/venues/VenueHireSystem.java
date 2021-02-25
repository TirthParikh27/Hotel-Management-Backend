/**
 *
 */
package unsw.venues;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Venue Hire System for COMP2511.
 *
 * A basic prototype to serve as the "back-end" of a venue hire system. Input
 * and output is in JSON format.
 *
 * @author Robert Clifton-Everest
 *
 */
public class VenueHireSystem {

    /**
     * Constructs a venue hire system. Initially, the system contains no venues,
     * rooms, or bookings.
     */
    private List<Venue> venueList;
    public VenueHireSystem() {
        // TODO Auto-generated constructor stub
        this.venueList = new ArrayList<Venue>();
    }

    private void processCommand(JSONObject json) {
        switch (json.getString("command")) {

        case "room":
            String venue = json.getString("venue");
            String room = json.getString("room");
            String size = json.getString("size");
            addRoom(venue, room, size);
            break;

        case "request":
            String id = json.getString("id");
            LocalDate start = LocalDate.parse(json.getString("start"));
            LocalDate end = LocalDate.parse(json.getString("end"));
            int small = json.getInt("small");
            int medium = json.getInt("medium");
            int large = json.getInt("large");
            JSONObject result = request(id, start, end, small, medium, large);
            System.out.println(result.toString(2));
            break;

        // TODO Implement other commands
        case "cancel":
            id = json.getString("id");
            cancelReservation(id);
            break;
        
        case "change":
            id = json.getString("id");
            start = LocalDate.parse(json.getString("start"));
            end = LocalDate.parse(json.getString("end"));
            small = json.getInt("small");
            medium = json.getInt("medium");
            large = json.getInt("large");
            result = changeReservation(id, start, end, small, medium, large);
            System.out.println(result.toString(2));
            break;

        case "list":
            venue = json.getString("venue");
            JSONArray list = null;
            for(Venue v : this.venueList){
                if(venue.equals(v.getName())){
                    list = v.giveList();
                }
            }

            System.out.println(list.toString(2));
            break;

        }
    }

    private void addRoom(String venue, String room, String size) {
        // TODO Process the room command
        int flag = 0;
        Venue newVenue = null;
        for(Venue v : venueList){
            if(v.getName().equals(venue)){
                flag = 1;
                newVenue = v;
                break;  
            }
        }
        if(flag == 0){
            newVenue = new Venue(venue);
            venueList.add(newVenue);
        }
        Room newRoom =null;
        switch (size) {
            case "small":
                newRoom = new Small(room);
                break;
            case "medium":
                newRoom = new Medium(room);
                break;
            case "large":
                newRoom = new Large(room);
                break;
        }
       
        newVenue.addRoom(newRoom);
    }

    public JSONObject request(String id, LocalDate start, LocalDate end,
            int small, int medium, int large) {
        JSONObject result = new JSONObject();

        // TODO Process the request commmand

        Venue v = checkAvailable(start, end, small, medium, large);
        if(v == null){
            result.put("status", "rejected");

        } else {
            Reservation reservation = new Reservation(id, start, end, v , small , medium , large);
            JSONArray rooms = new JSONArray();
            for(Room item : v.getTemp()){
                reservation.addRoom(item);
                item.addReservation(reservation);
                rooms.put(item.getName());
            }
            v.addReservation(reservation);
            
            result.put("status", "success");
            result.put("venue", v.getName());
            
            result.put("rooms", rooms);
            v.clearTemp();
           
        }
        
        return result;
    }

    private void cancelReservation(String id){

        for(Venue v : venueList){
            v.removeReservation(id);
        }

    }

    private JSONObject changeReservation(String id ,LocalDate start , LocalDate end , int small , int medium , int large){
        Reservation temp = null;
        for(Venue v : this.venueList){
            temp = v.getReservation(id);
            if(temp != null) break;
        }
        
        cancelReservation(id);
        JSONObject result = request(id, start, end, small, medium, large);
        if(result.getString("status").equals("rejected")){
            request(id, temp.getStart(), temp.getEnd(), temp.getSmall(), temp.getMedium(), temp.getLarge());
        }
        return result;
    }
    
    
    //Helper Functions
    private Venue checkAvailable(LocalDate start , LocalDate end , int small , int medium , int large){
        for(Venue v : venueList){
           if(v.check(small, medium, large, start, end)) return v;
           else v.clearTemp();
        }
        return null;
    }

    public static void main(String[] args) {
        VenueHireSystem system = new VenueHireSystem();

        Scanner sc = new Scanner(System.in);

        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            if (!line.trim().equals("")) {
                JSONObject command = new JSONObject(line);
                system.processCommand(command);
            }
        }
        sc.close();
    }

}
