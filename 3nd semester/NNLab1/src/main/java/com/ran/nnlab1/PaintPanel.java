package com.ran.nnlab1;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;

public class PaintPanel extends JPanel {

    private boolean[][] matrix = null;
    private BufferedImage lastFrameImage = null;
    private Pair<Integer, Integer> lastPoint = null;
    
    public PaintPanel() {
        initComponents();
        initListeners();
    }

    public BufferedImage getLastFrameImage() {
        return lastFrameImage;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setMaximumSize(new java.awt.Dimension(400, 400));
        setMinimumSize(new java.awt.Dimension(400, 400));
        setPreferredSize(new java.awt.Dimension(400, 400));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
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
        if ((e.getModifiersEx() & InputEvent.BUTTON1_DOWN_MASK) != 0) {
            matrix[e.getY()][e.getX()] = true;
            if (lastPoint != null) {
                List<Pair<Integer, Integer>> list = brezenhem(e.getX(), e.getY(), lastPoint.getFirst(), lastPoint.getSecond());
                list.stream().forEach((point) -> {
                    matrix[point.getSecond()][point.getFirst()] = true;
                });
            }
            lastPoint = new Pair<>(e.getX(), e.getY());
            repaint();
        }
    }
    
    public void clear() {
        int w = matrix[0].length;
        int h = matrix.length;
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                matrix[i][j] = false;
            }
        }
    }
    
    @Override
    protected void paintComponent(Graphics graphics) {
        int w = getWidth();
        int h = getHeight();
        if (matrix == null) {
            matrix = new boolean[h][w];
        }
        
        BufferedImage image = (BufferedImage) createImage(w, h);
        Graphics2D graphics2D = (Graphics2D) image.getGraphics();
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics2D.setColor(Color.WHITE);
        graphics2D.fillRect(0, 0, w - 1, h - 1);
        
        graphics2D.setColor(Color.BLACK);
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                if (matrix[y][x]) {
                    graphics2D.fillRect(x - 2, y - 2, 5, 5);
                }
            }
        }
        
        graphics.drawImage(image, 0, 0, this);
        graphics2D.dispose();
        lastFrameImage = image;
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
