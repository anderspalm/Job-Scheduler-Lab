package com.example.ander.merpderp;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.os.AsyncTask;
import android.util.Log;

/**
 * Created by ander on 8/14/2016.
 */
public class MyService extends JobService {

    private AsyncTask<Void, Void, String> mTask;
    private static final String TAG = "MyService";

    @Override
    public boolean onStartJob(final JobParameters jobParameters) {

        final String timer = jobParameters.getExtras().getString("timer");

        Log.i(TAG, "onStartJob: " + timer);
        
        mTask = new AsyncTask<Void, Void, String>() {

            @Override
            protected String doInBackground(Void... voids) {

                String text = "";

                switch (timer) {
                    default:
                    case "1":
                        text = "fizz";
                        break;
                    case "2":
                        text = "buzz";
                        break;
                    case "3":
                        text = "fizzbuzz";
                        break;
                }

                return text;
            }

            @Override
            protected void onPostExecute(String string) {
                super.onPostExecute(string);
                ServiceSingleton.getInstance().updateText(string);
                jobFinished(jobParameters, false);
            }
        };
        mTask.execute();
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {

        if (mTask.getStatus().equals(AsyncTask.Status.RUNNING)) {
            mTask.cancel(false);
        }
        return false;
    }
}



//
//                if (mCounter != null) {
//                    switch (timer) {
//                        default:
//                        case "1":
//                            mNewText = "inactive";
//                            break;
//                        case "2":
//                            mNewText = "active";
//                            break;
//                    }
//                } else {
//                    switch (timer) {
//                        default:
//                        case "1":
//                            mNewText = "inactive" + mCounter;
//                            break;
//                        case "2":
//                            mNewText = "active" + mCounter;
//                            break;
//                    }
//                }