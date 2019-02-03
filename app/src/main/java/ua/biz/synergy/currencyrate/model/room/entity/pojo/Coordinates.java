package ua.biz.synergy.currencyrate.model.room.entity.pojo;

public class Coordinates {
    public final double longitude;
    public final double latitude;
    
    
    public Coordinates(double longitude, double latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
        
    }
    
    @Override
    public int hashCode() {
        int hc = 14;
        return 33 * hc + ((Double) this.longitude).hashCode() + ((Double) this.latitude).hashCode();
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Coordinates)) {
            return false;
        }
        Coordinates coordinates = (Coordinates) obj;
        if (this.hashCode() != coordinates.hashCode()) {
            return false;
        }
        return ((Double) this.longitude).equals((Double) coordinates.longitude) &&
                ((Double) this.latitude).equals((Double) coordinates.latitude);
    }
}
