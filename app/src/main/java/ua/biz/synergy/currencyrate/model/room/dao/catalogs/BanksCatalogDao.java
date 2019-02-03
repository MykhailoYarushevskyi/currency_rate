package ua.biz.synergy.currencyrate.model.room.dao.catalogs;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Transaction;

import java.util.List;

import ua.biz.synergy.currencyrate.model.room.entity.catalogs.BankRecord;

@Dao
public abstract class BanksCatalogDao {
    // the catalogue of the banks
    
    @Insert
    public abstract void insertList(List<BankRecord> bankRecordList);
    
    @Query("DELETE FROM banks")
    public abstract void delete();
    
    @Transaction
    public void updateCatalogBanks(List<BankRecord> bankRecordList){
        delete();
        insertList(bankRecordList);
    }
}
