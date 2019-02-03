package ua.biz.synergy.currencyrate.model.room.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.Date;
import java.util.List;

import ua.biz.synergy.currencyrate.model.room.entity.MarketsRate;
import ua.biz.synergy.currencyrate.model.room.enums.NatureRate;

@Dao
public interface MarketsRateDao {
       /*
    MarketsRate
     */
    
    @Query("SELECT * FROM marketsrate")
    List<MarketsRate> selectAllMarketsRate();
    
    @Query("SELECT * FROM marketsrate ORDER BY date")
    List<MarketsRate> selectAllMarketsRateByDate();
    
    @Query("SELECT * FROM marketsrate WHERE id=:id")
    List<MarketsRate> findMarketsRateById(String id);
    
    @Query("SELECT * FROM marketsrate WHERE date BETWEEN :from AND :to")
    List<MarketsRate> findMarketsRateByDateBetweenDates(Date from, Date to);
    
    @Query("SELECT jsonRate FROM marketsrate WHERE date BETWEEN :from AND :to")
    String[] findJsonRateIntoMarketsRateByDateBetweenDates(Date from, Date to);
    
    
    @Query("SELECT * FROM marketsrate WHERE marketId=:marketId AND date BETWEEN :from AND :to ORDER BY date")
    abstract List<MarketsRate> findMarketsRateByMarketIdByDateBetweenDates(String marketId, Date from, Date to);
    
    @Query("SELECT * FROM marketsrate WHERE marketId=:marketId AND natureRate=:natureRate AND date BETWEEN :from AND :to ORDER BY date")
    abstract List<MarketsRate> findMarketsRateByMarketIdByNatureRateByDateBetweenDates(String marketId, NatureRate natureRate, Date from, Date to);
    
    @Query("SELECT COUNT(id) FROM marketsrate")
    long countMarketsRateNumberOfRow();
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(MarketsRate... marketsRates);
    
    @Insert
    long insertOneReturnRowNumber(MarketsRate marketsRate);
    
    @Insert
    void insertMarketsRateList(List<MarketsRate> marketsRateList);
    
    @Insert
    void insertArray(MarketsRate[] marketsRateArray);
    
    @Update
    void update(MarketsRate... marketsRates);
    
    @Update
    int updateOneReturnRowNumber(MarketsRate marketsRate);
    
    //@Query also supported UPDATE queries for edit rows
    
    @Delete
    void delete(MarketsRate... marketsRates);
    
    @Delete
    void deleteMarketsRateList(List<MarketsRate> marketsRateList);
    
    @Delete
    int deleteOneReturnRowNumber(MarketsRate marketsRate);
    
    @Query("DELETE FROM marketsrate")
    void delete();
    
    @Query("DELETE FROM marketsrate WHERE date =:date")
    void deleteForData(Date date);
    
    @Query("DELETE FROM marketsrate WHERE date BETWEEN :from AND :to")
    void deleteMarketsRateByDateBetweenDates(Date from, Date to);
}
