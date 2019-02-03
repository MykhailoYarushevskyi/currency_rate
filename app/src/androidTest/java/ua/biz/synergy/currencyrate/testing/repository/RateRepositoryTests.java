package ua.biz.synergy.currencyrate.testing.repository;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import ua.biz.synergy.currencyrate.App;
import ua.biz.synergy.currencyrate.repository.RateRepository;

import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class RateRepositoryTests {
    
    final String TAG = RateRepositoryTests.class.getSimpleName();
    Context context;
    RateRepository rateRepository;
    
    @Before
    public void setUp() {
        context = InstrumentationRegistry.getTargetContext();
        rateRepository = new App(InstrumentationRegistry.getTargetContext()).getRateRepository();
    }
    
    @After
    public void endUp() {
        rateRepository = null;
    }
    
    @Test
    public void base() {
        assertTrue(rateRepository != null);
    }
}
