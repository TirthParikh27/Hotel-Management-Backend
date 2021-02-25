package unsw.venues;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

public class Reservation {
    private Venue venue;
    private String id ;
    private LocalDate start , end;
    private int small , medium , large;
    private List<Room> rooms;

    public Reservation(String id,  LocalDate start, LocalDate end , Venue venue , int small , int medium , int large) {
        this.id = id;
        this.start = start;
        this.end = end;
        this.venue = venue;
        this.rooms = new ArrayList<Room>();
        this.small = small;
        this.medium = medium;
        this.large = large;
        
    }

    public void addRoom(Room room){
        rooms.add(room);
    }
    public String getId() {
        return id;
    }

    public Venue getVenue() {
        return venue;
    }

    
    public LocalDate getStart() {
        return start;
    }

    public LocalDate getEnd() {
        return end;
    }

   
    public int getSmall() {
        return small;
    }
    public int getMedium() {
        return medium;
    }
    public int getLarge() {
        return large;
    }

    public void removeMe(){
        for(Room r : this.rooms){
            r.removeReservation(this);
        }
    }

   
   public boolean checkClash(LocalDate start , LocalDate end){
       if((start.isBefore(this.start) && end.isBefore(this.start)) || (start.isAfter(this.end) && end.isAfter(this.end))){
           return false;
       } else return true;
    }
    

    public JSONObject giveJsonObject(){
        JSONObject obj = new JSONObject();
        obj.put("start", this.start.toString());
        obj.put("end", this.end.toString());
        obj.put("id", this.id);
        return obj;
    }
    
    
}
