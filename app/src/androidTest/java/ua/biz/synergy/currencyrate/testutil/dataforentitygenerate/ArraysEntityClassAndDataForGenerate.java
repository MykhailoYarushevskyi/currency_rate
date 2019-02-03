package ua.biz.synergy.currencyrate.testutil.dataforentitygenerate;

import ua.biz.synergy.currencyrate.model.room.entity.Banks;
import ua.biz.synergy.currencyrate.model.room.entity.BanksRate;
import ua.biz.synergy.currencyrate.model.room.entity.Markets;
import ua.biz.synergy.currencyrate.model.room.entity.MarketsRate;

/**
 * contains methods and arrays with a data, that will use
 * in a processes generating of an entity
 */
public interface ArraysEntityClassAndDataForGenerate {
    String[] ENTITIES_NAME = {"Banks", "BanksRate", "Markets", "MarketsRate"};

    EntitiesClassNameEnum[] ENTITIES_NAME_ENUM = {EntitiesClassNameEnum.BANKS, EntitiesClassNameEnum.BANKS_RATE, EntitiesClassNameEnum.MARKETS, EntitiesClassNameEnum.MARKETS_RATE};
    
    Class[] ENTITIES_CLASS = {Banks.class, BanksRate.class, Markets.class, MarketsRate.class};
    
    // defined amount of the Entities for generating these.
    // Define number of the entities if appropriate IS_RANDOM_NUMBER[] element is false
    // the value of the elements of the array must be > 0.
    int[] NUMBER_ENTITY = {3, 7, 2, 8};
    
    // defines, either will be random amount of the Entities for generating these.
    //     true - Number of the entities define by obtain random number
    //     false - Number of the entities define value of the "numberEntity" parameter directly
    // !!! Importantly. For correct generate a Foreign Key some entity, that contains a column with the foreign key,
    // for the appropriate entity, whose column uses for make foreign key,
    // the appropriate boolean variable from IS_RANDOM_NUMBER[] must be set "false"
    boolean[] IS_RANDOM_NUMBER = {false, true, false, true};
    
    // maximum possible random amount of the Entities for generating these
    // if "maxRandNumber" > 0,  then making calculation random number
    // if "maxRandNumber" <= 0, a random number of entities are assigning in 1;
    int[] MAX_RAND_NUMBER = {10, 4, 7, 6};
    
    int getDefinedNumberEntity(Class classEntity);
    int getMaxRandNumber(Class classEntity);
    boolean isRandomNumberAllowed(Class classEntity);
    EntitiesClassNameEnum getEntitiesClassNameEnum(Class classEntity);
    /**
     * calculating index of an array of classes of entities for given value of the class
     * @param classEntity
     * @return index value >=0 or =-1 if classEntity not found
     */
    default int getIndexClassEntityInArraysEntity(Class classEntity) {
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
