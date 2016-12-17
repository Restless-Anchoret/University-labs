package ui;

import java.awt.CardLayout;
import javax.swing.JPanel;

public class DialogPanel extends JPanel {

    public static final String FIRST_POLYGON_SETTING_DIALOG = "first_polygon_setting_dialog";
    public static final String SECOND_POLYGON_SETTING_DIALOG = "second_polygon_setting_dialog";
    public static final String RESULT_DIALOG = "result_dialog";
    
    private PolygonSettingDialog firstPolygonSettingDialog;
    private PolygonSettingDialog secondPolygonSettingDialog;
    private ResultDialog resultDialog;
    
    private CardLayout layout;
    private String currentDialogIdentifier;
    
    public DialogPanel() {
        initCustomComponents();
    }

    private void initCustomComponents() {
        layout = new CardLayout();
        setLayout(layout);
        
        firstPolygonSettingDialog = new PolygonSettingDialog();
        secondPolygonSettingDialog = new PolygonSettingDialog();
        resultDialog = new ResultDialog();
        
        add(firstPolygonSettingDialog, FIRST_POLYGON_SETTING_DIALOG);
        add(secondPolygonSettingDialog, SECOND_POLYGON_SETTING_DIALOG);
        add(resultDialog, RESULT_DIALOG);
        
        setCurrentDialog(FIRST_POLYGON_SETTING_DIALOG);
    }

    public PolygonSettingDialog getFirstPolygonSettingDialog() {
        return firstPolygonSettingDialog;
    }

    public PolygonSettingDialog getSecondPolygonSettingDialog() {
        return secondPolygonSettingDialog;
    }


    public ResultDialog getResultDialog() {
        return resultDialog;
    }

    public String getCurrentDialogIdentifier() {
        return currentDialogIdentifier;
    }

    public void setCurrentDialog(String currentDialogIdentifier) {
        this.currentDialogIdentifier = currentDialogIdentifier;
        layout.show(this, currentDialogIdentifier);
    }
    
}