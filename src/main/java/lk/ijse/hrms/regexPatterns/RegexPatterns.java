package lk.ijse.hrms.regexPatterns;

import java.util.regex.Pattern;

public class RegexPatterns {
    private static final Pattern empIdPattern = Pattern.compile("E\\d{3}$");
    private static final Pattern clientIdPattern = Pattern.compile("C\\d{3}$");
    private static final Pattern legIdPattern = Pattern.compile("L\\d{3}$");
    private static final Pattern projectIdPattern = Pattern.compile("P\\d{3}$");
    private static final Pattern emailPattern = Pattern.compile("^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
            + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$");
    private static final Pattern mobilePattern = Pattern.compile("^(?:0|94|\\+94|0094)?(?:(11|21|23|24|25|26|27|31|32|33|34|35|36|37|38|41|45|47|51|52|54|55|57|63|65|66|67|81|91)(0|2|3|4|5|7|9)|7(0|1|2|4|5|6|7|8)\\d)\\d{6}$");
    private static final Pattern oldIDPattern = Pattern.compile("^[0-9]{9}[vVxX]$");
    private static final Pattern newIDPattern = Pattern.compile("^([0-9]{9}[x|X|v|V]|[0-9]{12})$");
    private static final Pattern doublePattern = Pattern.compile("^[0-9]+\\.?[0-9]*$");
    private static final Pattern intPattern = Pattern.compile("^[1-9][0-9]?$|^100$");


    public static Pattern getEmpIdPattern() {
        return empIdPattern;
    }

    public static Pattern getClientIdPattern() {
        return clientIdPattern;
    }

    public static Pattern getLegIdPattern() {
        return legIdPattern;
    }

    public static Pattern getProjectIdPattern() {
        return projectIdPattern;
    }

    public static Pattern getEmailPattern() {
        return emailPattern;
    }

    public static Pattern getMobilePattern() {
        return mobilePattern;
    }

    public static Pattern getOldIDPattern() {
        return oldIDPattern;
    }

    public static Pattern getDoublePattern() {
        return doublePattern;
    }

    public static Pattern getIntPattern() {
        return intPattern;
    }

    public static Pattern getNewIDPattern() {
        return newIDPattern;
    }
}

