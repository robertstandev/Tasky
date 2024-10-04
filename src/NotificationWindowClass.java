import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class NotificationWindowClass extends Tasky
{
    public NotificationWindowClass(GUIBuilderClass guiBuilderClass, Dimension screenSize)
    {
        guiBuilderClass.setWindowFrame(notificationWindowFrame,"Tasky - Notification Window",true,new Dimension(300,200),new Point(screenSize.width - 300, screenSize.height - 240),new Color(255,255,255,255),1f,false,true,true,true);
        notificationWindowFrame.getContentPane().setLayout(null);

        JLabel notificationWindowLabel = new JLabel();
        guiBuilderClass.setLabel(notificationWindowLabel, "Tasky - Notification", new Font("Arial",Font.BOLD,20), new Color(255,0,0,255), new Dimension(250,20), new Point(40,5));
        notificationWindowFrame.getContentPane().add(notificationWindowLabel);

        guiBuilderClass.setTextBox(notificationWindowMessageTextBox, "Message",new Color(0,0,0,255),new Color(255,255,255,255),false, new Dimension(280,140), new Point(10,10), "Message");
        guiBuilderClass.addScrollFunction(notificationWindowFrame,notificationWindowMessageTextBox); //also adds text area to frame

        JButton notificationWindowCloseButton = new JButton();
        guiBuilderClass.setButton(notificationWindowCloseButton, "Close", new Dimension(80,20), new Point(130,175), new Color(255,0,0,255), "Press Button To Close Window");
        notificationWindowCloseButton.addActionListener(arg0 -> notificationWindowFrame.setVisible(false));
        notificationWindowFrame.getContentPane().add(notificationWindowCloseButton);

        taskyActionsTimer(15000);
    }


    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=Data=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    private static final JFrame notificationWindowFrame = new JFrame();
    private final JTextArea notificationWindowMessageTextBox = new JTextArea();


    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=Notification Timer=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    private void taskyActionsTimer(int timerInterval)
    {
        java.util.Timer timer = new Timer();
        TimerTask tt = new TimerTask()
        {
            @Override
            public void run()
            {
                if(mainWindow().getMainWindowTable().getRowCount() > 0)
                {
                    for(int i=0;i < mainWindow().getMainWindowTable().getRowCount();i++)
                    {
                        if(mainWindow().getMainWindowTable().getValueAt(i, 0).toString().equals("true"))
                        {
                            int tableHour = Integer.parseInt(mainWindow().getMainWindowTable().getValueAt(i, 2).toString().split(":")[0]);
                            int tableMinute = Integer.parseInt(mainWindow().getMainWindowTable().getValueAt(i, 2).toString().split(":")[1]);

                            notificationWindowFrame.setAlwaysOnTop(mainWindow().getMainWindowSecondTabTopMostCheckBox());

                            if(mainWindow().getMainWindowTable().getValueAt(i, 1).toString().equals("~"))
                            {
                                if(tableHour == Integer.parseInt(new SimpleDateFormat("HH").format(new Date())) && tableMinute == Integer.parseInt(new SimpleDateFormat("mm").format(new Date())) )
                                {
                                    checkActionAndExecute(i);
                                }
                            }
                            else if(mainWindow().getMainWindowTable().getValueAt(i, 1).toString().equals("~*"))
                            {
                                if(!(tableHour == Integer.parseInt(new SimpleDateFormat("HH").format(new Date())) && tableMinute == Integer.parseInt(new SimpleDateFormat("mm").format(new Date()))) )
                                {
                                    mainWindow().getMainWindowTable().setValueAt("~",i, 1);
                                }
                            }
                            else
                            {
                                String reformatDate = mainWindow().getMainWindowTable().getValueAt(i, 1).toString().replaceAll("\\D", "."); //for any user input ex: 22/10/2020  22.10.2020  22 10 2020 22-10-2020 etc
                                int tableYear = Integer.parseInt(reformatDate.split("\\.")[2]);
                                int tableMonth = Integer.parseInt(reformatDate.split("\\.")[1]);
                                int tableDay = Integer.parseInt(reformatDate.split("\\.")[0]);

                                if(tableYear == Integer.parseInt(new SimpleDateFormat("yyyy").format(new Date())) && tableMonth == Integer.parseInt(new SimpleDateFormat("MM").format(new Date())) && tableDay == Integer.parseInt(new SimpleDateFormat("dd").format(new Date())) ) {

                                    if(tableHour == Integer.parseInt(new SimpleDateFormat("HH").format(new Date())) && tableMinute == Integer.parseInt(new SimpleDateFormat("mm").format(new Date())) ) {

                                        checkActionAndExecute(i);

                                        if(mainWindow().getMainWindowSecondTabAutoRemoveExecutedCheckBox()) {
                                            mainWindow().getTableModel().removeRow(i);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        };
        timer.schedule(tt, 0, timerInterval);
    }


    public void checkActionAndExecute(int i)
    {
        if(mainWindow().getMainWindowTable().getValueAt(i, 3).toString().equals("Alarm")){
            showNotification(i);
        }else if(mainWindow().getMainWindowTable().getValueAt(i, 3).toString().equals("Open File") || mainWindow().getMainWindowTable().getValueAt(i, 3).toString().equals("Open Folder") || mainWindow().getMainWindowTable().getValueAt(i, 3).toString().equals("Open URL")){
            openFileFolderOrURL(mainWindow().getMainWindowTable().getValueAt(i, 4).toString());
        }else if(mainWindow().getMainWindowTable().getValueAt(i, 3).toString().equals("Shutdown PC")){
            try {
                shutdownPC();
                showNotification(i);
            } catch (Exception e) {
                new ErrorBuilder(e.toString());
            }
        }
        if(!mainWindow().getMainWindowTable().getValueAt(i, 1).toString().equals("~")){
            mainWindow().getMainWindowTable().setValueAt(false,i, 0);
        }else {
            mainWindow().getMainWindowTable().setValueAt("~*",i, 1);
        }
    }

    public static void showNotification(int row)
    {
        playSound();

        if(mainWindow().getMainWindowSecondTabShowNotificationWindowCheckbox())
        {
            notificationWindow().notificationWindowMessageTextBox.setText(mainWindow().getMainWindowTable().getValueAt(row, 4).toString());
            notificationWindowFrame.setVisible(true);
            notificationWindow().notificationWindowMessageTextBox.revalidate();
            notificationWindow().notificationWindowMessageTextBox.repaint();
        }
    }

    public static void playSound()
    {
        File tempFile = new File(mainWindow().getMainWindowSecondTabAlarmAudioPathSettingTextBoxArea());

        if(!tempFile.exists())
        {
            return;
        }
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(tempFile.getAbsoluteFile());
            final Clip clip = AudioSystem.getClip();
            clip.addLineListener(e ->
            {
                if (e.getType()== LineEvent.Type.STOP)
                {
                    synchronized(clip)
                    {
                        clip.notifyAll();
                    }
                }
            });
            clip.open(audioInputStream);
            clip.start();

            do
            {
                synchronized (clip)
                {
                    clip.wait();
                }
            } while (clip.isRunning());

            clip.drain();
            clip.stop();
            clip.close();
            clip.flush();
        }
        catch (Exception e)
        {
            new ErrorBuilder(e.toString());
        }
    }

    public static void shutdownPC() throws RuntimeException, IOException
    {
        String shutdownCommand;
        String operatingSystem = System.getProperty("os.name");

        if(operatingSystem.toLowerCase().contains("windows"))
        {
            shutdownCommand = "shutdown.exe -s -t 60"; //0
        }
        else if (operatingSystem.toLowerCase().contains("linux"))
        {
            shutdownCommand = "shutdown -P +1"; //now
        }
        else if (operatingSystem.toLowerCase().contains("mac"))
        {
            shutdownCommand = "shutdown -h +1"; //now
        }
        else
        {
            throw new UnsupportedOperationException(String.format("Not supported for %1$1s", operatingSystem));
        }

        Runtime.getRuntime().exec(shutdownCommand);
    }

    public static void openFileFolderOrURL(String input)
    {
        String operatingSystem = System.getProperty("os.name");
        ProcessBuilder processBuilder = null;

        if(operatingSystem.toLowerCase().contains("windows"))
        {
            processBuilder = new ProcessBuilder("explorer",input);
            //processMonitor("Cmd ","/C ",input);
        } else if (operatingSystem.toLowerCase().contains("linux"))
        {
            processBuilder = new ProcessBuilder("xdg-open",input);
        }else if (operatingSystem.toLowerCase().contains("mac"))
        {
            processBuilder = new ProcessBuilder("open",input);
        }else {
            new ErrorBuilder(String.format("Not supported for %1$1s", operatingSystem));
        }

        try
        {
            assert processBuilder != null;
            processBuilder.start();
        }
        catch (Exception e)
        {
            new ErrorBuilder(e.toString());
        }
    }
}
