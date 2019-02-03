package ua.biz.synergy.currencyrate.model.room.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.support.annotation.NonNull;

import java.util.Date;
import java.util.UUID;

import ua.biz.synergy.currencyrate.model.room.entity.pojo.Coordinates;
import ua.biz.synergy.currencyrate.model.room.enums.NatureRate;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(tableName = "marketsrate",
        foreignKeys = @ForeignKey(
                entity = Markets.class,
                parentColumns = "id",
                childColumns = "marketId",
                onDelete = CASCADE),
        indices = {@Index(value = {"marketId", "date"}, unique = false)})
public class MarketsRate extends Rate {
    @NonNull
    public final String marketId;
    
    @Ignore
    public MarketsRate(Date date, NatureRate natureRate, String jsonRate, Coordinates userLocation, String marketId) {
        this(UUID.randomUUID().toString(), date, natureRate, jsonRate, userLocation, marketId);
    }
    
    public MarketsRate(String id, Date date, NatureRate natureRate, String jsonRate, Coordinates userLocation, String marketId) {
        super(id, date, natureRate, jsonRate, userLocation);
        this.marketId = marketId;
    }
    
    @Override
    public String toString() {
        return "\"ID=\"" + id + "\" DATE=\"" + date + "\" NatureRate=\"" + natureRate;
    }
    
    @Override
    public int hashCode() {
        int hc = 14;
        hc = 33 * hc + (id == null || id.length() == 0 ? 0 : id.hashCode());
        hc = 33 * hc + (date == null ? 0 : date.hashCode());
        hc = 33 * hc + (natureRate == null ? 0 : natureRate.type);
        hc = 33 * hc + (jsonRate == null ? 0 : jsonRate.hashCode());
        hc = 33 * hc + (userLocation == null ? 0 : userLocation.hashCode());
        return hc;
    }
    
    /**
     * Compares this {@code MarketsRate} object to the specified object. The result of this compare is {@code true}
     * if and only if argument is not {@code null} and is a {@code MarketsRate}, that represent equal fields as this object.
     *
     * @param obj The object to compare this {@code MarketsRate} object against
     * @return {@code true} if the given object represent the object, equivalent to this {@code MarketsRate} object,
     * {@code false} otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof MarketsRate)) {
            return false;
        }
        MarketsRate marketsRate = (MarketsRate) obj;
        if (this.hashCode() != marketsRate.hashCode()) {
            return false;
        }
        if (!(this.id.equals(marketsRate.id) &&
                this.date.equals(marketsRate.date) &&
                this.natureRate.equals(marketsRate.natureRate) &&
                this.jsonRate.equals(marketsRate.jsonRate))) {
            return false;
        }
        if (this.userLocation == null && marketsRate.userLocation == null) {
            return true;
        }
        if (this.userLocation == null || marketsRate.userLocation == null) {
            return false;
        }
        return this.userLocation.equals(marketsRate.userLocation);
    }
}
