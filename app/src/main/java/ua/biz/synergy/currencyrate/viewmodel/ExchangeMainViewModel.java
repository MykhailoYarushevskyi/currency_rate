package ua.biz.synergy.currencyrate.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

public class ExchangeMainViewModel extends AndroidViewModel {
    
    public ExchangeMainViewModel(@NonNull Application application) {
        super(application);
    }
    
    /**
     * Return the application.
     */
    @NonNull
    @Override
    public <T extends Application> T getApplication() {
        return super.getApplication();
    }
    
    /**
     * This method will be called when this ViewModel is no longer used and will be destroyed.
     * <p>
     * It is useful when ViewModel observes some data and you need to clear this subscription to
     * prevent a leak of this ViewModel.
     */
    @Override
    protected void onCleared() {
        super.onCleared();
    }
}
