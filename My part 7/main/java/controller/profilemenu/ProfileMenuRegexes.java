package controller.profilemenu;

public enum ProfileMenuRegexes {
    CHANGE_NICKNAME("^profile change --nickname (\\S+)$"),
    CHANGE_PASSWORD_FIRST_PATTERN("^profile change --(?:password|P) --(?:current|C) (\\S+) --(?:new|N) (\\S+)$"),
    CHANGE_PASSWORD_SECOND_PATTERN("^profile change --(?:password|P) --(?:new|N) (\\S+) --(?:current|C) (\\S+)$"),
    CHANGE_PASSWORD_THIRD_PATTERN("^profile change --(?:current|C) (\\S+) --(?:password|P) --(?:new|N) (\\S+)$"),
    CHANGE_PASSWORD_FOURTH_PATTERN("^profile change --(?:current|C) (\\S+) --(?:new|N) (\\S+) --(?:password$|P)"),
    CHANGE_PASSWORD_FIFTH_PATTERN("^profile change --(?:new|N) (\\S+) --(?:password|P) --(?:current|C) (\\S+)$"),
    CHANGE_PASSWORD_SIXTH_PATTERN("^profile change --(?:new|N) (\\S+) --(?:current|C) (\\S+) --(?:password$|P)");

    private final String regex;

    ProfileMenuRegexes(String regex) {
        this.regex = regex;
    }

    public String getRegex() {
        return regex;
    }
}
