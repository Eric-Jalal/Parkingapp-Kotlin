package helper

import java.util.ArrayList
import java.util.Arrays

object StringToArray {

    fun convertStringToArrayCommaSeparated(notSeparatedString: String): List<String> {
        val commaSeparatedArr = notSeparatedString.split("\\s*,\\s*".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        return ArrayList(Arrays.asList(*commaSeparatedArr))
    }

    fun convertStringToArraySpaceSeparated(commaSeparatedString: String): List<String> {
        val spaceSeparatedArr = commaSeparatedString.split("\\s+".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        return ArrayList(Arrays.asList(*spaceSeparatedArr))
    }
}
