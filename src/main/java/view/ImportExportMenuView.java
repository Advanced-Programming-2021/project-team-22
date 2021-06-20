package view;

import controller.Utils;
import controller.importexportmenu.ImportExportMenuController;
import controller.importexportmenu.ImportExportMenuMessages;

public class ImportExportMenuView {
    public void ImportExportMenuView() {
        while (true) {
            String command = Utils.getScanner().nextLine().trim();
            ImportExportMenuMessages result = ImportExportMenuController.findCommand(command);

            System.out.print(result.getMessage());

            if (result.equals(ImportExportMenuMessages.EXIT_IMPORT_EXPORT_MENU)) break;
        }
    }
}
