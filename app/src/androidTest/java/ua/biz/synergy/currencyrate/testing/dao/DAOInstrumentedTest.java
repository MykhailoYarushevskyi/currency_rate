package ua.biz.synergy.currencyrate.testing.dao;

import android.support.annotation.NonNull;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ua.biz.synergy.currencyrate.App;
import ua.biz.synergy.currencyrate.model.room.dao.BanksDao;
import ua.biz.synergy.currencyrate.model.room.dao.BanksRateDao;
import ua.biz.synergy.currencyrate.model.room.dao.MarketsDao;
import ua.biz.synergy.currencyrate.model.room.dao.MarketsRateDao;
import ua.biz.synergy.currencyrate.model.room.database.RateDatabase;
import ua.biz.synergy.currencyrate.model.room.entity.Banks;
import ua.biz.synergy.currencyrate.model.room.entity.BanksRate;
import ua.biz.synergy.currencyrate.model.room.entity.Markets;
import ua.biz.synergy.currencyrate.model.room.entity.MarketsRate;
import ua.biz.synergy.currencyrate.testutil.PopulateEntities;
import ua.biz.synergy.currencyrate.testutil.dataforentitygenerate.ArraysEntityClassAndDataForGenerate;
import ua.biz.synergy.currencyrate.testutil.dataforentitygenerate.ConstantFieldForBanksGenerate;
import ua.biz.synergy.currencyrate.testutil.dataforentitygenerate.ConstantFieldForMarketsGenerate;
import ua.biz.synergy.currencyrate.testutil.dataforentitygenerate.EntitiesClassNameEnum;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class DAOInstrumentedTest implements ArraysEntityClassAndDataForGenerate {
    private final String TAG = DAOInstrumentedTest.class.getSimpleName();
    private RateDatabase db;
    private BanksDao banksDao;
    private BanksRateDao banksRateDao;
    private MarketsDao marketsDao;
    private MarketsRateDao marketsRateDao;
    private boolean memoryOnly = false;
    
    @Before
    public void setUp() {
        // Create a memoryOnly database:
//        db = RateDatabase.create(InstrumentationRegistry.getTargetContext(), memoryOnly);
//        db = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(), RateDatabase.class).build();
        // Create an on disk database:
        // The expression below cause on the db.clearAllTables();:
        // android.database.sqlite.SQLiteCantOpenDatabaseException: unknown error (code 14): Could not open database
//        db = Room.databaseBuilder(InstrumentationRegistry.getContext(), RateDatabase.class, "rates.db").build();
/*        db = RateDatabase.getInstance(InstrumentationRegistry.getTargetContext());
        Log.i(TAG, "RateDatabase.getInstance(InstrumentationRegistry.getTargetContext()) == " + db);*/
    
        db = new App(InstrumentationRegistry.getTargetContext()).getRateDatabase();
        Log.i(TAG, "db = " + db);
        db.clearAllTables();
        banksDao = db.getBanksDao();
        banksRateDao = db.getBanksRateDao();
        marketsDao = db.getMarketsDao();
        marketsRateDao = db.getMarketsRateDao();

        assertTrue(db.isOpen());
    }
    
    @After
    public void endUp()throws Exception {
        db.clearAllTables();
        db.close();
    }
    
    // InstantTaskExecutorRule rule make sure that Room executes
    // all the database operations instantly.
    
/*    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule =
            new InstantTaskExecutorRule();*/
    
    @Test
    public void base() {
//        Log.i(TAG, "banksDao.selectAllBanks().size() = " + banksDao.selectAllBanks().size());
        assertEquals(0, banksDao.selectAllBanks().size());
        assertEquals(0, banksRateDao.selectAllBanksRate().size());
        assertEquals(0, marketsDao.selectAllMarkets().size());
        assertEquals(0, marketsRateDao.selectAllMarketsRate().size());
        // Tests of the Dao's classes
        // !!! Importantly. For correct generate a Foreign Key some entity, that contains a column
        // with the foreign key, for the appropriate entity, whose column uses for make foreign key,
        // the appropriate boolean variable from IS_RANDOM_NUMBER[] must be set "false"
        for (int typeGenerateBanks = 0; typeGenerateBanks <= 2; typeGenerateBanks++) {
            testEntities(BanksDao.class, Banks.class, typeGenerateBanks);
            for (int typeGenerateBanksRate = 0; typeGenerateBanksRate <= 2; typeGenerateBanksRate++) {
                testEntities(BanksRateDao.class, BanksRate.class, typeGenerateBanksRate);
            }
        }
        for (int typeGenerateMarkets = 0; typeGenerateMarkets <= 2; typeGenerateMarkets++) {
            testEntities(MarketsDao.class, Markets.class, typeGenerateMarkets);
            for (int typeGenerateMarketsRate = 0; typeGenerateMarketsRate <= 2; typeGenerateMarketsRate++) {
                testEntities(MarketsRateDao.class, MarketsRate.class, typeGenerateMarketsRate);
            }
        }
    }
    
    /**
     * @param classDao     the {@code Class} object for a {@code class} "DAO" that involves methods,
     *                     that generating {@code Query}, {@code Insert}, {@code Delete}, {@code Update}
     *                     or another command of the {@code SQLite}
     * @param classEntity  the {@code Class} object for a {@code class} "ENTITY" which contains
     *                     a content of a table of a database
     * @param typeGenerate = 0 a direct order of the generation Entity (default);
     *                     = 1 a reverse order of the generation Entity (reverse order both for an {@code id},
     *                     and fore an index of fields an Entity);
     *                     = 2 an an id generate in a direct order, but an index for generating
     *                     fields of an Entity in a reverse order;
     */
    private void testEntities(@NonNull Class classDao, @NonNull Class classEntity, int typeGenerate) {
        
        switch (classDao.getSimpleName()) {
            case "BanksDao": {
                testBanksDao(classEntity, typeGenerate);
                break;
            }
            case "BanksRateDao": {
                testBanksRateDao(classEntity, typeGenerate);
                break;
            }
            case "MarketsDao": {
                testMarketsDao(classEntity, typeGenerate);
                break;
            }
            case "MarketsRateDao": {
                testMarketsRateDao(classEntity, typeGenerate);
                break;
            }
        }
    }
    
    /**
     * Generates the Banks entities and tests queries and others commands of SQLite
     * for the generated Banks entity
     *
     * @param classEntity  look above for the method testEntities
     * @param typeGenerate look above for the method testEntities
     */
    private void testBanksDao(@NonNull Class classEntity, int typeGenerate) {
        int numberEntity = getDefinedNumberEntity(classEntity);
        int maxRandNumber = getMaxRandNumber(classEntity);
        boolean isRandomNumber = isRandomNumberAllowed(classEntity);
        //EntitiesClassNameEnum entitiesClassNameEnum = getEntitiesClassNameEnum(classEntity);
        
        Log.i(TAG, "classEntity = " + classEntity.getSimpleName()
                + "; numberEntity = " + numberEntity
                + "; isRandomNumber = " + isRandomNumber
                + "; maxRandNumber = " + maxRandNumber);
        Log.i(TAG, "========== BanksDao TEST ========= typeGenerate = " + typeGenerate + " ========================================");
        //Generating a new test list of the entity for insert in the database
        List<Banks> banksList;
        if (1 == typeGenerate) {
            banksList = PopulateEntities.fillUpEntitiesReverseOrder(classEntity, numberEntity, maxRandNumber, isRandomNumber);
            Log.i(TAG, banksList.toString());
        } else if (2 == typeGenerate) {
            banksList = PopulateEntities.fillUpEntitiesFieldsReverseOrder(classEntity, numberEntity, maxRandNumber, isRandomNumber);
            Log.i(TAG, banksList.toString());
        } else {
            // if value of "typeGenerate" = 0 or "typeGenerate" is incorrect, calling of the
            // fillUpEntitiesDirectOrder() is default
            banksList = PopulateEntities.fillUpEntitiesDirectOrder(classEntity, numberEntity, maxRandNumber, isRandomNumber);
        }
        // @Insert   //   void insertBanksList(List<Banks> banksList);
        if (banksDao.selectAllBanks().size() != 0) { //the table of the database is not empty
            //    @Delete  //    void delete(Banks... banks);
            List<Banks> listForDelete = banksDao.selectAllBanks();
            for (Banks banksForDelete : listForDelete) {
                banksDao.delete(banksForDelete);
            }
        }
        //     @Insert     //    void insertBanksList(List<Banks> banksList);
        banksDao.insertBanksList(banksList);
        Log.i(TAG, "=========== Test @Query(\"SELECT * FROM banks\") =======");
    
        //    @Query("SELECT * FROM banks")   //    List<Banks> selectAllBanks();
        assertEquals(PopulateEntities.getNumber(), banksDao.selectAllBanks().size());
        assertEquals(banksList.size(), banksDao.selectAllBanks().size());
        Log.i(TAG, "banksDao.selectAllBanks().get(0) = " + banksDao.selectAllBanks().get(0).toString());
        assertEquals(banksList, banksDao.selectAllBanks());
        Log.i(TAG, "=========== Test @Query(\"SELECT * FROM banks WHERE id=:id\")  List<Banks> findBanksById(String id);) =======");
        //    @Query("SELECT * FROM banks WHERE id=:id") // List<Banks> findBanksById(String id);
        for (int countEntity = 0; countEntity < banksList.size(); countEntity++) {
            String idForTest = String.valueOf(countEntity);
            List<Banks> allBanksList = banksDao.selectAllBanks();
            List<Banks> selectedByIdList = new ArrayList<>();
            for (Banks banks : allBanksList) {
                if (banks.id.equals(idForTest)) {
                    selectedByIdList.add(banks);
                }
            }
            Log.i(TAG, "selectedByIdList = " + selectedByIdList);
            assertEquals(selectedByIdList, banksDao.findBanksById(idForTest));
        }
        Log.i(TAG, "=========== Test @Query(\"SELECT * FROM banks WHERE name=:bankName\")    List<Banks> findAllBanksByName(String bankName); =======");
        //    @Query("SELECT * FROM banks WHERE name=:bankName")  //    List<Banks> selectAllBanksByName(String bankName);
        for (int countEntity = 0; countEntity < banksList.size(); countEntity++) {
            String nameForTest = ConstantFieldForBanksGenerate.NAME_HEADER + String.valueOf(countEntity);
            List<Banks> allBanksList = banksDao.selectAllBanks();
            List<Banks> selectedByNameList = new ArrayList<>();
            for (Banks banks : allBanksList) {
                if (banks.name.equals(nameForTest)) {
                    selectedByNameList.add(banks);
                }
            }
            Log.i(TAG, "selectedByNameList = " + selectedByNameList);
            assertEquals(selectedByNameList, banksDao.findAllBanksByName(nameForTest));
        }
        Log.i(TAG, "=========== Test @Query(\"SELECT * FROM banks WHERE name=:bankName\")    List<Banks> findAllBanksByName(String bankName); =======");
        //     @Query("SELECT * FROM banks WHERE name=:bankName") //    List<Banks> selectAllBanksByName(String bankName);
        // Test with duplicate entity field "name" in some entities
        Log.i(TAG, "=========== Test with duplicate entity field \"name\" in some entities =======");
        List<Banks> allBanksList = banksDao.selectAllBanks();
        int numbersOfEntities = allBanksList.size();
        List<Banks> duplicatesBanksList = getListEntitiesWithDuplicatedFields(numbersOfEntities, allBanksList);
        if (duplicatesBanksList != null) {
            banksDao.insertReplaceBanksList(duplicatesBanksList); //replace table content to dublicateBanksList
            Log.i(TAG, "banksDao.selectAllBanks()(duplicatesBanksList) = " + banksDao.selectAllBanks());
            for (int countEntity = 0; countEntity < numbersOfEntities; countEntity++) {
                String nameForTest = ConstantFieldForBanksGenerate.NAME_HEADER + String.valueOf(countEntity);
                List<Banks> selectedByNameList = new ArrayList<>();
                for (Banks banks : duplicatesBanksList) {
                    if (banks.name.equals(nameForTest)) {
                        selectedByNameList.add(banks);
                    }
                }
                Log.i(TAG, "selectedByNameList = " + selectedByNameList);
                Log.i(TAG, "findAllBanksByName(" + nameForTest + ") = " + banksDao.findAllBanksByName(nameForTest));
                
                assertEquals(selectedByNameList, banksDao.findAllBanksByName(nameForTest));
            }
            //inserting with replace is a need for a correct passage of a sequence of the tests with a duplicating field an entity
            banksDao.insertReplaceBanksList(allBanksList); //restore table content
            Log.i(TAG, "banksDao.selectAllBanks()(allBanksList) = " + banksDao.selectAllBanks());
        }
    }
    
    /**
     * Generates the BanksRate entities and tests queries and others commands of SQLite
     * for the generated BanksRate entity
     *
     * @param classEntity  look above for the method testEntities
     * @param typeGenerate look above for the method testEntities
     */
    private void testBanksRateDao(@NonNull Class classEntity, int typeGenerate) {
        int numberEntity = getDefinedNumberEntity(classEntity);
        int maxRandNumber = getMaxRandNumber(classEntity);
        boolean isRandomNumber = isRandomNumberAllowed(classEntity);
        
        Log.i(TAG, "classEntity = " + classEntity.getSimpleName()
                + "; numberEntity = " + numberEntity
                + "; isRandomNumber = " + isRandomNumber
                + "; maxRandNumber = " + maxRandNumber);
        Log.i(TAG, "========== BanksRateDao TEST ========= typeGenerate = " + typeGenerate + " ========================================");
        //Generating a new test list of the entity for insert in the database
        List<BanksRate> banksRateList;
        if (1 == typeGenerate) {
            banksRateList = PopulateEntities.fillUpEntitiesReverseOrder(classEntity, numberEntity, maxRandNumber, isRandomNumber);
            Log.i(TAG, banksRateList.toString());
        } else if (2 == typeGenerate) {
            banksRateList = PopulateEntities.fillUpEntitiesFieldsReverseOrder(classEntity, numberEntity, maxRandNumber, isRandomNumber);
            Log.i(TAG, banksRateList.toString());
        } else {
            // if value of "typeGenerate" = 0 or "typeGenerate" is incorrect, calling of the
            // fillUpEntitiesDirectOrder() is default
            banksRateList = PopulateEntities.fillUpEntitiesDirectOrder(classEntity, numberEntity, maxRandNumber, isRandomNumber);
            Log.i(TAG, banksRateList.toString());
        }
        if (banksRateDao.selectAllBanksRate().size() != 0) { //the table of the database is not empty
            //    @Delete    //    void delete(BanksRate... banksRates);
            List<BanksRate> listForDelete = banksRateDao.selectAllBanksRate();
            for (BanksRate banksRateForDelete : listForDelete) {
                banksRateDao.delete(banksRateForDelete);
            }
        }
        //        @Insert     //    void insertBanksRateList(List<BanksRate> banksRateList);
        banksRateDao.insertBanksRateList(banksRateList);
        Log.i(TAG, "banksRateDao.selectAllBanksRate().size() = " + banksRateDao.selectAllBanksRate().size() +
                "; numberPopulatedEntities = " + PopulateEntities.getNumber());
        //    @Query("SELECT * FROM banksrate")         List<BanksRate> selectAllBanksRate();
        assertEquals(PopulateEntities.getNumber(), banksRateDao.selectAllBanksRate().size());
        assertEquals(banksRateList.size(), banksRateDao.selectAllBanksRate().size());
        //@Query("SELECT * FROM banksrate WHERE id=:id")      List<BanksRate> findBanksRateById(String id);
        for (int countEntity = 0; countEntity < banksRateList.size(); countEntity++) {
            String idForTest = String.valueOf(countEntity);
            List<BanksRate> allBanksRateList = banksRateDao.selectAllBanksRate();
            List<BanksRate> selectedByIdList = new ArrayList<>();
            for (BanksRate banksRate : allBanksRateList) {
                if (banksRate.id.equals(idForTest)) {
                    selectedByIdList.add(banksRate);
                }
            }
            Log.i(TAG, "selectedByIdList = " + selectedByIdList);
            assertEquals(selectedByIdList, banksRateDao.findBanksRateById(idForTest));
        }
        // @Query("SELECT * FROM banksrate ORDER BY date")           List<BanksRate> selectAllBanksRateOrderByDate();
        List<BanksRate> listSelectedOrderByDate = banksRateDao.selectAllBanksRateOrderByDate();
        Log.i(TAG, "===== @Query(\"SELECT * FROM banksrate ORDER BY date\")  List<BanksRate> selectAllBanksRateOrderByDate()");
        for (int countEntity = 0; countEntity < listSelectedOrderByDate.size(); countEntity++) {
            Log.i(TAG, "listSelectedOrderByDate.get(" + countEntity + "): " + listSelectedOrderByDate.get(countEntity));
            if (countEntity > 0) {
                assertTrue(listSelectedOrderByDate.get(countEntity - 1).date.before(listSelectedOrderByDate.get(countEntity).date)
                        || listSelectedOrderByDate.get(countEntity - 1).date.equals(listSelectedOrderByDate.get(countEntity).date));
            }
        }
        //    @Query("SELECT * FROM banksrate WHERE bankid=:bankId AND date BETWEEN :from AND :to ORDER BY date")
        //    abstract List<BanksRate> findBanksRateByBankIdByDateBetweenDates(String bankId, Date from, Date to);
//        List<BanksRate> listSelectedByBankIdAndByDate = banksRateDao.findBanksRateByBankIdByDateBetweenDates();
    }
    
    /**
     * Generates the Markets entities and tests queries and others commands of SQLite
     * for the generated Markets entity
     *
     * @param classEntity  look above for the method testEntities
     * @param typeGenerate look above for the method testEntities
     */
    private void testMarketsDao(@NonNull Class classEntity, int typeGenerate) {
        int numberEntity = getDefinedNumberEntity(classEntity);
        int maxRandNumber = getMaxRandNumber(classEntity);
        boolean isRandomNumber = isRandomNumberAllowed(classEntity);
        
        Log.i(TAG, "classEntity = " + classEntity.getSimpleName()
                + "; numberEntity = " + numberEntity
                + "; isRandomNumber = " + isRandomNumber
                + "; maxRandNumber = " + maxRandNumber);
        Log.i(TAG, "========== MarketsDao TEST ========= typeGenerate = " + typeGenerate + " ========================================");
        //Generating a new test list of the entity for insert in the database
        List<Markets> marketsList;
        if (1 == typeGenerate) {
            marketsList = PopulateEntities.fillUpEntitiesReverseOrder(classEntity, numberEntity, maxRandNumber, isRandomNumber);
            Log.i(TAG, marketsList.toString());
        } else if (2 == typeGenerate) {
            marketsList = PopulateEntities.fillUpEntitiesFieldsReverseOrder(classEntity, numberEntity, maxRandNumber, isRandomNumber);
            Log.i(TAG, marketsList.toString());
        } else {
            // if value of "typeGenerate" = 0 or "typeGenerate" is incorrect, calling of the
            // fillUpEntitiesDirectOrder() is default
            marketsList = PopulateEntities.fillUpEntitiesDirectOrder(classEntity, numberEntity, maxRandNumber, isRandomNumber);
        }
        if (marketsDao.selectAllMarkets().size() != 0) { //the table of the database is not empty
            //    @Delete  //    void delete(Markets... markets);
            List<Markets> listForDelete = marketsDao.selectAllMarkets();
            for (Markets marketsForDelete : listForDelete) {
                marketsDao.delete(marketsForDelete);
            }
        }
        Log.i(TAG, "marketsList.get(0): " + marketsList.get(0).toString() + "\n"
                + "marketsList.get(1): " + marketsList.get(1).toString());
        //     @Insert     //    void insertMarketsList(List<Markets> marketsList);
        //TODO
        marketsDao.insertMarketsList(marketsList);
        Log.i(TAG, "marketsDao.selectAllMarkets().size() = " + marketsDao.selectAllMarkets().size() +
                "; numberPopulatedEntities = " + PopulateEntities.getNumber());
        Log.i(TAG, "=========== Test  @Query(\"SELECT * FROM markets\" =================");
        //                @Query("SELECT * FROM markets")
        assertEquals(PopulateEntities.getNumber(), marketsDao.selectAllMarkets().size());
        assertEquals(marketsList.size(), marketsDao.selectAllMarkets().size());
        Log.i(TAG, "marketsList.get(0): " + marketsList.get(0).toString());
      Log.i(TAG, "marketsDao.selectAllMarkets().get(0): " + marketsDao.selectAllMarkets().get(0).toString()
                              + "\n marketsDao.selectAllMarkets().get(1): " + marketsDao.selectAllMarkets().get(1).toString());
        assertEquals(marketsList, marketsDao.selectAllMarkets());
        Log.i(TAG, "=========== Test  @Query(\"SELECT * FROM markets WHERE id=:id\") =================");
//                @Query("SELECT * FROM markets WHERE id=:id")
        for (int countEntity = 0; countEntity < marketsList.size(); countEntity++) {
            String idForTest = String.valueOf(countEntity);
            List<Markets> allMarketsList = marketsDao.selectAllMarkets();
            List<Markets> selectedByIdList = new ArrayList<>();
            for (Markets markets : allMarketsList) {
                if (markets.id.equals(idForTest)) {
                    selectedByIdList.add(markets);
                }
            }
            //TODO
//            Log.i(TAG, "selectedByIdList = " + selectedByIdList);
//            assertEquals(selectedByIdList, marketsDao.findMarketsById(idForTest));
        }
        Log.i(TAG, "=========== Test @Query(\"SELECT * FROM markets WHERE name=:marketsName\") List<Markets> findAllMarketsByName(String marketsName);=======");
//                @Query("SELECT * FROM markets WHERE name=:marketsName")  //          List<Markets> findAllMarketsByName(String marketsName);
        for (int countEntity = 0; countEntity < marketsList.size(); countEntity++) {
            String nameForTest = ConstantFieldForMarketsGenerate.NAME_HEADER + String.valueOf(countEntity);
            List<Markets> allMarketsList = marketsDao.selectAllMarkets();
            List<Markets> selectedByNameList = new ArrayList<>();
            for (Markets markets : allMarketsList) {
                if (markets.name.equals(nameForTest)) {
                    selectedByNameList.add(markets);
                }
            }//TODO
//            Log.i(TAG, "selectedByNameList = " + selectedByNameList);
//            assertEquals(selectedByNameList, marketsDao.findAllMarketsByName(nameForTest));
        }
        Log.i(TAG, "=========== Test @Query(\"SELECT * FROM markets WHERE name=:marketsName\") List<Markets> findAllMarketsByName(String marketsName);=======");
        Log.i(TAG, "=========== Test with duplicate entity field \"name\" in some entities =======");
//                @Query("SELECT * FROM markets WHERE name=:marketsName")  //       List<Markets> findAllMarketsByName(String marketsName);
        // Test with duplicate entity field "name" in some entities
        
        List<Markets> allMarketsList = marketsDao.selectAllMarkets();
        int numbersOfEntities = allMarketsList.size();
        List<Markets> duplicatesMarketsList = getListEntitiesWithDuplicatedFields(numbersOfEntities, allMarketsList);
        if (duplicatesMarketsList != null) {
            marketsDao.insertReplaceMarketsList(duplicatesMarketsList); // replace table content to duplicatesMarketsList
            Log.i(TAG, "marketsDao.selectAllMarkets()(duplicatesMarketsList) = " + marketsDao.selectAllMarkets());
            for (int countEntity = 0; countEntity < numbersOfEntities; countEntity++) {
                String nameForTest = ConstantFieldForMarketsGenerate.NAME_HEADER + String.valueOf(countEntity);
                List<Markets> selectedByNameList = new ArrayList<>();
                for (Markets marketss : duplicatesMarketsList) {
                    if (marketss.name.equals(nameForTest)) {
                        selectedByNameList.add(marketss);
                    }
                }
                //TODO
//                Log.i(TAG, "selectedByNameList = " + selectedByNameList);
//                Log.i(TAG, "findAllMarketsByName(" + nameForTest + ") = " + marketsDao.findAllMarketsByName(nameForTest));
                
//                assertEquals(selectedByNameList, marketsDao.findAllMarketsByName(nameForTest));
            }
            //inserting with replace is a need for a correct passage of a sequence of the tests with a duplicating field an entity
            marketsDao.insertReplaceMarketsList(allMarketsList); //restore table content
            Log.i(TAG, "marketsDao.selectAllMarkets()(allMarketsList) = " + marketsDao.selectAllMarkets());
        }
    }
    
    /**
     * Generates the MarketsRate entities and tests queries and others commands of SQLite
     * for the generated MarketsRate entity
     *
     * @param classEntity  look above for the method testEntities
     * @param typeGenerate look above for the method testEntities
     */
    private void testMarketsRateDao(@NonNull Class classEntity, int typeGenerate) {
        int numberEntity = getDefinedNumberEntity(classEntity);
        int maxRandNumber = getMaxRandNumber(classEntity);
        boolean isRandomNumber = isRandomNumberAllowed(classEntity);
        
        Log.i(TAG, "classEntity = " + classEntity.getSimpleName()
                + "; numberEntity = " + numberEntity
                + "; isRandomNumber = " + isRandomNumber
                + "; maxRandNumber = " + maxRandNumber);
        Log.i(TAG, "========== MarketsRateDao TEST ========= typeGenerate = " + typeGenerate + " ========================================");
        //Generating a new test list of the entity for insert in the database
        List<MarketsRate> ratesList;
        if (1 == typeGenerate) {
            ratesList = PopulateEntities.fillUpEntitiesReverseOrder(classEntity, numberEntity, maxRandNumber, isRandomNumber);
            Log.i(TAG, ratesList.toString());
        } else if (2 == typeGenerate) {
            ratesList = PopulateEntities.fillUpEntitiesFieldsReverseOrder(classEntity, numberEntity, maxRandNumber, isRandomNumber);
            Log.i(TAG, ratesList.toString());
        } else {
            // if value of "typeGenerate" = 0 or "typeGenerate" is incorrect, calling of the
            // fillUpEntitiesDirectOrder() is default
            ratesList = PopulateEntities.fillUpEntitiesDirectOrder(classEntity, numberEntity, maxRandNumber, isRandomNumber);
            Log.i(TAG, ratesList.toString());
        }
        if (marketsRateDao.selectAllMarketsRate().size() != 0) { // the table of the database is not empty
            //    @Delete   //    void delete(MarketsRate... marketsRates);
            List<MarketsRate> listForDelete = marketsRateDao.selectAllMarketsRate();
            for (MarketsRate marketsRateForDelete : listForDelete) {
                marketsRateDao.delete(marketsRateForDelete);
            }
        }
        //    @Insert  //    void insertMarketsRateList(List<MarketsRate> marketsRateList);
        marketsRateDao.insertMarketsRateList(ratesList);
        Log.i(TAG, "marketsRateDao.selectAllMarkets().size() = " + marketsRateDao.selectAllMarketsRate().size() +
                "; numberPopulatedEntities = " + PopulateEntities.getNumber());
        assertEquals(PopulateEntities.getNumber(), marketsRateDao.selectAllMarketsRate().size());
        assertEquals(ratesList.size(), marketsRateDao.selectAllMarketsRate().size());
        //    @Query("SELECT * FROM marketsrate WHERE id=:id")       List<MarketsRate> findMarketsRateById(String id);
        for (int countEntity = 0; countEntity < ratesList.size(); countEntity++) {
            String idForTest = String.valueOf(countEntity);
            List<MarketsRate> allMarketsRate = marketsRateDao.selectAllMarketsRate();
            List<MarketsRate> selectedByIdList = new ArrayList<>();
            for (MarketsRate marketsRate : allMarketsRate) {
                if (marketsRate.id.equals(idForTest)) {
                    selectedByIdList.add(marketsRate);
                }
            }
            Log.i(TAG, "selectedByIdList = " + selectedByIdList);
            assertEquals(selectedByIdList, marketsRateDao.findMarketsRateById(idForTest));
        }
        //    @Query("SELECT * FROM marketsrate ORDER BY date") //    List<MarketsRate> selectAllMarketsRateByDate();
        List<MarketsRate> listSelectedOrderByDate = marketsRateDao.selectAllMarketsRateByDate();
        Log.i(TAG, "===== @Query(\"SELECT * FROM banksrate ORDER BY date\")  List<MarketsRate> selectAllMarketsRateOrderByDate()");
        for (int countEntity = 0; countEntity < listSelectedOrderByDate.size(); countEntity++) {
            Log.i(TAG, "listSelectedOrderByDate.get(" + countEntity + "): " + listSelectedOrderByDate.get(countEntity));
            if (countEntity > 0) {
                assertTrue(listSelectedOrderByDate.get(countEntity - 1).date.before(listSelectedOrderByDate.get(countEntity).date));
            }
        }
    }
    
    /**
     * returns an int value of a number of the entities that are allowed for a generator of the Entity.
     * Exceptions  	java.lang.ArrayIndexOutOfBoundsException
     *
     * @param classEntity {@code Class} of the entity
     * @return an integer number of an entity that is defined
     */
    @Override
    public int getDefinedNumberEntity(Class classEntity) {
        return ArraysEntityClassAndDataForGenerate.NUMBER_ENTITY[getIndexClassEntityInArraysEntity(classEntity)];
    }
    
    /**
     * returns an integer value of a maximum random number of the entities that are allowed,
     * for a generator of the Entities
     * Exceptions  	{@code java.lang.ArrayIndexOutOfBoundsException}
     *
     * @param classEntity {@code Class} of the entity
     * @return an integer value of a maximum random number of entities, that is allowed.
     */
    @Override
    public int getMaxRandNumber(Class classEntity) {
        return ArraysEntityClassAndDataForGenerate.MAX_RAND_NUMBER[getIndexClassEntityInArraysEntity(classEntity)];
    }
    
    /**
     * returns boolean value of a isRandomNumber for generator of the Entity
     * Exceptions  	java.lang.ArrayIndexOutOfBoundsException
     *
     * @param classEntity {@code Class} object of the entity
     * @return {@code true} if a generator of entities can use a random number for a generate,
     * otherwise {@code false}
     */
    @Override
    public boolean isRandomNumberAllowed(Class classEntity) {
        return ArraysEntityClassAndDataForGenerate.IS_RANDOM_NUMBER[getIndexClassEntityInArraysEntity(classEntity)];
    }
    
    @Override
    public EntitiesClassNameEnum getEntitiesClassNameEnum(Class classEntity) {
        return ArraysEntityClassAndDataForGenerate.ENTITIES_NAME_ENUM[getIndexClassEntityInArraysEntity(classEntity)];
    }
    
    /**
     * Generates from a source of a list of entities a list of the entities that have a duplicate
     * field or fields
     *
     * @param numbersOfEntities A number of the entities
     * @param allEntitiesList   A source of a list of the entities
     * @param <T>               A type of the entity
     * @return the list of the entity with a duplicated fields
     */
    private <T> List<T> getListEntitiesWithDuplicatedFields(int numbersOfEntities, List<T> allEntitiesList) {
        if (allEntitiesList != null) {
            if (allEntitiesList.get(0) instanceof Banks) {
                if (numbersOfEntities >= 2) {
                    Banks nameFromThis = (Banks) allEntitiesList.get(0);
                    Banks nameToThis = (Banks) allEntitiesList.get(numbersOfEntities - 1);
                    Banks duplicateName = new Banks(nameToThis.id, nameFromThis.name, nameToThis.title,
                            nameToThis.urlSite, nameToThis.urlsRate, nameToThis.city, nameToThis.jsonString, nameToThis.natureRates, nameToThis.placeLocation);
                    
                    Banks[] entitiesArray = allEntitiesList.toArray(new Banks[0]);
                    List<Banks> duplicatesEntitiesList = Arrays.asList(entitiesArray);
                    
                    duplicatesEntitiesList.set(numbersOfEntities - 1, duplicateName);
                    return (List<T>) duplicatesEntitiesList;
                }
            } else if (allEntitiesList.get(0) instanceof Markets) {
                if (numbersOfEntities >= 2) {
                    Markets nameFromThis = (Markets) allEntitiesList.get(0);
                    Markets nameToThis = (Markets) allEntitiesList.get(numbersOfEntities - 1);
                    Markets duplicateName = new Markets(nameToThis.id, nameFromThis.name, nameToThis.title,
                            nameToThis.urlSite, nameToThis.urlsRate, nameToThis.city, nameToThis.jsonString, nameToThis.natureRates, nameToThis.placeLocation);
                    
                    Markets[] entitiesArray = allEntitiesList.toArray(new Markets[0]);
                    List<Markets> duplicatesEntitiesList = Arrays.asList(entitiesArray);
                    
                    duplicatesEntitiesList.set(numbersOfEntities - 1, duplicateName);
                    return (List<T>) duplicatesEntitiesList;
                }
            }
        }
        return null;
    }
}
