import javax.swing.table.DefaultTableModel;
import java.io.*;
import java.util.ArrayList;

public class SaveLoadClass extends Tasky
{
    private final String tableContentsLocation;
    private final String alarmAudioLocation;
    private final boolean applicationTopMost;
    private final boolean tableAutoRemoveItem;

    public String getTableContentsLocation() { return this.tableContentsLocation; }
    public String getAlarmAudioLocation() { return this.alarmAudioLocation; }
    public boolean getApplicationTopMost() { return this.applicationTopMost; }
    public boolean getTableAutoRemoveItem() { return this.tableAutoRemoveItem; }

    public SaveLoadClass()
    {
        ArrayList<String> loadData = getPathsForCurrentSettings(getSettingsFileLocation());
        this.tableContentsLocation = loadData.get(0);
        this.alarmAudioLocation = loadData.get(1);
        this.applicationTopMost = Boolean.parseBoolean(loadData.get(2));
        this.tableAutoRemoveItem =  Boolean.parseBoolean(loadData.get(3));
    }

    public String getSettingsFileLocation()
    {
        String operatingSystem = System.getProperty("os.name");
        String settingsFile = System.getProperty("user.home");
        if(operatingSystem.toLowerCase().contains("windows"))
        {
            settingsFile = settingsFile + "\\";
        }
        else if (operatingSystem.toLowerCase().contains("linux") || operatingSystem.toLowerCase().contains("mac")){
            settingsFile = settingsFile + "/";
        }
        else
        {
            throw new UnsupportedOperationException(String.format("Not supported for %1$1s", operatingSystem));
        }

        File tempFile = new File(settingsFile + "TaskySettings.ini");
        if(!tempFile.exists())
        {
            try
            {
                BufferedWriter writer = new BufferedWriter(new FileWriter(settingsFile + "TaskySettings.ini"));
                writer.write("Table Contents Location |" + settingsFile + "TaskyTableContents.txt");
                writer.write(System.getProperty("line.separator"));
                writer.write("Alarm Audio Location | ");
                writer.write(System.getProperty("line.separator"));
                writer.write("Application Top Most |true");
                writer.write(System.getProperty("line.separator"));
                writer.write("Table Auto Delete Item |true");
                writer.close();
            }
            catch (Exception e)
            {
                new ErrorBuilder(e.toString());
            }
        }
        return settingsFile+"TaskySettings.ini";
    }



    public ArrayList<String> getPathsForCurrentSettings(String settingsLocation)
    {
        File fileLocation = new File(settingsLocation);
        String line;
        String[] parts;
        ArrayList<String> allSettings = new ArrayList<String>();
        try
        {
            if(fileLocation.exists())
            {
                BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fileLocation)));
                while ((line = br.readLine()) != null)
                {
                    parts = line.split("\\|");
                    allSettings.add(parts[1]);
                }
                br.close();
            }
        }
        catch (IOException e)
        {
            new ErrorBuilder(e.toString());
        }
        return allSettings;
    }

    public void saveTableToFile()
    {
        File fileLocation = new File(mainWindow().getMainWindowSecondTabTablePathSettingTextBoxArea());
        StringBuilder str = new StringBuilder();
        try
        {
            for (int row = 0; row < mainWindow().getMainWindowTable().getRowCount(); row++)
            {
                for (int col = 0; col < mainWindow().getMainWindowTable().getColumnCount(); col++)
                {
                    if(col < mainWindow().getMainWindowTable().getColumnCount()-1)
                    {
                        str.append(mainWindow().getMainWindowTable().getValueAt(row, col)).append(" ----- ");
                    }
                    else
                    {
                        str.append(mainWindow().getMainWindowTable().getValueAt(row, col)).append("\r");
                    }
                }
            }
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileLocation));
            writer.write(str.toString());
            writer.close();
        }
        catch (IOException e)
        {
            new ErrorBuilder(e.toString());
        }
    }


    public void loadFromFileToTable(DefaultTableModel tableModel)
    {
        File fileLocation = new File(this.tableContentsLocation);

        if(!fileLocation.exists()) { return; }

        String line;
        String[] parts;
        try
        {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fileLocation)));
            while ((line = br.readLine()) != null)
            {
                parts = line.split("\\ ----- ");
                tableModel.addRow(new Object[0]);
                tableModel.setValueAt(Boolean.valueOf(parts[0]),tableModel.getRowCount()-1,0);
                tableModel.setValueAt(parts[1],tableModel.getRowCount()-1,1);
                tableModel.setValueAt(parts[2],tableModel.getRowCount()-1,2);
                tableModel.setValueAt(parts[3],tableModel.getRowCount()-1,3);
                tableModel.setValueAt(parts[4],tableModel.getRowCount()-1,4);
            }
            br.close();
        }
        catch (IOException e)
        {
            new ErrorBuilder(e.toString());
        }
    }


    public void saveSettingsToFile()
    {
        File tempFile = new File(getSettingsFileLocation());

        if(!tempFile.exists()) { return; }

        try
        {
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

            writer.write("Table Contents Location |" + (mainWindow().getMainWindowSecondTabTablePathSettingTextBoxArea().length() > 0 ? mainWindow().getMainWindowSecondTabTablePathSettingTextBoxArea() : this.tableContentsLocation));
            writer.write(System.getProperty("line.separator"));

            writer.write("Alarm Audio Location |" + (mainWindow().getMainWindowSecondTabAlarmAudioPathSettingTextBoxArea().length() > 0 ? mainWindow().getMainWindowSecondTabAlarmAudioPathSettingTextBoxArea() : this.alarmAudioLocation));
            writer.write(System.getProperty("line.separator"));

            writer.write("Application Top Most |" + mainWindow().getMainWindowSecondTabTopMostCheckBox());
            writer.write(System.getProperty("line.separator"));

            writer.write("Table Auto Delete Item |" + mainWindow().getMainWindowSecondTabAutoRemoveExecutedCheckBox());
            writer.close();
        }
        catch (Exception e)
        {
            new ErrorBuilder(e.toString());
        }
    }
}
