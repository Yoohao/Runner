package com.yoyo.runner;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

public class Main2Activity extends AppCompatActivity {

    private int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        getSupportActionBar().hide();


    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        RelativeLayout relativeLayout;
        relativeLayout = (RelativeLayout) findViewById(R.id.activity_main2);
        switch (count){
            case 0:
                relativeLayout.setBackgroundResource(R.drawable.pause_continue);
                break;
            case 1:
                relativeLayout.setBackgroundResource(R.drawable.left);
                break;
            case 2:
                relativeLayout.setBackgroundResource(R.drawable.right);
                break;
            case 3:
                Intent intent = new Intent();
                intent.setClass(this, MainActivity.class);
                startActivity(intent);
                this.finish();
                break;
            default:
                break;
        }
        count ++;
        return super.onTouchEvent(event);
    }
}
