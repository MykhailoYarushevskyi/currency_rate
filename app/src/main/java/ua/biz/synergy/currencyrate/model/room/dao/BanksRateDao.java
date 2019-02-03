package ua.biz.synergy.currencyrate.model.room.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.Date;
import java.util.List;

import ua.biz.synergy.currencyrate.model.room.entity.BanksRate;
import ua.biz.synergy.currencyrate.model.room.enums.NatureRate;

@Dao
public interface BanksRateDao {
    /*
    BanksRate
     */
    
    @Query("SELECT * FROM banksrate")
    List<BanksRate> selectAllBanksRate();
    
    @Query("SELECT * FROM banksrate ORDER BY date")
    List<BanksRate> selectAllBanksRateOrderByDate();
    
    @Query("SELECT * FROM banksrate WHERE id=:id")
    List<BanksRate> findBanksRateById(String id);
    
    @Query("SELECT * FROM banksrate WHERE date BETWEEN :from AND :to")
    List<BanksRate> findBanksRateByDateBetweenDates(Date from, Date to);
    
    @Query("SELECT jsonRate FROM banksrate WHERE date BETWEEN :from AND :to")
    String[] findJsonRateIntoBanksRateByDateBetweenDates(Date from, Date to);
    
    @Query("SELECT * FROM banksrate WHERE bankid=:bankId AND date BETWEEN :from AND :to ORDER BY date")
    abstract List<BanksRate> findBanksRateByBankIdByDateBetweenDates(String bankId, Date from, Date to);
    
    @Query("SELECT * FROM banksrate WHERE bankid=:bankId AND natureRate=:natureRate AND date BETWEEN :from AND :to ORDER BY date")
    abstract List<BanksRate> findBanksRateByBankIdByNatureRateByDateBetweenDates(String bankId, NatureRate natureRate, Date from, Date to);
    
    @Query("SELECT COUNT(id) FROM banksrate")
    long countBanksRateNumberOfRow();
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(BanksRate... banksRates);
    
    @Insert
    long insertOneReturnRowNumber(BanksRate banksRate);
    
    @Insert
    void insertBanksRateList(List<BanksRate> banksRateList);
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertReplaceBanksRateList(List<BanksRate> banksRateList);
    
    @Insert
    void insertBanksRateArray(BanksRate[] banksRateArray);
    
    @Update
    void update(BanksRate... banksRates);
    
    @Update
    int updateOneReturnRowNumber(BanksRate banksRate);
    
    //@Query also supported UPDATE queries for edit rows
    
    @Delete
    void delete(BanksRate... banksRates);
    
    @Delete
    void deleteBanksRateList(List<BanksRate> banksRateList);
    
    @Delete
    int deleteOneReturnRowNumber(BanksRate banksRate);
    
    @Query("DELETE FROM banksrate")
    void delete();
    
    @Query("DELETE FROM banksrate WHERE date =:date")
    void deleteForData(Date date);
    
    @Query("DELETE FROM banksrate WHERE date BETWEEN :from AND :to")
    void deleteBanksRateByDateBetweenDates(Date from, Date to);
}