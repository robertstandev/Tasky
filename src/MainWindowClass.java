import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class MainWindowClass extends Tasky
{
    private SaveLoadClass saveLoadClass;

    public MainWindowClass(GUIBuilderClass guiBuilderClass, SaveLoadClass saveLoadClass, Dimension screenSize)
    {
        guiBuilderClass.setWindowFrame(mainWindowFrame,"Tasky - Main Window",saveLoadClass.getApplicationTopMost(), new Dimension(800,600),new Point(screenSize.width/2 - 400, screenSize.height/2 - 300),new Color(255,255,255,255),1f,false,false,false,true);
        //mainWindowFrame.getContentPane().setLayout(null);

        createFirstTabTable(guiBuilderClass);
        createFirstTabButtons(guiBuilderClass);
        createFirstTabTextBoxes(guiBuilderClass);
        createFirstTabComboBoxes(guiBuilderClass);
        createFirstTabLabels(guiBuilderClass);

        createSecondTabTextBoxes(guiBuilderClass, saveLoadClass);
        createSecondTabButtons(guiBuilderClass);
        createSecondTabCheckBoxes(guiBuilderClass, saveLoadClass);
        createSecondTabLabels(guiBuilderClass);

        createTabs();

        saveLoadClass.loadFromFileToTable(this.tableModel);

        mainWindowFrame.setVisible(true);
    }


    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=Data=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    private final JFrame mainWindowFrame = new JFrame();
    private final JTable mainWindowTable = new JTable();
    private DefaultTableModel tableModel;

    private final JButton mainWindowAddRowButton = new JButton();
    private final JButton mainWindowExecuteNowButton = new JButton();
    private final JButton mainWindowRemoveSelectedRowButton = new JButton();
    private final JButton mainWindowSecondTabTablePathSettingButton = new JButton();
    private final JButton mainWindowSecondTabAlarmAudioPathSettingButton = new JButton();

    private final JTextField mainWindowFirstTabDateTextBox = new JTextField();
    private final JTextField mainWindowFirstTabTimeTextBox = new JTextField();
    private final JTextField mainWindowFirstTabMessageTextBox = new JTextField();
    private final JTextField mainWindowSecondTabTablePathSettingTextBoxArea = new JTextField();
    private final JTextField mainWindowSecondTabAlarmAudioPathSettingTextBoxArea = new JTextField();

    private final JCheckBox mainWindowSecondTabTopMostCheckBox = new JCheckBox();
    private final JCheckBox mainWindowSecondTabAutoRemoveExecutedCheckBox = new JCheckBox();

    private final JComboBox<String> mainWindowFirstTabActionComboBox = new JComboBox<>();

    private final JLabel mainWindowFirstTabDateLabel = new JLabel();
    private final JLabel mainWindowFirstTabTimeLabel = new JLabel();
    private final JLabel mainWindowFirstTabActionLabel = new JLabel();
    private final JLabel mainWindowFirstTabMessageLabel = new JLabel();
    private final JLabel mainWindowSecondTabTablePathSettingLabel = new JLabel();
    private final JLabel mainWindowSecondTabAlarmAudioPathSettingLabel = new JLabel();

    private final JPanel mainWindowPage1 = new JPanel();
    private final JPanel mainWindowPage2 = new JPanel();


    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=GETTERS=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    public JTable getMainWindowTable() {
        return mainWindowTable;
    }
    public DefaultTableModel getTableModel() {
        return tableModel;
    }
    public String getMainWindowSecondTabTablePathSettingTextBoxArea() { return this.mainWindowSecondTabTablePathSettingTextBoxArea.getText(); }
    public String getMainWindowSecondTabAlarmAudioPathSettingTextBoxArea() { return this.mainWindowSecondTabAlarmAudioPathSettingTextBoxArea.getText(); }
    public Boolean getMainWindowSecondTabAutoRemoveExecutedCheckBox() { return this.mainWindowSecondTabAutoRemoveExecutedCheckBox.isSelected(); }
    public Boolean getMainWindowSecondTabTopMostCheckBox() { return this.mainWindowSecondTabTopMostCheckBox.isSelected(); }


    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=GUI=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    public void createTabs()
    {
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Table", null, mainWindowPage1,"Table");
        tabbedPane.addTab("Options", null, mainWindowPage2,"Options");
        mainWindowFrame.getContentPane().add(tabbedPane);
    }


    public void createFirstTabTable(GUIBuilderClass guiBuilderClass)
    {
        //ADD JScrollPane
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(25,75,740,450);

        //THE TABLE
        scrollPane.setViewportView(mainWindowTable);

        //ASSIGN THE MODEL TO TABLE
        tableModel = new DefaultTableModel()
        {
            public Class<?> getColumnClass(int column)
            {
                if (column == 0)
                {
                    return Boolean.class;
                }
                return String.class;
            }
        };
        mainWindowTable.setModel(tableModel);
        mainWindowTable.getTableHeader().setReorderingAllowed(false);


        //THE COLUMNS
        tableModel.addColumn("Active");
        tableModel.addColumn("Date");
        tableModel.addColumn("Time");
        tableModel.addColumn("Action");
        tableModel.addColumn("Message or Path");

        guiBuilderClass.setJPanel(mainWindowPage1, new Color(255,255,255,255),scrollPane);
    }


    public void createFirstTabButtons(GUIBuilderClass guiBuilderClass)
    {
        //ADD ROW BUTTON
        mainWindowAddRowButton.addActionListener(arg0 ->
        {
            if(mainWindowFirstTabDateTextBox.getText().length() > 0 && mainWindowFirstTabTimeTextBox.getText().length() > 2 && mainWindowFirstTabMessageTextBox.getText().length() > 0)
            {
                tableModel.addRow(new Object[0]);
                tableModel.setValueAt(true,tableModel.getRowCount() - 1,0);
                tableModel.setValueAt(mainWindowFirstTabDateTextBox.getText(),tableModel.getRowCount()-1,1);
                tableModel.setValueAt(mainWindowFirstTabTimeTextBox.getText(),tableModel.getRowCount()-1,2);
                tableModel.setValueAt(mainWindowFirstTabActionComboBox.getSelectedItem().toString(),tableModel.getRowCount()-1,3);
                tableModel.setValueAt(mainWindowFirstTabMessageTextBox.getText(),tableModel.getRowCount()-1,4);
            }
            else
            {
                new ErrorBuilder("Please fill all the boxes !");
            }
        });
        guiBuilderClass.setButton(mainWindowAddRowButton, "Add Row", new Dimension(95,25),new Point(420,8) , new Color(50,50,255,255), "Create New Row From Your Input");

        //EXECUTE NOW BUTTON
        mainWindowExecuteNowButton.addActionListener(arg0 ->
        {
            for(int i=0;i < mainWindowTable.getRowCount();i++)
            {
                if(mainWindowTable.isRowSelected(i))
                {
                    notificationWindow().checkActionAndExecute(i);
                }
            }

        });
        guiBuilderClass.setButton(mainWindowExecuteNowButton, "Execute Selected", new Dimension(155,25),new Point(610,8) , new Color(255,100,0,255), "Execute Selected Row Now");

        //REMOVE SELECTED ROW BUTTON
        mainWindowRemoveSelectedRowButton.addActionListener(arg0 ->
        {
            DefaultTableModel model = (DefaultTableModel) mainWindowTable.getModel();
            int[] rows = mainWindowTable.getSelectedRows();
            for(int i=0;i<rows.length;i++)
            {
                model.removeRow(rows[i]-i);
            }
        });
        guiBuilderClass.setButton(mainWindowRemoveSelectedRowButton, "Remove Selected", new Dimension(155,25),new Point(610,42) , new Color(255,0,0,255), "Remove Selected Row");

        guiBuilderClass.setJPanel(mainWindowPage1, new Color(255,255,255,255),mainWindowAddRowButton);
        guiBuilderClass.setJPanel(mainWindowPage1, new Color(255,255,255,255),mainWindowExecuteNowButton);
        guiBuilderClass.setJPanel(mainWindowPage1, new Color(255,255,255,255),mainWindowRemoveSelectedRowButton);
    }


    public void createFirstTabTextBoxes(GUIBuilderClass guiBuilderClass)
    {
        guiBuilderClass.setTextBox(mainWindowFirstTabDateTextBox, "~",new Color(0,0,0,255),new Color(245,245,245,255),true, new Dimension(100,20), new Point(80,10), "Use ~ For Everyday Else Use Date Format: 25.10.2020");
        guiBuilderClass.setJPanel(mainWindowPage1, new Color(255,255,255,255),mainWindowFirstTabDateTextBox);

        guiBuilderClass.setTextBox(mainWindowFirstTabTimeTextBox, "00:05",new Color(0,0,0,255),new Color(245,245,245,255),true, new Dimension(100,20), new Point(80,40), "Use 24h Time Format With 0 If Number Lower Than 9 Example: 00:05");
        guiBuilderClass.setJPanel(mainWindowPage1, new Color(255,255,255,255),mainWindowFirstTabTimeTextBox);

        guiBuilderClass.setTextBox(mainWindowFirstTabMessageTextBox, "Prepare For Meeting!",new Color(0,0,0,255),new Color(245,245,245,255),true, new Dimension(100,20), new Point(270,40), "Type What Text You Want");
        guiBuilderClass.setJPanel(mainWindowPage1, new Color(255,255,255,255),mainWindowFirstTabMessageTextBox);
    }


    public void createFirstTabLabels(GUIBuilderClass guiBuilderClass)
    {
        guiBuilderClass.setLabel(mainWindowFirstTabDateLabel, "Date:", new Font("Arial",Font.PLAIN,12), new Color(0,0,0,255),new Dimension(40,10),new Point(25,15));
        guiBuilderClass.setJPanel(mainWindowPage1, new Color(255,255,255,255),mainWindowFirstTabDateLabel);

        guiBuilderClass.setLabel(mainWindowFirstTabTimeLabel, "Time:", new Font("Arial",Font.PLAIN,12), new Color(0,0,0,255),new Dimension(40,10),new Point(25,45));
        guiBuilderClass.setJPanel(mainWindowPage1, new Color(255,255,255,255),mainWindowFirstTabTimeLabel);

        guiBuilderClass.setLabel(mainWindowFirstTabActionLabel, "Action:", new Font("Arial",Font.PLAIN,12), new Color(0,0,0,255),new Dimension(50,10),new Point(195,15));
        guiBuilderClass.setJPanel(mainWindowPage1, new Color(255,255,255,255),mainWindowFirstTabActionLabel);

        guiBuilderClass.setLabel(mainWindowFirstTabMessageLabel, "Message:", new Font("Arial",Font.PLAIN,12), new Color(0,0,0,255),new Dimension(70,15),new Point(195,45));
        guiBuilderClass.setJPanel(mainWindowPage1, new Color(255,255,255,255),mainWindowFirstTabMessageLabel);
    }


    public void createFirstTabComboBoxes(GUIBuilderClass guiBuilderClass)
    {
        guiBuilderClass.setComboBox(mainWindowFirstTabActionComboBox, new String[] {"Alarm","Open File","Open Folder","Open URL","Shutdown PC"}, new Color(245,245,245,255), new Dimension(120,20), new Point(270,10), "Actions:"+"Alarm - To Just Show A Window With The Message"+"Open - To Open A File Or Folder");
        guiBuilderClass.setJPanel(mainWindowPage1, new Color(255,255,255,255), mainWindowFirstTabActionComboBox);
        mainWindowFirstTabActionComboBox.addActionListener(arg0 ->
        {
            if(mainWindowFirstTabActionComboBox.getSelectedIndex() == 0 || mainWindowFirstTabActionComboBox.getSelectedIndex() == 4)
            {
                mainWindowFirstTabMessageLabel.setText("Message:");
            }else{
                mainWindowFirstTabMessageLabel.setText("Path:");
            }
            mainWindowFirstTabMessageTextBox.setText("");
        });
    }


    public void createSecondTabTextBoxes(GUIBuilderClass guiBuilderClass , SaveLoadClass saveLoadClass)
    {
        guiBuilderClass.setTextBox(mainWindowSecondTabTablePathSettingTextBoxArea, saveLoadClass.getTableContentsLocation(), new Color(0,0,0,255),new Color(245,245,245,255),true, new Dimension(400,20), new Point(140,25), "Location where the table contents will saved");
        guiBuilderClass.setJPanel(mainWindowPage2, new Color(255,255,255,255),mainWindowSecondTabTablePathSettingTextBoxArea);

        guiBuilderClass.setTextBox(mainWindowSecondTabAlarmAudioPathSettingTextBoxArea, saveLoadClass.getAlarmAudioLocation(), new Color(0,0,0,255),new Color(245,245,245,255),true, new Dimension(400,20), new Point(140,60), "Location where the alarm audio file is");
        guiBuilderClass.setJPanel(mainWindowPage2, new Color(255,255,255,255),mainWindowSecondTabAlarmAudioPathSettingTextBoxArea);
    }


    public void createSecondTabButtons(GUIBuilderClass guiBuilderClass)
    {
        JFileChooser FileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        mainWindowSecondTabTablePathSettingButton.addActionListener(arg0 ->
        {
            FileChooser.setDialogTitle("Select Path And Name For Table Contents");
            int UserInputResult = FileChooser.showSaveDialog(null);
            if (UserInputResult == JFileChooser.APPROVE_OPTION)
            {
                mainWindowSecondTabTablePathSettingTextBoxArea.setText(FileChooser.getSelectedFile().getAbsolutePath());
            }
        });
        guiBuilderClass.setButton(mainWindowSecondTabTablePathSettingButton, "Modify Path", new Dimension(120,25),new Point(560,20) , new Color(0,0,0,255), "Modify Table Contents Path");
        guiBuilderClass.setJPanel(mainWindowPage2, new Color(255,255,255,255),mainWindowSecondTabTablePathSettingButton);

        mainWindowSecondTabAlarmAudioPathSettingButton.addActionListener(arg0 ->
        {
            FileChooser.setDialogTitle("Select An Audio File From PC");
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Audio Files", "wav", "wav");
            FileChooser.setFileFilter(filter);
            int UserInputResult = FileChooser.showOpenDialog(null);
            if (UserInputResult == JFileChooser.APPROVE_OPTION && FileChooser.getSelectedFile().exists())
            {
                mainWindowSecondTabAlarmAudioPathSettingTextBoxArea.setText(FileChooser.getSelectedFile().getAbsolutePath());
            }
        });
        guiBuilderClass.setButton(mainWindowSecondTabAlarmAudioPathSettingButton, "Select File", new Dimension(120,25),new Point(560,55) , new Color(0,0,0,255), "Select Alarm Audio Path (.wav)");
        guiBuilderClass.setJPanel(mainWindowPage2, new Color(255,255,255,255),mainWindowSecondTabAlarmAudioPathSettingButton);
    }


    public void createSecondTabCheckBoxes(GUIBuilderClass guiBuilderClass, SaveLoadClass saveLoadClass)
    {
        guiBuilderClass.setCheckBox(mainWindowSecondTabTopMostCheckBox, "Application Top Most",new Color(0,0,0,255),new Color(255,255,255,255), new Dimension(180,20), new Point(25,100), "Make the application windows be on top of all other system windows");
        guiBuilderClass.setJPanel(mainWindowPage2, new Color(255,255,255,255),mainWindowSecondTabTopMostCheckBox);
        mainWindowSecondTabTopMostCheckBox.setSelected(saveLoadClass.getApplicationTopMost());
        mainWindowSecondTabTopMostCheckBox.addActionListener(arg0 -> { mainWindowFrame.setAlwaysOnTop(mainWindowSecondTabTopMostCheckBox.isSelected()); });

        guiBuilderClass.setCheckBox(mainWindowSecondTabAutoRemoveExecutedCheckBox, "Automatically remove items",new Color(0,0,0,255),new Color(255,255,255,255), new Dimension(220,20), new Point(25,125), "After an item is executed if it doesn't have '~' as date , it will be automatically removed from the table");
        guiBuilderClass.setJPanel(mainWindowPage2, new Color(255,255,255,255),mainWindowSecondTabAutoRemoveExecutedCheckBox);
        mainWindowSecondTabAutoRemoveExecutedCheckBox.setSelected(saveLoadClass.getTableAutoRemoveItem());
    }


    public void createSecondTabLabels(GUIBuilderClass guiBuilderClass)
    {
        guiBuilderClass.setLabel(mainWindowSecondTabTablePathSettingLabel, "Table Save Path:", new Font("Arial",Font.PLAIN,12), new Color(0,0,0,255),new Dimension(105,10),new Point(20,30));
        guiBuilderClass.setJPanel(mainWindowPage2, new Color(255,255,255,255),mainWindowSecondTabTablePathSettingLabel);

        guiBuilderClass.setLabel(mainWindowSecondTabAlarmAudioPathSettingLabel, "Alarm Audio Path:", new Font("Arial",Font.PLAIN,12), new Color(0,0,0,255),new Dimension(115,10),new Point(20,65));
        guiBuilderClass.setJPanel(mainWindowPage2, new Color(255,255,255,255),mainWindowSecondTabAlarmAudioPathSettingLabel);
    }
}
