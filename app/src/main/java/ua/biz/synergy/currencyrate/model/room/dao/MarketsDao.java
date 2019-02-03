package ua.biz.synergy.currencyrate.model.room.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import ua.biz.synergy.currencyrate.model.room.entity.Markets;

@Dao
public interface MarketsDao {
        /*
    Markets
     */
    
    @Query("SELECT * FROM markets")
    List<Markets> selectAllMarkets();
    
    @Query("SELECT * FROM markets WHERE id=:id")
    List<Markets> findMarketsById(String id);
    
    @Query("SELECT * FROM markets WHERE id=:id")
    Markets findOneMarketsById(String id);
    
    @Query("SELECT * FROM markets WHERE name=:marketsName")
    List<Markets> findAllMarketsByName(String marketsName);
    
    @Query("SELECT * FROM markets WHERE city=:city")
    List<Markets> findAllMarketsByCity(String city);
    
    @Query("SELECT * FROM markets ORDER BY name")
    List<Markets> selectAllMarketsByName();
    
    @Query("SELECT * FROM markets ORDER BY city")
    List<Markets> selectAllMarketsByCity();
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Markets... markets);
    
    @Insert
    long insertOneReturnRowNumber(Markets market);
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMarketsList(List<Markets> marketsList);
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertReplaceMarketsList(List<Markets> marketsList);
    
    @Insert
    void insertMarketsArray(Markets[] marketsArray);
    
    @Update
    void update(Markets... markets);
    
    @Update
    int updateOneReturnRowNumber(Markets market);
    
    //@Query also supported UPDATE and DELETE queries for edit or delete rows
    
    @Delete
    void delete(Markets... markets);
    
    @Delete
    int deleteOneReturnRowNumber(Markets market);
    
    @Query("DELETE FROM markets")
    void delete();
}
