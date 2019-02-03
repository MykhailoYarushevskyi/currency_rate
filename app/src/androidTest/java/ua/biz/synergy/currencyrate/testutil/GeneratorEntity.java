package ua.biz.synergy.currencyrate.testutil;

import android.support.annotation.NonNull;
import android.util.Log;

import ua.biz.synergy.currencyrate.testutil.dataforentitygenerate.ArraysEntityClassAndDataForGenerate;
import ua.biz.synergy.currencyrate.testutil.dataforentitygenerate.BanksEntityGenerator;
import ua.biz.synergy.currencyrate.testutil.dataforentitygenerate.BanksRateEntityGenerator;
import ua.biz.synergy.currencyrate.testutil.dataforentitygenerate.EntitiesClassNameEnum;
import ua.biz.synergy.currencyrate.testutil.dataforentitygenerate.MarketsEntityGenerator;
import ua.biz.synergy.currencyrate.testutil.dataforentitygenerate.MarketsRateEntityGenerator;

public class GeneratorEntity{
    private static final String TAG = GeneratorEntity.class.getSimpleName();
    
    /**
     * the method returns an instance of an entity, whose class value
     * appropriate to the value of the parameter classEntity
     * @param classEntity value of a Class entity that must be generate
     * @param sequenceNumberEntity for generate fields of entities
     * @param <T> the returning object type
     * @return the instance of an entity, whose class value
     *         appropriate to the value of the parameter classEntity
     */
    public static <T> T getGeneratedEntity(@NonNull Class classEntity, int sequenceNumberEntity, int idEntity) {

        T entity = null;
        
        EntitiesClassNameEnum entitiesClassNameEnum =
                ArraysEntityClassAndDataForGenerate.ENTITIES_NAME_ENUM[getIndexClassEntityInArraysEntity(classEntity)];
        switch (entitiesClassNameEnum) {
            case BANKS: {
                entity = (T) BanksEntityGenerator.generateBanksEntityForRecord(sequenceNumberEntity, idEntity);
                Log.i(TAG, "CALLED BanksEntityGenerator.generateBanksEntityForRecord(sequenceNumberEntity)"
                        + "; entity= " + entity.toString());
                break;
            }
            case BANKS_RATE: {
                entity = (T) BanksRateEntityGenerator.generateBanksRateEntityForRecord(sequenceNumberEntity, idEntity);
                Log.i(TAG, "CALLED BanksRateEntityGenerator.generateBanksRateEntityForRecord(sequenceNumberEntity)"
                        + "; entity= " + entity.toString());
                break;
            }
            case MARKETS: {
                entity = (T) MarketsEntityGenerator.generateMarketsEntityForRecord(sequenceNumberEntity, idEntity);
                Log.i(TAG, "CALLED MarketsEntityGenerator.generateMarketsEntityForRecord(sequenceNumberEntity"
                        + "; entity= " + entity.toString());
                break;
            }
            case MARKETS_RATE: {
                entity = (T) MarketsRateEntityGenerator.generateMarketsRateEntityForRecord(sequenceNumberEntity, idEntity);
                Log.i(TAG, "CALLED MarketsRateEntityGenerator.generateMarketsRateEntityForRecord(sequenceNumberEntity"
                        + "; entity= " + entity.toString());
                break;
            }
        }
        return entity;
    }
    /**
     * calculating index of an array of classes of entities for given value of the class
     * @param classEntity
     * @return index value >=0 or =-1 if classEntity not found
     */
    private static int getIndexClassEntityInArraysEntity(Class classEntity) {
        int ind = -1;
        for (int index = 0; index < ArraysEntityClassAndDataForGenerate.ENTITIES_CLASS.length; index++) {
            if (ArraysEntityClassAndDataForGenerate.ENTITIES_CLASS[index] == classEntity) {
                ind = index;
                break;
            }
        }
        return ind;
    }
    
}
