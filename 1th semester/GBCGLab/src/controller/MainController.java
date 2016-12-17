package controller;

import algorithm.GridValidator;
import algorithm.RandomPolygonGenerator;
import algorithm.UnionAlgorithm;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import utils.PointsWithEdges;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import ui.DialogPanel;
import ui.MainFrame;
import ui.PolygonSettingDialog;

public class MainController {
    
    private MainFrame mainFrame;
    
    private UnionAlgorithm unionAlgorithm;
    private GridValidator polygonGridValidator;
    private RandomPolygonGenerator anyPolygonGenerator;
    private RandomPolygonGenerator convexPolygonGenerator;
    
    private PointsWithEdges firstPolygonPointsWithEdges;
    private PointsWithEdges secondPolygonPointsWithEdges;
    private PointsWithEdges unionPolygonPointsWithEdges;

    public MainController(UnionAlgorithm unionAlgorithm, GridValidator polygonGridValidator,
            RandomPolygonGenerator anyPolygonGenerator, RandomPolygonGenerator convexPolygonGenerator) {
        this.unionAlgorithm = unionAlgorithm;
        this.polygonGridValidator = polygonGridValidator;
        this.anyPolygonGenerator = anyPolygonGenerator;
        this.convexPolygonGenerator = convexPolygonGenerator;
    }
    
    public void startApplication() {
        loadUI();
        initBasicListeners();
        initListenersForFirstPolygonSettingDialog();
        mainFrame.setVisible(true);
    }
    
