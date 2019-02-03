package ua.biz.synergy.currencyrate.model.room.database;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.util.Log;

import java.io.File;

import ua.biz.synergy.currencyrate.model.room.dao.catalogs.BanksCatalogDao;
import ua.biz.synergy.currencyrate.model.room.entity.catalogs.BankRecord;

@Database(version = 1, entities = {BankRecord.class}, exportSchema = false)
abstract public class CatalogsDatabase extends RoomDatabase {
    
    private static final String TAG = CatalogsDatabase.class.getSimpleName();
    private static final boolean memoryOnlyDatabase = false;
    private static final String DB_NAME = "catalogs.db";
    private static volatile CatalogsDatabase INSTANCE = null;
    private static final MutableLiveData<Boolean> isDatabaseCreatedLD = new MutableLiveData<>();
    
    abstract public BanksCatalogDao getBanksCatalogDao();
    
    /**
     * Generate instance of the database
     * @param context Application context
     * @return instance whether the database, that obtain via {@Link create(Context context, boolean memoryOnly)}
     * or already exists database
     */
    public synchronized static CatalogsDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = create(context.getApplicationContext(), memoryOnlyDatabase);
            if (!memoryOnlyDatabase) {
                // notify that the database was created and ready to be use
                updateIsDatabaseCreated(context.getApplicationContext());
            }
        }
        return INSTANCE;
    }
    
    /**
     *
     * @param context Application context
     * @param memoryOnly is database location in memory only
     * @return the instance the database,whether obtaining via {@Link create(Context context, boolean memoryOnly)}
     * or of the already existing database
     */
    public static CatalogsDatabase create(Context context, boolean memoryOnly) {
        if (memoryOnly) {
            return Room.inMemoryDatabaseBuilder(context.getApplicationContext(), CatalogsDatabase.class)
                    .build();
        }
        return Room.databaseBuilder(context.getApplicationContext(), CatalogsDatabase.class, DB_NAME)
                .build();
    }
    
    /**
     * Check whether the database already exists and update a state of the appropriate LiveDate object
     * via {@Link setIsDatabaseCreated(boolean isCreated)}
     *
     * @param context Appplication context
     */
    public static void updateIsDatabaseCreated(final Context context) {
        if (getFullPathNameDatabase(context).exists()) {
            Log.i(TAG, "updateIsDatabaseCreated(): Database exists");
            Log.i(TAG, "databaseFile = " + context.getDatabasePath(DB_NAME).toString());
            setIsDatabaseCreated(true);
        } else {
            setIsDatabaseCreated(false);
        }
    }
    
    /**
     * Obtaining a LiveData object that intended for notify whether the database was created
     *
     * @return a LiveData object that intended for notify whether the database was created
     */
    public LiveData<Boolean> getIsDatabaseCreated() {
        return isDatabaseCreatedLD;
    }
    
    /**
     * Notify for observers was or were not created the database
     *
     * @param isCreated true - database was created; false - database was not created
     */
    private static void setIsDatabaseCreated(boolean isCreated) {
        isDatabaseCreatedLD.postValue(isCreated);
    }
    
    /**
     * Deleting a file of the database
     * @param context Application context
     * @return true - deleting database was successful; else - false
     */
    public boolean deleteDB(final Context context){
        boolean result = true;
        updateIsDatabaseCreated(context);
        if((isDatabaseCreatedLD.getValue() != null) && (isDatabaseCreatedLD.getValue())){
            result = getFullPathNameDatabase(context).delete();
            Log.i(TAG, "getFullPathNameDatabase(context) = " + getFullPathNameDatabase(context) + "; result of the deleting = " + result);
        }
        return result;
    }
    
    /**
     * obtain a full pathname in a file system, including the name of the database.
     * @param context Application context
     * @return File object
     */
    private static File getFullPathNameDatabase(Context context){
        return context.getDatabasePath(DB_NAME);
    }
    
    private static void setDelay(){
        try{
            Thread.sleep(4000);
        }
        catch(InterruptedException ignored){}
    }
}
