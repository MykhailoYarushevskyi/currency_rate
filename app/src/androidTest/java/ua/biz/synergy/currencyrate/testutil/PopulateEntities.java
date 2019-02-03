package ua.biz.synergy.currencyrate.testutil;

import java.util.ArrayList;
import java.util.Random;

public class PopulateEntities {
    
    static final String TAG = PopulateEntities.class.getSimpleName();
    
    private static int returnNumberBack; //value of the number of the entities
    
    /**
     * generating a certain number of entities in direct order.
     * Number of the entities define either value
     * of the "numberEntity" parameter directly, or by obtain random number.
     *
     * @param numberEntity   defined amount of the Entities for generating these
     *                       define number of the entities if parameter "isRandomNumber" is false
     * @param isRandomNumber defines, either will be random amount of the Entities for generating these
     *                       true - Number of the entities define by obtain random number
     *                       false - Number of the entities define value
     *                       of the "numberEntity" parameter directly
     * @param maxRandNumber  //maximum possible random amount of the Entities for generating these
     *                       if "maxRandNumber" > 0,  then making calculation random number
     *                       if "maxRandNumber" <= 0, a random number of entities are assigning in 0;
     * @return list of the entities
     */
    public static <T> ArrayList<T> fillUpEntitiesDirectOrder(Class<T> classEntity,
                                                             int numberEntity,
                                                             int maxRandNumber,
                                                             boolean isRandomNumber) {
        ArrayList<T> result = new ArrayList<>();
        int number = numberEntity;
        
        if (isRandomNumber) {
            number = randNumber(maxRandNumber);
        }
        // Importantly. In loop here, the index "count" must begin with 0 for correctness indexing
        // List elements (Entities) when generating
        int idEntity = 0;
        for (int count = 0; count < number; count++) {
            idEntity = count;
            result.add((T) GeneratorEntity.getGeneratedEntity(classEntity, count, idEntity));
        }
        returnNumberBack = number;
        return result;
    }
    
    
    /**
     * generating a certain number of entities in a reverse order (reverse order both for an id,
     * and fore an index of fields an Entity)
     * Number of the entities define either value
     * of the "numberEntity" parameter directly, or by obtain random number.
     *
     * @param numberEntity   defined amount of the Entities for generating these
     *                       define number of the entities if parameter "isRandomNumber" is false
     * @param isRandomNumber defines, either will be random amount of the Entities for generating these
     *                       true - Number of the entities define by obtain random number
     *                       false - Number of the entities define value
     *                       of the "numberEntity" parameter directly
     * @param maxRandNumber  //maximum possible random amount of the Entities for generating these
     *                       if "maxRandNumber" > 0,  then making calculation random number
     *                       if "maxRandNumber" <= 0, a random number of entities are assigning in 0;
     * @return list of the entities
     */
    public static <T> ArrayList<T> fillUpEntitiesReverseOrder(Class<T> classEntity,
                                                             int numberEntity,
                                                             int maxRandNumber,
                                                             boolean isRandomNumber) {
        ArrayList<T> result = new ArrayList<>();
        int number = numberEntity;
        
        if (isRandomNumber) {
            number = randNumber(maxRandNumber);
        }
        // Importantly. In loop here, the index "count" must begin with 0 for correctness indexing
        // List elements (Entities) when generating
        int idEntity = 0;
        for (int count = number -1; count >= 0; count--) {
            idEntity = count;
            result.add((T) GeneratorEntity.getGeneratedEntity(classEntity, count, idEntity));
        }
        returnNumberBack = number;
        return result;
    }
    
    /**
     * generating a certain number of entities in reverse order (an id generate in a direct order,
     * but an index for generating fields of an Entity in a reverse order)
     * Number of the entities define either value
     * of the "numberEntity" parameter directly, or by obtain random number.
     *
     * @param numberEntity   defined amount of the Entities for generating these
     *                       define number of the entities if parameter "isRandomNumber" is false
     * @param isRandomNumber defines, either will be random amount of the Entities for generating these
     *                       true - Number of the entities define by obtain random number
     *                       false - Number of the entities define value
     *                       of the "numberEntity" parameter directly
     * @param maxRandNumber  //maximum possible random amount of the Entities for generating these
     *                       if "maxRandNumber" > 0,  then making calculation random number
     *                       if "maxRandNumber" <= 0, a random number of entities are assigning in 0;
     * @return list of the entities
     */
    public static <T> ArrayList<T> fillUpEntitiesFieldsReverseOrder(Class<T> classEntity,
                                                              int numberEntity,
                                                              int maxRandNumber,
                                                              boolean isRandomNumber) {
        ArrayList<T> result = new ArrayList<>();
        int number = numberEntity;
        
        if (isRandomNumber) {
            number = randNumber(maxRandNumber);
        }
        // Importantly. In loop here, the index "count" must begin with 0 for correctness indexing
        // List elements (Entities) when generating
        int idEntity = 0;
        for (int count = number -1; count >= 0; count--) {
            result.add((T)GeneratorEntity.getGeneratedEntity(classEntity, count, idEntity));
            idEntity++;
        }
        returnNumberBack = number;
        return result;
    }
    
    /**
     * generates a random number that less or equal the parameter {@code maxNumber}
     * and grate or equal 1
     * @param maxNumber an integer value of a maximum random number, that is allowed.
     * @return an integer value that > 0 and <= a parameter {@code maxNumber}
     * if the parameter > 0, otherwise the value = 1
     */
    private static int randNumber(int maxNumber) {
        int randomNumber = new Random().nextInt(maxNumber + 1);
        return randomNumber > 0 ? randomNumber : 1;
    }
    
    /**
     * @return a determined value of the number of the entities
     */
    
    public static int getNumber() {
        return returnNumberBack;
    }
}
