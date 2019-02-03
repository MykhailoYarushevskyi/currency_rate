package ua.biz.synergy.currencyrate.model.room.entity.catalogs;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.Objects;


@Entity(tableName = "banks", indices = {@Index(value = "name", unique = false)})
public class BankRecord {
    @Ignore
    private final String TAG = BankRecord.class.getSimpleName();
    @PrimaryKey
    @NonNull
    private String id;
    private String name;
    private String url;
    
    @Override
    public String toString() {
        return "BankRecord{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BankRecord)) return false;
        BankRecord that = (BankRecord) o;
        return Objects.equals(getId(), that.getId()) &&
                Objects.equals(getName(), that.getName()) &&
                Objects.equals(getUrl(), that.getUrl());
    }
    
    @Override
    public int hashCode() {
        
        return Objects.hash(getId(), getName(), getUrl());
    }
    
    public String getId() {
    
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getUrl() {
        return url;
    }
    
    public void setUrl(String url) {
        this.url = url;
    }
}
