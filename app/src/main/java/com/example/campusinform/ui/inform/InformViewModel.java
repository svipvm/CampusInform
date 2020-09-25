package com.example.campusinform.ui.inform;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class InformViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public InformViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is notifications fragment");


    }

    public LiveData<String> getText() {
        return mText;
    }
}