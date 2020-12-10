// 
// Decompiled by Procyon v0.5.36
// 

package club.novola.zori.util;

import java.util.Random;
import java.util.Arrays;

public class StringUtils
{
    public static boolean isNumber(final String string) {
        try {
            Integer.parseInt(string);
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }
    
    public static String toCompleteString(final String[] args, final int start) {
        if (args.length <= start) {
            return "";
        }
        return String.join(" ", (CharSequence[])Arrays.copyOfRange(args, start, args.length));
    }
    
    public static String replace(final String string, final String searchChars, String replaceChars) {
        if (string.isEmpty() || searchChars.isEmpty() || searchChars.equals(replaceChars)) {
            return string;
        }
        if (replaceChars == null) {
            replaceChars = "";
        }
        final int stringLength = string.length();
        final int searchCharsLength = searchChars.length();
        final StringBuilder stringBuilder = new StringBuilder(string);
        int i = 0;
        while (i < stringLength) {
            final int start = stringBuilder.indexOf(searchChars, i);
            if (start == -1) {
                if (i == 0) {
                    return string;
                }
                return stringBuilder.toString();
            }
            else {
                stringBuilder.replace(start, start + searchCharsLength, replaceChars);
                ++i;
            }
        }
        return stringBuilder.toString();
    }
    
    public static String randomString(final int length, final boolean letters, final boolean numbers, final boolean uppercases) {
        String SALTCHARS = "";
        if (letters && uppercases) {
            SALTCHARS += "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        }
        if (letters) {
            SALTCHARS += "abcdefghijklmnopqrstuvwxyz";
        }
        if (numbers) {
            SALTCHARS += "1234567890";
        }
        final StringBuilder salt = new StringBuilder();
        final Random rnd = new Random();
        while (salt.length() < length) {
            final int index = (int)(rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        final String saltStr = salt.toString();
        return saltStr;
    }
}
