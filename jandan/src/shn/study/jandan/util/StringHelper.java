package shn.study.jandan.util;

/**
 * Created by shn7798 on 14-9-9.
 */
public class StringHelper {
    public static class StringCursor {
        public int value;
        public StringCursor(int value){
            this.value = value;
        }
    }
    public static String getSubString(final String source,
                               final String flagStart,
                               final String flagEnd,
                               StringCursor cursor,
                               Boolean isKeepFlag){
        int posStart;
        int posEnd;


        if(cursor == null)
            cursor = new StringCursor(0);

        posStart = source.indexOf(flagStart, cursor.value);
        if(posStart < 0)
            return "";

        // 如果结束字符是空，那么匹配到字符串最后
        if(flagEnd.equals("")){
            posEnd = source.length()-1;
        } else {
            posEnd = source.indexOf(flagEnd, posStart + flagStart.length());
            if (posEnd < 0)
                return "";
        }
        cursor.value = posEnd + flagEnd.length();

        if(isKeepFlag){
            posEnd += flagEnd.length();
        } else {
            posStart += flagStart.length();
        }

        return source.substring(posStart,posEnd);
    }
    public static String getSubString(final String source,
                                      final String flagStart,
                                      final String flagEnd,
                                      StringCursor cursor){
        return getSubString(source,flagStart,flagEnd,cursor,false);
    }
}
