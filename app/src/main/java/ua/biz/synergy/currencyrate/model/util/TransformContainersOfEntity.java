package ua.biz.synergy.currencyrate.model.util;

import java.util.Collection;

public class TransformContainersOfEntity<T> {
    /**
     * Transformation a Collection to an Array
     * @param listEntity Collection<T> for transforming in array
     * @param mockArray any type T array fom mock array in method newInstance()
     * @param <T> type of the object
     * @return The array that is a result transformation from the Collection object
     */
    public static <T> T[] collectionToArray(Collection<T> listEntity, T[] mockArray) {
        return listEntity.toArray(mockArray); //"Java Generics and Collections" by Maurice Naftalin and Philip Wadler page 85
    }
    
    /**
     * Transformation a Collection to an Array
     * The same method that a "collectionToArray(Collection<T> listEntity, T[] mockArray)",
     * with some other implementation
     * @param listEntity Collection<T> for transforming in array
     * @param mockArray any type T array fom mock array in method newInstance()
     * @param <T> type of the object
     * @return The array that is a result transformation from the Collection object
     */
    
    public static <T> T[] fromCollectionToArray(Collection<T> listEntity, T[] mockArray) {
        int index = 0;
//        T[] entityArray = (T[]) new Object[listEntity.size()]; //warning: Unchecked cast: 'java.lang.Object[]' to 'T[]'
        T[] entityArray = (T[]) java.lang.reflect.Array.
                newInstance(mockArray.getClass().getComponentType(), listEntity.size());
        for (T entity : listEntity) {
            entityArray[index++] = entity;
        }
        if(index < entityArray.length) entityArray[index] = null; //Unless length mockArray was grate than listEntity.size()
        return entityArray;
    }
}
