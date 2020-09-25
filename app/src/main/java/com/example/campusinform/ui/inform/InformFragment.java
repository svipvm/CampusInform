package com.example.campusinform.ui.inform;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ShareActionProvider;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.campusinform.MainActivity;
import com.example.campusinform.R;
import com.example.campusinform.Spider;

import java.util.ArrayList;

public class InformFragment extends Fragment {

    private InformViewModel informViewModel;
    private EditText editTextUser, editTextPass;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        informViewModel =
                ViewModelProviders.of(this).get(InformViewModel.class);
        View root = inflater.inflate(R.layout.fragment_inform, container, false);
//        final TextView textView = root.findViewById(R.id.text_notifications);
//        informViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
        editTextUser = root.findViewById(R.id.editTextTextPersonName);
        editTextPass = root.findViewById(R.id.editTextTextPassword);

        Button button_login = root.findViewById(R.id.button_login);
        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = editTextUser.getText().toString();
                String passwrod = editTextPass.getText().toString();
//                System.out.println(username + " - " + passwrod);
                if("".equals(username) || "".equals(passwrod)) {
//                    System.out.println("请输入账号、密码！");
                    Toast.makeText(getActivity(), "请输入账号、密码！", Toast.LENGTH_SHORT).show();
                } else {
                    System.out.println("Debug start");
                    SharedPreferences sharedPreferences = getActivity().getSharedPreferences("Account", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("username", username);
                    editor.putString("password", passwrod);
                    editor.apply();
                    try {
                        SpiderThread spider = new SpiderThread(username, passwrod);
                        spider.start();
                        spider.join();
                        saveTableData(spider.result);
                    } catch (InterruptedException e) {
//                        System.out.println("网络不佳或账号密码错误！");
                        Toast.makeText(getActivity(), "网络不佳或账号密码错误！", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                    System.out.println("Debug end");
                }
            }
        });
        return root;
    }

    private void saveTableData(ArrayList<String> result) {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("Data", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        for(int i = 0; i < result.size(); i++) {
            String course = result.get(i);
            editor.putString(Integer.toString(i), course);

            System.out.println(course);
//            switch (course.get(1)) {
//                case "一": day = 1; break;
//                case "二": day = 2; break;
//                case "三": day = 3; break;
//                case "四": day = 4; break;
//                case "五": day = 5; break;
//                case "六": day = 6; break;
//                case "日": day = 7; break;
//            }
//            int start = Integer.parseInt(course.get(2));
//            int end = Integer.parseInt(course.get(3));
//            System.out.println(course.get(0) + " - " + course.get(4) + " : " + day);
//            courses.add(new Course(course.get(0), course.get(4), day, start, end));
        }
        editor.apply();
    }


}