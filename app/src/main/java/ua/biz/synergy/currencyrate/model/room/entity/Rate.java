package ua.biz.synergy.currencyrate.model.room.entity;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.Date;
import java.util.UUID;

import ua.biz.synergy.currencyrate.model.room.entity.pojo.Coordinates;
import ua.biz.synergy.currencyrate.model.room.enums.NatureRate;

abstract class Rate {
    @PrimaryKey
    @NonNull
    public final String id;
    @NonNull
    public final Date date;
    public final NatureRate natureRate;
    public final String jsonRate;
    @Embedded(prefix = "user")
    public final Coordinates userLocation;          //creates columns user_latitude, user_longitude
    
    @Ignore
    Rate(Date date, NatureRate natureRate, String jsonRate, Coordinates userLocation) {
        this(UUID.randomUUID().toString(), date, natureRate, jsonRate, userLocation);
    }
    
    Rate(@NonNull String id, @NonNull Date date, NatureRate natureRate, String jsonRate, Coordinates userLocation) {
        this.id = id;
        this.date = date;
        this.natureRate = natureRate;
        this.jsonRate = jsonRate;
        this.userLocation = userLocation;
    }
    

    @Override
    public String toString() {
        return this.id;
    }
    
    @Override
    public boolean equals(Object obj) {
        return false;
    }
    
}
