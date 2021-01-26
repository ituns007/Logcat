package org.ituns.android.logcat.logger;

import android.content.Context;
import android.os.Process;
import android.util.Log;

import org.ituns.android.logcat.LoggerUtils;
import org.ituns.android.logcat.Priority;

import java.io.File;
import java.io.FileWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static org.ituns.android.logcat.LoggerClient.TAG;

public class StorageLogger extends BaseLogger {
    private static final String FORMAT = "MM-dd HH:mm:ss.SSS";

    private final String mFilePath;
    private final DateFormat mDateFormat;

    public StorageLogger(Context context) {
        mFilePath = LoggerUtils.buildCacheFilePath(context);
        mDateFormat = new SimpleDateFormat(FORMAT, Locale.getDefault());
        LoggerUtils.clearLogerFile(mFilePath);
    }

    @Override
    public void log(Priority priority, String tag, String msg) {
        String content = buildLogContent(priority.value(), tag, msg);
        writeLogToFile(mFilePath, content);
    }

    private String buildLogContent(int priority, String tag, String msg) {
        StringBuilder builder = new StringBuilder();
        builder.append(mDateFormat.format(new Date()));
        builder.append(" ");
        builder.append(Process.myPid());
        builder.append("-");
        builder.append(Process.myTid());
        builder.append(" ");
        builder.append(parsePriority(priority));
        builder.append("/");
        builder.append(tag);
        builder.append(": ");
        builder.append(msg);
        return builder.toString();
    }

    private String parsePriority(int priority) {
        if(priority == Log.VERBOSE) {
            return "V";
        } else if(priority == Log.DEBUG) {
            return "D";
        } else if(priority == Log.INFO) {
            return "I";
        } else if(priority == Log.WARN) {
            return "W";
        } else if(priority == Log.ERROR) {
            return "E";
        } else if(priority == Log.ASSERT) {
            return "A";
        } else {
            return "";
        }
    }

    private void writeLogToFile(String filepath, String content) {
        File file = new File(filepath);
        if (!file.exists()) {
            file.getParentFile().mkdirs();
        }
        writeLogToFile(file, content);
    }

    private void writeLogToFile(File file, String content) {
        try(FileWriter writer = new FileWriter(file, true)) {
            writer.write(content);
        } catch (Throwable e) {
            Log.i(TAG, "write log to file exception:", e);
        }
    }
}
