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
import ua.biz.synergy.currencyrate.model.util.ModelUtil;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(tableName = "banksrate",
        foreignKeys = @ForeignKey(
                entity = Banks.class,
                parentColumns = "id",
                childColumns = "bankId",
                onDelete = CASCADE),
        indices = {@Index(value = {"bankId", "date"}, unique = false)})

public class BanksRate extends Rate {
    
    @NonNull
    public final String bankId;
    
    @Ignore
    public BanksRate(Date date, NatureRate natureRate, String jsonRate, Coordinates userLocation, String bankId) {
        this(UUID.randomUUID().toString(), date, natureRate, jsonRate, userLocation, bankId);
    }
    
    public BanksRate(String id, Date date, NatureRate natureRate, String jsonRate, Coordinates userLocation, String bankId) {
        super(id, date, natureRate, jsonRate, userLocation);
        this.bankId = bankId;
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
     * Compares this {@code BanksRate} object to the specified object. The result of this compare is {@code true}
     * if and only if argument is not {@code null} and is a {@code BanksRate}, that represent same fields as this object.
     *
     * @param obj The object to compare this {@code BanksRate} object against
     * @return {@code true} if the given object represent the object, equivalent to this {@code BanksRate} object,
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
        if (!(obj instanceof BanksRate)) {
            return false;
        }
        BanksRate banksRate = (BanksRate) obj;
        if (this.hashCode() != banksRate.hashCode()) {
            return false;
        }
        if (!(this.id.equals(banksRate.id) &&
                this.date.equals(banksRate.date) &&
                this.natureRate.equals(banksRate.natureRate) &&
                this.jsonRate.equals(banksRate.jsonRate))) {
            return false;
        }
        return (ModelUtil.isEqualsCoordinates(this.userLocation, banksRate.userLocation));
    }
}
