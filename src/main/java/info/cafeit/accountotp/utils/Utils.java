package info.cafeit.accountotp.utils;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Utils {

    public static long getDiffTimes(Date d1, Date d2) {
        long diff = d2.getTime() - d1.getTime();
        return TimeUnit.MILLISECONDS.toSeconds(diff);
    }
}
