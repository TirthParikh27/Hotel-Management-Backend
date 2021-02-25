package unsw.venues;

public class Medium extends Room {
    public Medium(String room){
        super(room);
    }

    public boolean isequals(String str){
        return str.equals("Medium");
    }
}