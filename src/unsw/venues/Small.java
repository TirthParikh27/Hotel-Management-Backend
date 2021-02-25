package unsw.venues;

public class Small extends Room {
    public Small(String room){
        super(room);
    }

    public boolean isequals(String str){
        return str.equals("Small");
    }
    
}