package anish.tutorial.toc.ui.hints;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HintViewModel extends ViewModel {
    private MutableLiveData<String> mText;

    public HintViewModel(){
        mText = new MutableLiveData<>();
        mText.setValue("This is Hint Fragment");

    }
    public LiveData<String> getString(){
        return mText;
    }
}
