package controller.importexportmenu;

public enum ImportExportMenuRegexes {
    IMPORT_CARD("^import card ([^\n]+)$"),
    EXPORT_CARD("^export card ([^\n]+)$");

    private final String regex;

    ImportExportMenuRegexes(String regex) {
        this.regex = regex;
    }

    public String getRegex() {
        return regex;
    }
}
