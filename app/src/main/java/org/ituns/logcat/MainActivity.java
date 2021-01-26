package org.ituns.logcat;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import org.ituns.android.logcat.LoggerClient;
import org.ituns.android.logcat.LoggerConfig;
import org.ituns.android.logcat.Priority;

public class MainActivity extends AppCompatActivity {
    private LoggerClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LoggerConfig config = new LoggerConfig.Builder(this)
                .tag("Main")
                .debug(true)
                .priority(Priority.VERBOSE)
                .build();
        client = new LoggerClient(config);
        client.print(Priority.ERROR, "Create", "create", null);
    }

    @Override
    protected void onStart() {
        super.onStart();
        client.print(Priority.ERROR, "Create", "start", null);
    }

    @Override
    protected void onResume() {
        super.onResume();
        client.print(Priority.ERROR, "Create", "resume", null);
    }

    @Override
    protected void onPause() {
        super.onPause();
        client.print(Priority.ERROR, "Create", "pause", null);
    }

    @Override
    protected void onStop() {
        super.onStop();
        client.print(Priority.ERROR, "Create", "stop", null);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        client.print(Priority.ERROR, "Create", "destroy", null);
        client.release();
    }
}
