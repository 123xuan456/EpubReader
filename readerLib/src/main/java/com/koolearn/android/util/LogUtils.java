package com.koolearn.android.util;

import android.util.Log;

/**
 * ******************************************
 * ******************************************
 */
public class LogUtils {
    static String className;//类名
    static String methodName;//方法名
    static int lineNumber;//行数

    static boolean flag1 = true;
    static boolean flag2 = false;
    static boolean flag3 = false;
    static boolean flag5 = false;
    static boolean flag6 = false;
    static boolean flag8 = false;
    static boolean flag9 = false;
    static boolean flag10 = false;
    static boolean flag11 = false;
    static boolean flag12 = false;
    static boolean flag13 = false;
    static boolean flag15 = false;
    static boolean flag16 = false;
    static boolean flag18 = false;
    static boolean flag20 = false;
    static boolean flag21 = false;
    static boolean flag24 = false;
    private static String createLog( String log ) {
        StringBuffer buffer = new StringBuffer();
        buffer.append(methodName);
        buffer.append("(").append(className).append(":").append(lineNumber).append(")");
        buffer.append("="+log);
        return buffer.toString();
    }

    private static void getMethodNames(StackTraceElement[] sElements){
        className = sElements[1].getFileName();
        methodName = sElements[1].getMethodName();
        lineNumber = sElements[1].getLineNumber();
    }
    public static void i(String message) {
        getMethodNames(new Throwable().getStackTrace());
        Log.i(className, createLog(message));
    }
    public static void i(int message){
        if (flag1)
            return;

        getMethodNames(new Throwable().getStackTrace());
        Log.i(className, createLog(message+""));
    }
    public static void d(String message) {
        getMethodNames(new Throwable().getStackTrace());
        Log.d(className, createLog(message));
    }
    public static void e(String message) {
        getMethodNames(new Throwable().getStackTrace());
        Log.e(className, createLog(message));
    }
    public static void i1(String info) {
        if (flag1) {
            final StackTraceElement[] stack = new Throwable().getStackTrace();
            final int i = 1;
//            for (int id = 0; id < stack.length; id++) {
            final StackTraceElement ste = stack[i];
            android.util.Log.i("yul2_log_", String.format("[%s][%s]%s[%s]",
                    ste.getFileName(), ste.getMethodName(), ste.getLineNumber(), info));
//            }
        }
    }

    public static void i2(String info) {
        if (flag2) {
            final StackTraceElement[] stack = new Throwable().getStackTrace();
            final int i = 1;
//            for (int id = 0; id < stack.length; id++) {
            final StackTraceElement ste = stack[i];
            android.util.Log.i("yul2_log_", String.format("[%s][%s]%s[%s]",
                    ste.getFileName(), ste.getMethodName(), ste.getLineNumber(), info));
//            }
        }
    }

    public static void i3(String info) {
        if (flag3) {
            final StackTraceElement[] stack = new Throwable().getStackTrace();
            final int i = 1;
//            for (int id = 0; id < stack.length; id++) {
            final StackTraceElement ste = stack[i];
            android.util.Log.i("yul2_log_", String.format("[%s][%s]%s[%s]",
                    ste.getFileName(), ste.getMethodName(), ste.getLineNumber(), info));
//            }
        }
    }

    public static void i5(String info) {
        if (flag5) {
            final StackTraceElement[] stack = new Throwable().getStackTrace();
            final int i = 1;
//            for (int id = 0; id < stack.length; id++) {
            final StackTraceElement ste = stack[i];
            android.util.Log.i("yul2_log_", String.format("[%s][%s]%s[%s]",
                    ste.getFileName(), ste.getMethodName(), ste.getLineNumber(), info));
//            }
        }
    }

    public static void i6(String info) {
        if (flag6) {
            final StackTraceElement[] stack = new Throwable().getStackTrace();
            final int i = 1;
//            for (int id = 0; id < stack.length; id++) {
            final StackTraceElement ste = stack[i];
            android.util.Log.i("yul2_log_", String.format("[%s][%s]%s[%s]",
                    ste.getFileName(), ste.getMethodName(), ste.getLineNumber(), info));
//            }
        }
    }

    public static void i8(String info) {
        if (flag8) {
            final StackTraceElement[] stack = new Throwable().getStackTrace();
            final int i = 1;
//            for (int id = 0; id < stack.length; id++) {
            final StackTraceElement ste = stack[i];
            android.util.Log.i("yul2_log_", String.format("[%s][%s]%s[%s]",
                    ste.getFileName(), ste.getMethodName(), ste.getLineNumber(), info));
//            }
        }
    }

