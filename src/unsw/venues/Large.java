package unsw.venues;

public class Large extends Room {
    public Large(String room){
        super(room);
    }

    public boolean isequals(String str) {
        return str.equals("Large");
    }
    
}