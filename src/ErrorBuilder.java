import javax.swing.*;
import java.awt.*;

public class ErrorBuilder extends Tasky
{
    public ErrorBuilder(String errorString)
    {
        JFrame newFrame = new JFrame();
        JTextArea newTextArea = new JTextArea();

        newFrame.getContentPane().setLayout(null);
        guiBuilderClass().setWindowFrame(newFrame, "Tasky - Error", true, new Dimension(310,210), new Point(300,200), new Color(255,255,255,255), 1f, false, false, true, true);
        guiBuilderClass().setTextBox(newTextArea, errorString,new Color(255,0,0,255),new Color(255,255,255,255),false, new Dimension(280,140), new Point(10,10), "Error Message");
        guiBuilderClass().addScrollFunction(newFrame,newTextArea);
        newFrame.setVisible(true);
    }
}
