package xyz.johanmans10.simplecommandspy.utilities;

import java.util.regex.Pattern;

public class ColorUtils {

    public static String format(String textToTranslate) {
        char[] b = textToTranslate.toCharArray();
        for (int i = 0; i < b.length - 1; i++) {
            if (b[i] == '&' && "0123456789AaBbCcDdEeFfKkLlMmNnOoRrXx".indexOf(b[i + 1]) > -1) {
                b[i] = '§';
                b[i + 1] = Character.toLowerCase(b[i + 1]);
            }
        }
        return new String(b);
    }

    public static String stripColor(final String input) {
        if (input == null) {
            return null;
        }
        String result = Pattern.compile("(?i)" + '&' + "[0-9a-fk-orxA-FK-ORX]").matcher(input).replaceAll("");
        return Pattern.compile("(?i)" + '§' + "[0-9a-fk-orxA-FK-ORX]").matcher(result).replaceAll("");
    }
}
