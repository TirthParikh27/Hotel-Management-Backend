package unsw.venues;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class Venue {
    private String name;
    private List<Room> rooms , temp;
    private List<Reservation> reservations;

    public Venue(String name) {
        this.name = name;
        this.rooms = new ArrayList<Room>();
        this.reservations = new ArrayList<Reservation>();
        this.temp = new ArrayList<Room>();
    }

    public void addRoom(Room room ){
        this.rooms.add(room);
    }

    public void addReservation(Reservation reservation){
        this.reservations.add(reservation);
    }

    public void removeReservation(String id){
        Reservation temp = null;
        for(Reservation item : this.reservations){
            if(id.equals(item.getId())){
                temp = item;
                break;
            }
        }
        if(temp != null){
            temp.removeMe();
            this.reservations.remove(temp);
        }
        
    }

    public Reservation getReservation(String id){
        for(Reservation item : reservations){
            if(id.equals(item.getId())){
                return item;
            }
        }
        return null;
    }

    public String getName() {
        return name;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    
    public List<Room> getTemp(){
        return this.temp;
    }

    public void clearTemp(){
        this.temp.clear();
    }
    

    public boolean check(int small , int medium , int large, LocalDate start , LocalDate end ){
        int sm = 0;
        int md = 0;
        int lg = 0;
        
        for(Room r : this.rooms){
            if(sm == small && md == medium && lg == large) return true;
            
            if(r.checkAvailability(start, end)){
                if(r.isequals("Small") && sm < small){
                    sm++;
                    this.temp.add(r);
                } else if (r.isequals("Medium") && md < medium){
                    md++;
                    this.temp.add(r);
                } else if (r.isequals("Large") && lg < large){
                    lg++;
                    this.temp.add(r);
                }
                
            }
            
        }
        if(sm == small && md == medium && lg == large){
            return true;
        } else {
            this.clearTemp();
            return false;
        
        }
    }

    
    public JSONArray giveList(){
        JSONArray result = new JSONArray();
        
        for(Room r : this.rooms){
            JSONObject obj = new JSONObject();
            obj.put("reservations", r.giveJsonArray());
            obj.put("room", r.getName());
            result.put(obj);
        }
        return result;
    }
    
}