import java.awt.*;

/*
 * v 0.9.0
 * -added shutdown function
 * -now alarm doesn't disable the rows that must be executed in other days (~)
 *
 * v 1.0.0
 * -modified execute now code to work for open file/folder/ etc
 */

//modific locul de salvare de la user.home la user.dir + /Data/ si sa creeze folderul daca nu exista


public class Tasky
{
    public static void main(String[] args)
    {
        Dimension getScreenSize = Toolkit.getDefaultToolkit().getScreenSize();

        saveLoadClass = new SaveLoadClass();
        guiBuilderClass = new GUIBuilderClass();

        mainWindow = new MainWindowClass(guiBuilderClass, saveLoadClass, getScreenSize);
        notificationWindow = new NotificationWindowClass(guiBuilderClass, getScreenSize);
    }

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=Classes=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    private static SaveLoadClass saveLoadClass;
    private static GUIBuilderClass guiBuilderClass;
    private static MainWindowClass mainWindow;
    private static NotificationWindowClass notificationWindow;

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=GETTERS=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    public static SaveLoadClass saveLoadClass() { return saveLoadClass; }
    public static GUIBuilderClass guiBuilderClass() { return guiBuilderClass; }
    public static MainWindowClass mainWindow() {
        return mainWindow;
    }
    public static NotificationWindowClass notificationWindow() {
        return notificationWindow;
    }
}