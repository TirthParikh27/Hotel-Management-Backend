package unsw.venues;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.json.JSONArray;

public abstract class Room {
    private String name;
    private List<Reservation> reservations;
    
    public Room(String room){
        this.name = room;
        this.reservations = new ArrayList<Reservation>();
    }

    public String getName() {
        return name;
    }
   
    public List<Reservation> getReservations() {
        return reservations;
    }

    public void addReservation(Reservation reservation){
        this.reservations.add(reservation);
    }

    public void removeReservation(Reservation reservation){
        this.reservations.remove(reservation);
    }
    
    public boolean checkAvailability(LocalDate start , LocalDate end){
        if(this.reservations.isEmpty()) return true;

        for(Reservation r : reservations){
            if(r.checkClash(start, end)) return false;
        }

        return true;
    }


    public JSONArray giveJsonArray(){
        JSONArray result = new JSONArray();
        this.reservations.sort(Comparator.comparing(Reservation :: getStart));
        for(Reservation r : this.reservations){
            result.put(r.giveJsonObject());
        }
        return result;
    }

    public abstract boolean isequals(String str);
}