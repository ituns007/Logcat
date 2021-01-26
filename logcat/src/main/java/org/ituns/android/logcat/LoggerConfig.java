package org.ituns.android.logcat;

import android.content.Context;

public class LoggerConfig {
    private Context context;
    private String tag;
    private boolean debug;
    private Priority priority;

    private LoggerConfig(Builder builder) {
        this.context = builder.context;
        this.tag = builder.tag;
        this.debug = builder.debug;
        this.priority = builder.priority;
    }

    public Context context() {
        return context;
    }

    public String tag() {
        return tag;
    }

    public boolean debug() {
        return debug;
    }

    public Priority priority() {
        return priority;
    }

    public static class Builder {
        private Context context;
        private String tag;
        private boolean debug;
        private Priority priority;

        public Builder(Context context) {
            this.context = context;
            this.tag = tag;
            this.debug = false;
            this.priority = Priority.NONE;
        }

        public Builder tag(String tag) {
            this.tag = tag;
            return this;
        }

        public Builder debug(boolean debug) {
            this.debug = debug;
            return this;
        }

        public Builder priority(Priority priority) {
            this.priority = priority;
            return this;
        }

        public LoggerConfig build() {
            return new LoggerConfig(this);
        }
    }
}