    public static void i9(String info) {
        if (flag9) {
            final StackTraceElement[] stack = new Throwable().getStackTrace();
            final int i = 1;
            final StackTraceElement ste = stack[i];
            android.util.Log.i("yul2_log_", String.format("[%s][%s]%s[%s]",
                    ste.getFileName(), ste.getMethodName(), ste.getLineNumber(), info));
        }
    }


    public static void i10(String info) {
        if (flag10) {
            final StackTraceElement[] stack = new Throwable().getStackTrace();
            final int i = 1;
//            for (int id = 0; id < stack.length; id++) {
            final StackTraceElement ste = stack[i];
            android.util.Log.i("yul2_log_", String.format("[%s][%s]%s[%s]",
                    ste.getFileName(), ste.getMethodName(), ste.getLineNumber(), info));
//            }
        }
    }

    public static void i11(String info) {
        if (flag11) {
            final StackTraceElement[] stack = new Throwable().getStackTrace();
            final int i = 1;
//            for (int id = 0; id < stack.length; id++) {
            final StackTraceElement ste = stack[i];
            android.util.Log.i("yul2_log_", String.format("[%s][%s]%s[%s]",
                    ste.getFileName(), ste.getMethodName(), ste.getLineNumber(), info));
//            }
        }
    }

    public static void i12(String info) {
        if (flag12) {
            final StackTraceElement[] stack = new Throwable().getStackTrace();
            final int i = 1;
//            for (int id = 0; id < stack.length; id++) {
            final StackTraceElement ste = stack[i];
            android.util.Log.i("yul2_log_", String.format("[%s][%s]%s[%s]",
                    ste.getFileName(), ste.getMethodName(), ste.getLineNumber(), info));
//            }
        }
    }

    public static void i13(String info) {
        if (flag13) {
            final StackTraceElement[] stack = new Throwable().getStackTrace();
            final int i = 1;
//            for (int id = 0; id < stack.length; id++) {
            final StackTraceElement ste = stack[i];
            android.util.Log.i("yul2_log_", String.format("[%s][%s]%s[%s]",
                    ste.getFileName(), ste.getMethodName(), ste.getLineNumber(), info));
//            }
        }
    }

    public static void i15(String info) {
        if (flag15) {
            final StackTraceElement[] stack = new Throwable().getStackTrace();
            final int i = 1;
//            for (int id = 0; id < stack.length; id++) {
            final StackTraceElement ste = stack[i];
            android.util.Log.i("yul2_log_", String.format("[%s][%s]%s[%s]",
                    ste.getFileName(), ste.getMethodName(), ste.getLineNumber(), info));
//            }
        }
    }

    public static void i16(String info) {
        if (flag16) {
            final StackTraceElement[] stack = new Throwable().getStackTrace();
            final int i = 1;
//            for (int id = 0; id < stack.length; id++) {
            final StackTraceElement ste = stack[i];
            android.util.Log.i("yul2_log_", String.format("[%s][%s]%s[%s]",
                    ste.getFileName(), ste.getMethodName(), ste.getLineNumber(), info));
//            }
        }
    }

    public static void i18(String info) {
        if (flag18) {
            final StackTraceElement[] stack = new Throwable().getStackTrace();
            final int i = 1;
//            for (int id = 0; id < stack.length; id++) {
            final StackTraceElement ste = stack[i];
            android.util.Log.i("yul2_log_", String.format("[%s][%s]%s[%s]",
                    ste.getFileName(), ste.getMethodName(), ste.getLineNumber(), info));
//            }
        }
    }

    public static void i20(String info) {
        if (flag20) {
            final StackTraceElement[] stack = new Throwable().getStackTrace();
            final int i = 1;
//            for (int id = 0; id < stack.length; id++) {
            final StackTraceElement ste = stack[i];
            android.util.Log.i("yul2_log_", String.format("[%s][%s]%s[%s]",
                    ste.getFileName(), ste.getMethodName(), ste.getLineNumber(), info));
//            }
        }
    }

    public static void i21(String info) {
        if (flag21) {
            final StackTraceElement[] stack = new Throwable().getStackTrace();
            final int i = 1;
//            for (int id = 0; id < stack.length; id++) {
            final StackTraceElement ste = stack[i];
            android.util.Log.i("yul2_log_", String.format("[%s][%s]%s[%s]",
                    ste.getFileName(), ste.getMethodName(), ste.getLineNumber(), info));
//            }
        }
    }

    public static void i24(String info) {
        if (flag24) {
            final StackTraceElement[] stack = new Throwable().getStackTrace();
            final int i = 1;
//            for (int id = 0; id < stack.length; id++) {
            final StackTraceElement ste = stack[i];
            android.util.Log.i("yul2_log_", String.format("[%s][%s]%s[%s]",
                    ste.getFileName(), ste.getMethodName(), ste.getLineNumber(), info));
//            }
        }
    }
}
