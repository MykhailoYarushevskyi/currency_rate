package ua.biz.synergy.currencyrate.model.room.entity.pojo;

public class CoordinatesString {
    public final String longitude;
    public final String latitude;
    
    
    public CoordinatesString(String longitude, String latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
        
    }
    
    @Override
    public int hashCode() {
        int hc = 14;
        return 33 * hc + (this.longitude).hashCode() + (this.latitude).hashCode();
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof CoordinatesString)) {
            return false;
        }
        CoordinatesString coordinates = (CoordinatesString) obj;
        if (this.hashCode() != coordinates.hashCode()) {
            return false;
        }
        return ((this.longitude).equals(coordinates.longitude) &&
                (this.latitude).equals(coordinates.latitude));
    }
}
