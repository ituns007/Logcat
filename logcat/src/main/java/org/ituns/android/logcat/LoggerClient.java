package org.ituns.android.logcat;

import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;

import org.ituns.android.logcat.logger.BaseLogger;
import org.ituns.android.logcat.logger.ConsoleLogger;
import org.ituns.android.logcat.logger.StorageLogger;

public class LoggerClient {
    public static final String TAG = "Logcat";

    private String mTag;
    private Handler mHandler;
    private Priority mPriority;
    private BaseLogger mLogger;

    public LoggerClient(LoggerConfig config) {
        mTag = config.tag();
        mPriority = config.priority();
        mHandler = initHandler();
        if(config.debug()) {
            mLogger = new ConsoleLogger();
        } else {
            mLogger = new StorageLogger(config.context());
        }
    }

    private Handler initHandler() {
        HandlerThread thread = new HandlerThread(TAG);
        thread.start();
        return new Handler(thread.getLooper());
    }

    public final void print(Priority priority, String tag, String msg, Throwable throwable) {
        log(priority, tag, msg, throwable, 1);
    }

    public final void print(Priority priority, String tag, String msg, Throwable throwable, int stackDepth) {
        stackDepth = stackDepth < 0 ? 1 : stackDepth + 1;
        log(priority, tag, msg, throwable, stackDepth);
    }

    private void log(Priority priority, String tag, String msg, Throwable throwable, int stackDepth) {
        if(priority == Priority.NONE || priority.value() < mPriority.value()) {
            Log.i(TAG, "priority is invalid.");
            return;
        }

        Handler handler = mHandler;
        if(handler == null) {
            Log.i(TAG, "handler is null.");
            return;
        }

        final BaseLogger logger = mLogger;
        if(logger == null) {
            Log.i(TAG, "logger is null.");
            return;
        }

        final int depth = stackDepth < 0 ? 3 : stackDepth + 3;
        StackTraceElement[] elements = Thread.currentThread().getStackTrace();
        handler.post(() -> {
            String realTag = LoggerUtils.buildTag(mTag, tag);
            String realMsg = LoggerUtils.buildMsg(msg, throwable, depth, elements);
            logger.log(priority, realTag, realMsg);
        });
    }

    public void release() {
        mTag = null;
        mLogger = null;
        mPriority = Priority.NONE;
        Handler handler = mHandler;
        if(handler != null) {
            handler.getLooper().quit();
            mHandler = null;
        }
    }
}
