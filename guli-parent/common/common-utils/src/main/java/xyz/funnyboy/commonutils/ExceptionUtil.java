package xyz.funnyboy.commonutils;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * 异常工具类
 *
 * @author VectorX
 * @version V1.0
 * @date 2023-12-23 21:57:10
 */
public class ExceptionUtil
{
    public static String getMessage(Exception e) {
        try (StringWriter sw = new StringWriter(); PrintWriter pw = new PrintWriter(sw)) {
            e.printStackTrace(pw);
            pw.flush();
            sw.flush();
            return sw.toString();
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return ex.getMessage();
        }
    }
}
