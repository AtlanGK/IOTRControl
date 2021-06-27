package com.autohome.iotrcontrol.util;
import android.content.Context;
import android.content.res.Resources;


public class ScreenUtil {
    public ScreenUtil() {
    }

    public static float dpToPx(Context context, float dp) {
        return context == null ? -1.0F : dp * Resources.getSystem().getDisplayMetrics().density;
    }

    public static int dpToPxInt(Context context, float dp) {
        return (int)(dpToPx(context, dp) + 0.5F);
    }

    public static int dpToPxIntRound(Context context, float dp) {
        return Math.round(dpToPx(context, dp) + 0.5F);
    }

    public static int sp2px(Context context, float spValue) {
        float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int)(spValue * fontScale + 0.5F);
    }

    public static float pxToDp(Context context, float px) {
        return context != null && px != 0.0F ? px / Resources.getSystem().getDisplayMetrics().density : -1.0F;
    }

    public static int pxToDpInt(Context context, float px) {
        return (int)(pxToDp(context, px) + 0.5F);
    }

    public static int pxToDpIntRound(Context context, float px) {
        return Math.round(pxToDp(context, px) + 0.5F);
    }
}
