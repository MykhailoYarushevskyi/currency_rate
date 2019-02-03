package ua.biz.synergy.currencyrate.model.room.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import ua.biz.synergy.currencyrate.model.room.entity.Banks;

@Dao
public interface BanksDao {
        /*
    BanksDao
     */
    
    @Query("SELECT * FROM banks")
    List<Banks> selectAllBanks();
    
    @Query("SELECT * FROM banks ORDER BY name")
    List<Banks> selectAllBanksOrderByName();
    
    @Query("SELECT * FROM banks ORDER BY city")
    List<Banks> selectAllBanksOrderByCity();
    
    @Query("SELECT * FROM banks WHERE id=:id")
    List<Banks> findBanksById(String id);
    
    @Query("SELECT * FROM banks WHERE name=:bankName")
    List<Banks> findAllBanksByName(String bankName);
    
    @Query("SELECT * FROM banks WHERE city=:city")
    List<Banks> findAllBanksByCity(String city);
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Banks... banks);
    
    @Insert
    long insertOneReturnRowNumber(Banks bank);
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertReplaceBanksList(List<Banks> banksList);
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertBanksList(List<Banks> banksList);
    
    @Insert
    void insertBanksArray(Banks[] banksArray);
    
    @Update
    void update(Banks... banks);
    
    @Update
    int updateOneReturnRowNumber(Banks bank);
    
    //@Query also supported UPDATE and DELETE queries for edit or delete rows
    
    @Delete
    void delete(Banks... banks);
    
    @Delete
    int deleteOneReturnRowNumber(Banks bank);
    
    @Query("DELETE FROM banks")
    void delete();
}
