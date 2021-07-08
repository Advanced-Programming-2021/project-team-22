package controller.importexportmenu;

public enum ImportExportMenuMessages {
    INVALID_NAVIGATION("menu navigation is not possible\n"),
    EXIT_IMPORT_EXPORT_MENU(""),
    SHOW_MENU("Import/Export Menu\n"),
    INVALID_COMMAND("invalid command\n"),
    UNAVAILABLE_FILE("there isn't any file with your entered showSelectedCard name\n"),
    INVALID_FILE("your showSelectedCard file is not valid to import\n"),
    AVAILABLE_CARD("your entered showSelectedCard name is available\n"),
    UNAVAILABLE_CARD("your entered showSelectedCard name is not available\n"),
    EMPTY("");

    private final String message;

    ImportExportMenuMessages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