    private void loadUI() {
        try {
            for (UIManager.LookAndFeelInfo info: UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException |
                IllegalAccessException | UnsupportedLookAndFeelException exception) {
            exception.printStackTrace();
        }
        mainFrame = new MainFrame();
    }
    
    private void initBasicListeners() {
        mainFrame.getBackButton().addActionListener(event -> makeStepBack());
        mainFrame.getFurtherButton().addActionListener(event -> makeStepFurther());
        DialogPanel dialogPanel = mainFrame.getDialogPanel();
        PolygonSettingDialog firstPolygonSettingDialog = dialogPanel.getFirstPolygonSettingDialog();
        firstPolygonSettingDialog.getCleanButton().addActionListener(
                event -> initListenersForFirstPolygonSettingDialog());
        firstPolygonSettingDialog.getGenerateAnyPolygonButton().addActionListener(
                event -> generateFirstPolygon(anyPolygonGenerator, firstPolygonSettingDialog));
        firstPolygonSettingDialog.getGenerateConvexPolygonButton().addActionListener(
                event -> generateFirstPolygon(convexPolygonGenerator, firstPolygonSettingDialog));
        PolygonSettingDialog secondPolygonSettingDialog = dialogPanel.getSecondPolygonSettingDialog();
        secondPolygonSettingDialog.getCleanButton().addActionListener(
                event -> initListenersForSecondPolygonSettingDialog());
        secondPolygonSettingDialog.getGenerateAnyPolygonButton().addActionListener(
                event -> generateSecondPolygon(anyPolygonGenerator, secondPolygonSettingDialog));
        secondPolygonSettingDialog.getGenerateConvexPolygonButton().addActionListener(
                event -> generateSecondPolygon(convexPolygonGenerator, secondPolygonSettingDialog));
    }
    
    private void initListenersForFirstPolygonSettingDialog() {
        initListenersForFirstPolygonSettingDialog(new PointsWithEdges(new ArrayList<>(), new ArrayList<>()));
    }
    
    private void initListenersForFirstPolygonSettingDialog(PointsWithEdges pointsWithEdges) {
        mainFrame.getDialogPanel().setCurrentDialog(DialogPanel.FIRST_POLYGON_SETTING_DIALOG);
        firstPolygonPointsWithEdges = pointsWithEdges;
        SimplePaintStrategy paintStrategy = new SimplePaintStrategy(pointsWithEdges);
        mainFrame.getImagePanel().removeAllImagePanelListeners();
        mainFrame.getImagePanel().addImagePanelListener(new SettingEdgeController(paintStrategy));
        mainFrame.getImagePanel().setImagePanelPaintStrategy(paintStrategy);
        mainFrame.getImagePanel().repaint();
    }
    
    private void initListenersForSecondPolygonSettingDialog() {
        initListenersForSecondPolygonSettingDialog(new PointsWithEdges(new ArrayList<>(), new ArrayList<>()));
    }

    private void initListenersForSecondPolygonSettingDialog(PointsWithEdges pointsWithEdges) {
        mainFrame.getDialogPanel().setCurrentDialog(DialogPanel.SECOND_POLYGON_SETTING_DIALOG);
        secondPolygonPointsWithEdges = pointsWithEdges;
        SimplePaintStrategy paintStrategy = new SimplePaintStrategy(pointsWithEdges, firstPolygonPointsWithEdges);
        mainFrame.getImagePanel().removeAllImagePanelListeners();
        mainFrame.getImagePanel().addImagePanelListener(new SettingEdgeController(paintStrategy));
        mainFrame.getImagePanel().setImagePanelPaintStrategy(paintStrategy);
        mainFrame.getImagePanel().repaint();
    }
    
    private void initListenersForResultDialog(PointsWithEdges pointsWithEdges) {
        mainFrame.getDialogPanel().setCurrentDialog(DialogPanel.RESULT_DIALOG);
        unionPolygonPointsWithEdges = pointsWithEdges;
        SimplePaintStrategy paintStrategy = new SimplePaintStrategy(pointsWithEdges);
        mainFrame.getImagePanel().removeAllImagePanelListeners();
        mainFrame.getImagePanel().setImagePanelPaintStrategy(paintStrategy);
        mainFrame.getImagePanel().repaint();
    }
    
    private void makeStepBack() {
        switch (mainFrame.getDialogPanel().getCurrentDialogIdentifier()) {
            case DialogPanel.SECOND_POLYGON_SETTING_DIALOG:
                initListenersForFirstPolygonSettingDialog(firstPolygonPointsWithEdges);
                break;
            case DialogPanel.RESULT_DIALOG:
                initListenersForSecondPolygonSettingDialog(secondPolygonPointsWithEdges);
                break;
        }
    }
        
    private void makeStepFurther() {
        boolean validationResult;
        switch (mainFrame.getDialogPanel().getCurrentDialogIdentifier()) {
            case DialogPanel.FIRST_POLYGON_SETTING_DIALOG:
                validationResult = validateGrid(firstPolygonPointsWithEdges);
                if (validationResult) {
                    initListenersForSecondPolygonSettingDialog();
                }
                break;
            case DialogPanel.SECOND_POLYGON_SETTING_DIALOG:
                validationResult = validateGrid(secondPolygonPointsWithEdges);
                if (validationResult) {
                    makePolygonsUnion();
                }
                break;
        }
    }
    
    private boolean validateGrid(PointsWithEdges pointsWithEdges) {
        boolean validationResult = polygonGridValidator.validateGrid(pointsWithEdges);
        if (!validationResult) {
            JOptionPane.showMessageDialog(mainFrame, "Grid is not a polygon!", "Validation", JOptionPane.INFORMATION_MESSAGE);
        }
        return validationResult;
    }
    
    private void generateFirstPolygon(RandomPolygonGenerator polygonGenerator, PolygonSettingDialog polygonSettingDialog) {
        PointsWithEdges pointsWithEdges = generatePolygon(polygonGenerator, polygonSettingDialog);
        initListenersForFirstPolygonSettingDialog(pointsWithEdges);
    }
    
    private void generateSecondPolygon(RandomPolygonGenerator polygonGenerator, PolygonSettingDialog polygonSettingDialog) {
        PointsWithEdges pointsWithEdges = generatePolygon(polygonGenerator, polygonSettingDialog);
        initListenersForSecondPolygonSettingDialog(pointsWithEdges);
    }
    
    private PointsWithEdges generatePolygon(RandomPolygonGenerator polygonGenerator, PolygonSettingDialog polygonSettingDialog) {
        return polygonGenerator.generatePolygon(
                polygonSettingDialog.getTx(),
                polygonSettingDialog.getTy(),
                polygonSettingDialog.getA(),
                polygonSettingDialog.getB(),
                polygonSettingDialog.getTheta()
        );
    }
    
    void makePolygonsUnion() {
        unionPolygonPointsWithEdges = unionAlgorithm.unionPolygons(firstPolygonPointsWithEdges, secondPolygonPointsWithEdges);
        initListenersForResultDialog(unionPolygonPointsWithEdges);
    }

}