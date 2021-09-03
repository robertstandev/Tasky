import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class GUIBuilderClass extends Tasky
{
    public void setButton(JButton jButton,String text,Dimension newDimensionWidthHeight,Point newPointXYLocation,Color textColorNewColorRGBA,String tooltip)
    {
        jButton.setText(text);
        jButton.setSize(newDimensionWidthHeight);
        jButton.setLocation(newPointXYLocation);
        jButton.setForeground(textColorNewColorRGBA);
        jButton.setToolTipText(tooltip);
    }

    public void setTextBox(JTextField jTextField,String textExample,Color textColorNewColor,Color backgroundColorNewColor,Boolean isEditable,Dimension newDimensionWidthHeight,Point newPointXYLocation,String hint)
    {
        jTextField.setText(textExample);
        jTextField.setForeground(textColorNewColor);
        jTextField.setBackground(backgroundColorNewColor);
        jTextField.setEditable(isEditable);
        jTextField.setSize(newDimensionWidthHeight);
        jTextField.setLocation(newPointXYLocation);
        jTextField.setToolTipText(hint);
        jTextField.setBorder(null);
    }

    public void setCheckBox(JCheckBox jCheckBox, String textExample, Color textColorNewColor, Color backgroundColorNewColor, Dimension newDimensionWidthHeight, Point newPointXYLocation, String hint)
    {
        jCheckBox.setText(textExample);
        jCheckBox.setForeground(textColorNewColor);
        jCheckBox.setBackground(backgroundColorNewColor);
        jCheckBox.setSize(newDimensionWidthHeight);
        jCheckBox.setLocation(newPointXYLocation);
        jCheckBox.setToolTipText(hint);
        jCheckBox.setBorder(null);
    }

    public void setLabel(JLabel jLabel,String text,Font textFontNewFont,Color textColorNewColorRGBA,Dimension newDimensionWidthHeight,Point newPointXYLocation)
    {
        jLabel.setText(text);
        jLabel.setFont(textFontNewFont);
        jLabel.setForeground(textColorNewColorRGBA);
        jLabel.setSize(newDimensionWidthHeight);
        jLabel.setLocation(newPointXYLocation);
    }

    public void setJPanel(JPanel jPanel,Color backgroundColorNewColorRGBA,Component component)
    {
        jPanel.setBackground(backgroundColorNewColorRGBA);
        jPanel.setLayout(null);
        jPanel.add(component);
    }

    public void setComboBox(JComboBox<String> jComboBox,String[] items,Color backgroundColorNewColorRGBA,Dimension newDimensionWidthHeight,Point newPointXYLocation,String hint)
    {
        for (String item : items)
        {
            jComboBox.addItem(item);
        }

        jComboBox.setEditable(false);
        jComboBox.setBackground(backgroundColorNewColorRGBA);
        jComboBox.setSize(newDimensionWidthHeight);
        jComboBox.setLocation(newPointXYLocation);
        jComboBox.setToolTipText(hint);
        jComboBox.setBorder(null);
        jComboBox.setSelectedIndex(0);
    }
    public void addScrollFunction(JFrame jFrame, JTextArea jTextArea)
    {
        JScrollPane scrollTextArea = new JScrollPane(jTextArea);
        scrollTextArea.setLocation(new Point(10,30));
        scrollTextArea.setSize(new Dimension(285,140));
        JPanel panelGUI = new JPanel();
        panelGUI.add(scrollTextArea);
        jFrame.getContentPane().add(scrollTextArea);
    }

    public void setWindowFrame(JFrame jFrame,String titleString,boolean onTopBoolean,Dimension newDimensionWidthHeight,Point newPointXYLocation,Color newColor,float opacityFloat,boolean isResizableBoolean,boolean isUndecoratedBoolean,boolean hideFrameOnClose,boolean showInTaskBar)
    {
        jFrame.setTitle(titleString);
        jFrame.setUndecorated(isUndecoratedBoolean);
        jFrame.setSize(newDimensionWidthHeight);
        jFrame.setLocation(newPointXYLocation);
        jFrame.getContentPane().setBackground(newColor);
        jFrame.setResizable(isResizableBoolean);
        jFrame.setOpacity(opacityFloat);
        jFrame.setAlwaysOnTop(onTopBoolean);

        if(!showInTaskBar)
        {
            jFrame.setType(javax.swing.JFrame.Type.POPUP);
            jFrame.setFocusableWindowState(false);
        }

        jFrame.addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                if(hideFrameOnClose)
                {
                    jFrame.setVisible(false);
                }
                else
                {
                    saveLoadClass().saveSettingsToFile();
                    saveLoadClass().saveTableToFile();
                    System.exit(0);
                }
            }
        });
    }

    public void setTextBox(JTextArea JTextArea,String textExample,Color textColorNewColor,Color backgroundColorNewColor,Boolean isEditable,Dimension newDimensionWidthHeight,Point newPointXYLocation,String hint) {
        JTextArea.setText(textExample);
        JTextArea.setForeground(textColorNewColor);
        JTextArea.setBackground(backgroundColorNewColor);
        JTextArea.setEditable(isEditable);
        JTextArea.setSize(newDimensionWidthHeight);
        JTextArea.setLocation(newPointXYLocation);
        JTextArea.setToolTipText(hint);
    }
}
