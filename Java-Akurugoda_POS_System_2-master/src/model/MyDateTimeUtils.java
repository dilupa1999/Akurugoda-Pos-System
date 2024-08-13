
package model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MyDateTimeUtils {
    public static String getCurrentDateTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        return formatter.format(date);
    }
}
