package org.ituns.android.logcat.logger;

import org.ituns.android.logcat.Priority;

public abstract class BaseLogger {

    public abstract void log(Priority priority, String tag, String msg);
}
