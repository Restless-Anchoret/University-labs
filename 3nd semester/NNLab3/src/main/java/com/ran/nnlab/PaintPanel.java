package com.ran.nnlab;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;

public class PaintPanel extends JPanel {

    private static final int SIDE_LENGTH = 300;
    private static final int PIXEL_BLOCK_SIDE = PictureConverter.PIXEL_BLOCK_SIDE;
    private static final int PIXEL_AROUND = 1;
    
    private BufferedImage image = null;
    private Pair<Integer, Integer> lastPoint = null;
    
    public PaintPanel() {
        initComponents();
        initListeners();
    }
    
    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public BufferedImage getImage() {
        return image;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setMaximumSize(new java.awt.Dimension(300, 300));
        setMinimumSize(new java.awt.Dimension(300, 300));
        setPreferredSize(new java.awt.Dimension(300, 300));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables

    private void initListeners() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                lastPoint = null;
                savePixel(e);
            }
        });
        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                savePixel(e);
            }
        });
    }
    
    private void savePixel(MouseEvent e) {
        if (e.getX() < 0 || e.getX() >= SIDE_LENGTH ||
                e.getY() < 0 || e.getY() >= SIDE_LENGTH) {
            return;
        }
        
        Color color;
        if ((e.getModifiersEx() & InputEvent.BUTTON1_DOWN_MASK) != 0) {
            color = Color.BLACK;
        } else if ((e.getModifiersEx() & InputEvent.BUTTON3_DOWN_MASK) != 0) {
            color = Color.WHITE;
        } else {
            return;
        }
        
        Graphics2D graphics = image.createGraphics();
        graphics.setColor(color);
        
        int x = e.getX() / PIXEL_BLOCK_SIDE;
        int y = e.getY() / PIXEL_BLOCK_SIDE;
        
        if (lastPoint != null) {
            List<Pair<Integer, Integer>> list = brezenhem(x, y,
                    lastPoint.getFirst(), lastPoint.getSecond());
            list.stream().forEach((point) -> {
                graphics.fillRect(
                        (point.getFirst() - PIXEL_AROUND) * PIXEL_BLOCK_SIDE,
                        (point.getSecond() - PIXEL_AROUND) * PIXEL_BLOCK_SIDE,
                        PIXEL_BLOCK_SIDE * (PIXEL_AROUND * 2 + 1),
                        PIXEL_BLOCK_SIDE * (PIXEL_AROUND * 2 + 1));
            });
        }
        lastPoint = new Pair<>(x, y);
        repaint();
    }
    
    @Override
    protected void paintComponent(Graphics graphics) {
        if (image != null) {
            graphics.drawImage(image, 0, 0, this);
        }
    }
    
    private List<Pair<Integer, Integer>> brezenhem(int x1, int y1, int x2, int y2) {
        int x = x1;
        int y = y1;

        int deltaX = Math.abs(x2 - x1);
        int deltaY = Math.abs(y2 - y1);

        int signX = (int)Math.signum(x2 - x1);
        int signY = (int)Math.signum(y2 - y1);

        int h = 0;
        boolean eLessThenHalf = true;
        List<Pair<Integer, Integer>> result = new ArrayList<>();
        result.add(new Pair<>(x, y));

        if (deltaY < deltaX) {
            for (int k = 1; k <= deltaX; k++) {
                if (!eLessThenHalf) {
                    h++;
                }
                eLessThenHalf = (2 * k * deltaY - (2 * h + 1) * deltaX <= 0);
                x += signX;
                if (!eLessThenHalf) {
                    y += signY;
                }
                result.add(new Pair<>(x, y));
            }
        } else {
            for (int k = 1; k <= deltaY; k++) {
                if (!eLessThenHalf) {
                    h++;
                }
                eLessThenHalf = (2 * k * deltaX - (2 * h + 1) * deltaY <= 0);
                y += signY;
                if (!eLessThenHalf) {
                    x += signX;
                }
                result.add(new Pair<>(x, y));
            }
        }
        return result;
    }
    
}
