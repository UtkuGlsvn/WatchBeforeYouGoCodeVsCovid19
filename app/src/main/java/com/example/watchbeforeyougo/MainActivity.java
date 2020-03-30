package com.example.watchbeforeyougo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.app.FragmentManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.watchbeforeyougo.fragment.FragmentMap;
import com.example.watchbeforeyougo.fragment.FragmentStaticts;

public class MainActivity extends AppCompatActivity {

    Button mapbtn,statictsbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mapbtn=findViewById(R.id.mapbtn);
        statictsbtn=findViewById(R.id.statictsbtn);
        fragmentAdd(FragmentMap.newInstance(),"map");
    }

    void fragmentAdd(Fragment fragment,String tag)
    {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentGroup, fragment);
        transaction.addToBackStack(tag);
        transaction.commitAllowingStateLoss();
    }

    public void onClick(View v){
        switch (v.getId()) {
            case R.id.mapbtn:
                fragmentAdd(FragmentMap.newInstance(),"map");
                break;
            case R.id.statictsbtn:
                fragmentAdd(FragmentStaticts.newInstance(),"statict");
                break;
        }
    }

    @Override
    public void onBackPressed() {
        this.finish();
        super.onBackPressed();
    }
}
