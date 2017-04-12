package com.ran.dissertation.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

public class ImagePanel extends JPanel {

    public static final Color BACKGROUND_COLOR = Color.WHITE;
    public static final Color FRAME_COLOR = Color.BLACK;
    
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
        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent event) {
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
                    imagePanelListenerSupport.fireMouseDraggedLeftMouseButton(ImagePanel.this, previousX, previousY, x, y, width, height);
                } else if ((event.getModifiersEx() & InputEvent.BUTTON2_DOWN_MASK) != 0) {
                    imagePanelListenerSupport.fireMouseDraggedMiddleMouseButton(ImagePanel.this, previousX, previousY, x, y, width, height);
                } else if ((event.getModifiersEx() & InputEvent.BUTTON3_DOWN_MASK) != 0) {
                    imagePanelListenerSupport.fireMouseDraggedRightMouseButton(ImagePanel.this, previousX, previousY, x, y, width, height);
                }
                previousX = x;
                previousY = y;
            }
        });
        addMouseWheelListener(new MouseAdapter() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent event) {
                imagePanelListenerSupport.fireMouseWheelMoved(ImagePanel.this,
                        event.getX(), event.getY(), getWidth(), getHeight(), event.getWheelRotation());
            }
        });
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables

    private int previousX;
    private int previousY;
    private ImagePanelListenerSupport imagePanelListenerSupport = new ImagePanelListenerSupport();
    private ImagePanelPaintStrategy imagePanelPaintStrategy = (paintDelegate, width, height) -> { };
    private PaintDelegate paintDelegate = new PaintDelegate();
    private BufferedImage lastFrameImage = null;
    
    public void addImagePanelListener(ImagePanelListener listener) {
        imagePanelListenerSupport.addImagePanelListener(listener);
    }
    
    public void removeImagePanelListener(ImagePanelListener listener) {
        imagePanelListenerSupport.removeImagePanelListener(listener);
    }

    public ImagePanelPaintStrategy getImagePanelPaintStrategy() {
        return imagePanelPaintStrategy;
    }

    public void setImagePanelPaintStrategy(ImagePanelPaintStrategy imagePanelPaintStrategy) {
        this.imagePanelPaintStrategy = imagePanelPaintStrategy;
    }

    public BufferedImage getLastFrameImage() {
        return lastFrameImage;
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
        if (imageWidth > 2 && imageHeight > 2) {
            paintDelegate.setGraphics(graphics2D);
            imagePanelPaintStrategy.paint(paintDelegate, imageWidth, imageHeight);
        }
        graphics2D.setColor(FRAME_COLOR);
        graphics2D.drawRect(0, 0, imageWidth - 1, imageHeight - 1);
        graphics.drawImage(image, 0, 0, this);
        graphics2D.dispose();
        lastFrameImage = image;
    }
    
}
