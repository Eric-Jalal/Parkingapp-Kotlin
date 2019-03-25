package helper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StringToArray {

    public static List<String> convertStringToArray(String commaSeparatedStr) {
        String[] commaSeparatedArr = commaSeparatedStr.split("\\s*,\\s*");
        return new ArrayList<String>(Arrays.asList(commaSeparatedArr));
    }
}
