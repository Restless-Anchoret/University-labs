package ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

public class ImagePanel extends JPanel {

    public static final Color BACKGROUND_COLOR = Color.WHITE;
    public static final Color FRAME_COLOR = Color.BLACK;
    
    private ImagePanelListenerSupport imagePanelListenerSupport = new ImagePanelListenerSupport();
    private ImagePanelPaintStrategy imagePanelPaintStrategy = (graphics, width, height) -> { };
    private int previousX;
    private int previousY;
    
    public ImagePanel() {
        initComponents();
        initListeners();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void initListeners() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent event) {
                int x = event.getX();
                int y = event.getY();
                int width = getWidth();
                int height = getHeight();
                switch (event.getButton()) {
                    case MouseEvent.BUTTON1:
                        imagePanelListenerSupport.fireMouseLeftClicked(ImagePanel.this, x, y, width, height); break;
                    case MouseEvent.BUTTON2:
                        imagePanelListenerSupport.fireMouseMiddleClicked(ImagePanel.this, x, y, width, height); break;
                    case MouseEvent.BUTTON3:
                        imagePanelListenerSupport.fireMouseRightClicked(ImagePanel.this, x, y, width, height); break;
                }
            }
        });
        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent event) {
                int x = event.getX();
                int y = event.getY();
                int width = getWidth();
                int height = getHeight();
                if (event.getButton() == MouseEvent.NOBUTTON) {
                    imagePanelListenerSupport.fireMouseMovedWithoutPressedButtons(ImagePanel.this, previousX, previousY, x, y, width, height);
                }
                previousX = event.getX();
                previousY = event.getY();
            }
            @Override
            public void mouseDragged(MouseEvent event) {
                int x = event.getX();
                int y = event.getY();
                int width = getWidth();
                int height = getHeight();
                if ((event.getModifiersEx() & InputEvent.BUTTON1_DOWN_MASK) != 0) {
                    imagePanelListenerSupport.fireMouseLeftDragged(ImagePanel.this, previousX, previousY, x, y, width, height);
                } else if ((event.getModifiersEx() & InputEvent.BUTTON2_DOWN_MASK) != 0) {
                    imagePanelListenerSupport.fireMouseMiddleDragged(ImagePanel.this, previousX, previousY, x, y, width, height);
                } else if ((event.getModifiersEx() & InputEvent.BUTTON3_DOWN_MASK) != 0) {
                    imagePanelListenerSupport.fireMouseRightDragged(ImagePanel.this, previousX, previousY, x, y, width, height);
                }
                previousX = event.getX();
                previousY = event.getY();
            }
        });
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
    
    public void addImagePanelListener(ImagePanelListener listener) {
        imagePanelListenerSupport.addImagePanelListener(listener);
    }
    
    public void removeImagePanelListener(ImagePanelListener listener) {
        imagePanelListenerSupport.removeImagePanelListener(listener);
    }
    
    public void removeAllImagePanelListeners() {
        imagePanelListenerSupport.removeAllImagePanelListeners();
    }

    public ImagePanelPaintStrategy getImagePanelPaintStrategy() {
        return imagePanelPaintStrategy;
    }

    public void setImagePanelPaintStrategy(ImagePanelPaintStrategy imagePanelPaintStrategy) {
        this.imagePanelPaintStrategy = imagePanelPaintStrategy;
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        int imageWidth = getWidth();
        int imageHeight = getHeight();
        BufferedImage image = (BufferedImage) createImage(imageWidth, imageHeight);
        Graphics2D graphics2D = (Graphics2D) image.getGraphics();
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics2D.setColor(BACKGROUND_COLOR);
        graphics2D.fillRect(0, 0, imageWidth - 1, imageHeight - 1);
        imagePanelPaintStrategy.paint(graphics2D, imageWidth, imageHeight);
        graphics2D.setColor(FRAME_COLOR);
        graphics2D.drawRect(0, 0, imageWidth - 1, imageHeight - 1);
        graphics.drawImage(image, 0, 0, this);
    }
    
}
