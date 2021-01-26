package org.ituns.android.logcat.logger;

import android.util.Log;

import org.ituns.android.logcat.Priority;

import java.io.ByteArrayInputStream;

import static org.ituns.android.logcat.LoggerClient.TAG;

public class ConsoleLogger extends BaseLogger {
    private static final int LOG_BUFFER_LENGTH = 4000;

    @Override
    public void log(Priority priority, String tag, String msg) {
        if(tag == null || msg == null) {
            Log.e(TAG, "tag or msg is null.");
            return;
        }

        try {
            printLog(priority.value(), tag, msg);
        } catch (Exception e) {
            Log.e(TAG, "ConsoleLoger.log", e);
        }
    }

    private void printLog(int priority, String tag, String msg) throws Exception {
        int length = 0;
        byte[] bytes = new byte[LOG_BUFFER_LENGTH];
        ByteArrayInputStream bis = new ByteArrayInputStream(msg.getBytes());
        while ((length = bis.read(bytes)) != -1) {
            String splitMsg = new String(bytes, 0, length);
            Log.println(priority, tag, splitMsg);
        }
        bis.close();
    }
}
