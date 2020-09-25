package com.example.campusinform.ui.score;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ScoreViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ScoreViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("The score function has not been developed.");
    }

    public LiveData<String> getText() {
        return mText;
    }
}