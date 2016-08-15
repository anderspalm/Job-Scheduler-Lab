package com.example.ander.merpderp;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.ComponentName;
import android.graphics.Color;
import android.os.Handler;
import android.os.Looper;
import android.os.PersistableBundle;
import android.support.annotation.ColorInt;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements ServiceSingleton.DataChangeListener {

    TextView mTV1, mTV2, mTV3;
    public static int mNum;
    private static final String TAG = "MainActivity";
    JobScheduler mJobScheduler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mNum = 0;

        ServiceSingleton.getInstance().setListener(MainActivity.this);

        mTV1 = (TextView) findViewById(R.id.tv1);
        mTV2 = (TextView) findViewById(R.id.tv2);
        mTV3 = (TextView) findViewById(R.id.tv3);

        PersistableBundle timer1 = new PersistableBundle();
        timer1.putString("timer", "1");
        PersistableBundle timer2 = new PersistableBundle();
        timer2.putString("timer", "2");
        PersistableBundle timer3 = new PersistableBundle();
        timer3.putString("timer", "3");

        mJobScheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);

        JobInfo jobInfo1 = new JobInfo.Builder(1, new ComponentName(getPackageName(), MyService.class.getName()))
//                .setRequiresCharging(false)
                .setPeriodic(1_000)
                .setExtras(timer1)
                .build();
        mJobScheduler.schedule(jobInfo1);

        JobInfo jobInfo2 = new JobInfo.Builder(2, new ComponentName(getPackageName(), MyService.class.getName()))
                .setPeriodic(3_000)
                .setExtras(timer2)
                .build();
        mJobScheduler.schedule(jobInfo2);

        JobInfo jobInfo3 = new JobInfo.Builder(3, new ComponentName(getPackageName(), MyService.class.getName()))
                .setRequiresCharging(true)
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED)
                .setPeriodic(5_000)
                .setExtras(timer3)
                .build();
        mJobScheduler.schedule(jobInfo3);

    }

    @Override
    public void onDataChanged(String text) {

        mNum = mNum + 1;
        String numToString = mNum + "";
        Log.i(TAG, "onDataChanged: " + mNum);
        if (mTV1.getText().length() != 0) {
            mTV1.setText(text);
            mTV2.setText(numToString);
        }
        mTV1.setText(ServiceSingleton.getInstance().getMyString());
        mTV2.setText(numToString);

        if(mNum > 50){
            mJobScheduler.cancelAll();
        }
    }

}
