package controller.importexportmenu;

public enum ImportExportMenuMessages {
    INVALID_FILE("your card file is not valid to import"),
    AVAILABLE_CARD("your entered card name is available"),
    IMPORT_SUCCESSFULLY("import successfully");

    private final String message;

    ImportExportMenuMessages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
