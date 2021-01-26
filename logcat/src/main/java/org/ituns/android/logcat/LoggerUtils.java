package org.ituns.android.logcat;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;

import static org.ituns.android.logcat.LoggerClient.TAG;

public class LoggerUtils {

    public static String buildCacheFilePath(Context context) {
        return cacheDirectory(context) +
                File.separator + "ituns" +
                File.separator + "logcat" +
                File.separator + "logcat.log";
    }

    private static String cacheDirectory(Context context) {
        File cacheDir = null;
        if(Environment.MEDIA_MOUNTED.endsWith(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            cacheDir = context.getExternalCacheDir();
        }
        if(cacheDir == null) {
            cacheDir = context.getCacheDir();
        }
        return cacheDir.getAbsolutePath();
    }

    public static void clearLogerFile(String filepath) {
        try {
            new File(filepath).delete();
        } catch (Exception e) {
            Log.i(TAG, "LogerUtils.clearLogerFile", e);
        }
    }

    public static String buildTag(String root, String child) {
        StringBuilder builder = new StringBuilder();
        if(TextUtils.isEmpty(root) || TextUtils.isEmpty(child)) {
           root = root == null ? "" : root;
           child = child == null ? "" : child;
           builder.append(root).append(child);
        } else {
            builder.append(root).append("#").append(child);
        }
        if(builder.length() > 23) {
            return builder.substring(0, 23);
        }
        return builder.toString();
    }

    public static String buildMsg(String msg, Throwable throwable, int stackDepth, StackTraceElement[] elements) {
        //build call stack infomation
        StringBuilder builder = new StringBuilder();
        if(elements != null && stackDepth < elements.length) {
            StackTraceElement element = elements[stackDepth];
            builder.append("(");
            builder.append(element.getFileName());
            builder.append(":");
            builder.append(element.getLineNumber());
            builder.append(")").append("#");
            builder.append(element.getMethodName());
            builder.append("=>");
        }

        if(!TextUtils.isEmpty(msg)) {
            builder.append(msg);
            builder.append('\n');
        }

        if(throwable != null) {
            builder.append(throwableToString(throwable));
        }

        return builder.toString();
    }

    private static String throwableToString(Throwable throwable) {
        if (throwable == null) {
            return "";
        }

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        throwable.printStackTrace(pw);
        return sw.toString();
    }
}
