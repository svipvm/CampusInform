package com.example.campusinform.ui.score;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.campusinform.R;

public class ScoreFragment extends Fragment {

    private ScoreViewModel scoreViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        scoreViewModel =
                ViewModelProviders.of(this).get(ScoreViewModel.class);
        View root = inflater.inflate(R.layout.fragment_score, container, false);
        final TextView textView = root.findViewById(R.id.text_score);
        scoreViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}