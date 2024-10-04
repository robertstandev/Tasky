import java.awt.*;

/*
 * v 0.9.0
 * -added shutdown function
 * -now alarm doesn't disable the rows that must be executed in other days (~)
 *
 * v 1.0.0
 * -modified 'execute now' code to work for open file/folder/ etc
 *
 * v 1.1.0
 * - added option to not show notification window (good when using fullscreen applications)
 * - modified the label on the notification window
 *
 * TO DO:
 * -when multiple alarms execute at the same time :
 *      stack them in the notification window (save all text messages of alarms in memory and when the user presses X to close the window , change the text if it has more alarms to show , maybe change the color of the notification window for each alarm)
 *      or show a notification window for each alarm (need to make a different class that does the monitor (taskyActionsTimer from NotificationWindowClass) and make new Notification Window class for each alarm
 */



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
    public static MainWindowClass mainWindow() { return mainWindow; }
    public static NotificationWindowClass notificationWindow() { return notificationWindow; }
}
