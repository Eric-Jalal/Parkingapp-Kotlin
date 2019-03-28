package helper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StringToArray {

    public static List<String> convertStringToArrayCommaSeparated(String notSeparatedString) {
        String[] commaSeparatedArr = notSeparatedString.split("\\s*,\\s*");
        return new ArrayList<String>(Arrays.asList(commaSeparatedArr));
    }

    public static List<String> convertStringToArraySpaceSeparated(String commaSeparatedString) {
        String[] spaceSeparatedArr = commaSeparatedString.split("\\s+");
        return new ArrayList<String>(Arrays.asList(spaceSeparatedArr));
    }
}
