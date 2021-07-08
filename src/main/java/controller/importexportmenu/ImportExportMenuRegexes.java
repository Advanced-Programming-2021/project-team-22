package controller.importexportmenu;

public enum ImportExportMenuRegexes {
    IMPORT_CARD("^import showSelectedCard ([^\n]+)$"),
    EXPORT_CARD("^export showSelectedCard ([^\n]+)$");

    private final String regex;

    ImportExportMenuRegexes(String regex) {
        this.regex = regex;
    }

    public String getRegex() {
        return regex;
    }
}
