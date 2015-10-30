package com.example.customui.util;

import android.util.Log;

/**
 * Êó•Âøó ‰ø°ÊÅØ
 *
 * @author caiyuhao
 *         2013Âπ?1Êú?1Êó?
 */
public class Logger {
	private String oldTag = "group_purchase+++++++++";
    private String tag = "group_purchase+++++++++";
    public static int logLevel = Log.INFO;

    private static boolean logFlag = true;

    private static Logger logger = new Logger();

    public static Logger getLogger() {
        return logger;
    }

    private Logger() {
    }

    public String getFunctionName() {
        StackTraceElement[] sts = Thread.currentThread().getStackTrace();

        if (sts == null) {
            return null;
        }

        for (StackTraceElement st : sts) {
            if (st.isNativeMethod()) {
                continue;
            }

            if (st.getClassName().equals(Thread.class.getName())) {
                continue;
            }

            if (st.getClassName().equals(this.getClass().getName())) {
                continue;
            }

            return "[ " + Thread.currentThread().getName() + ": "
                    + st.getFileName() + ":" + st.getLineNumber() + " ]";
        }

        return null;
    }

    public void i(Object str) {
        if (!logFlag)
            return;
        if (logLevel <= Log.INFO) {
            String name = getFunctionName();
            if (name != null) {
                Log.i(tag, name + " - " + str);
            } else {
                Log.i(tag, str.toString());
            }
            tag = oldTag;
        }
    }

    public void v(Object str) {
        if (!logFlag)
            return;
        if (logLevel <= Log.VERBOSE) {
            String name = getFunctionName();
            if (name != null) {
                Log.v(tag, name + " - " + str);
            } else {
                Log.v(tag, str.toString());
            }
            tag = oldTag;
        }
    }

    public void w(Object str) {
        if (!logFlag)
            return;
        if (logLevel <= Log.WARN) {
            String name = getFunctionName();

            if (name != null) {
                Log.w(tag, name + " - " + str);
            } else {
                Log.w(tag, str.toString());
            }
            tag = oldTag;
        }
    }

    public void e(Object str) {
        if (!logFlag)
            return;
        if (logLevel <= Log.ERROR) {

            String name = getFunctionName();
            if (name != null) {
                Log.e(tag, name + " - " + str);
            } else {
                Log.e(tag, str.toString());
            }
            tag = oldTag;
        }
    }

    public void e(Exception ex) {
        if (!logFlag)
            return;
        if (logLevel <= Log.ERROR) {
            Log.e(tag, "error", ex);
            tag = oldTag;
        }
    }

    public void d(Object str) {
        if (!logFlag)
            return;
        if (logLevel <= Log.DEBUG) {
            String name = getFunctionName();
            if (name != null) {
                Log.d(tag, name + " - " + str);
            } else {
                Log.d(tag, str.toString());
            }
            tag = oldTag;
        }
    }

    public void i(String tag, Object str) {
        this.tag = tag;
        i(str);
    }

    public void v(String tag, Object str) {
        this.tag = tag;
        v(str);
    }

    public void w(String tag, Object str) {
        this.tag = tag;
        w(str);
    }

    public void e(String tag, Object str) {
        this.tag = tag;
        e(str);
    }

    public void d(String tag, Object str) {
        this.tag = tag;
        d(str);
    }
}
