package com.epita.assistants.ping.Frontend.panels;

import com.epita.assistants.ping.Class.MainConfig;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImagePanel extends JPanel {

    BufferedImage image;
    Image scaled;
    boolean followMainWindowSize = true;

    public ImagePanel(File imageFile)
    {
        super(new GridLayout(1, 1));
        if (imageFile == null)
            image = null;
        else {
            try {
                image = ImageIO.read(imageFile);
            } catch (IOException e) {
                image = null;
            }
        }
        scaled = image;
        addComponentListener(new ComponentAdapter() {

            @Override
            public void componentResized(ComponentEvent e) {
                if (!followMainWindowSize)
                    resizeImage(getSize());
            }

        });
    }


    public void setImageToImagePanel(File imageFile){
        if (imageFile == null) {
            image = null;
            scaled = null;
            repaint();
            return;
        }
        try {
            image = ImageIO.read(imageFile);
        } catch (IOException e) {
            image = null;
        }
        resizeImage(getSize());
    }

    public void setFollowMainWindowSize(boolean followMainWindowSize)
    {
        this.followMainWindowSize = followMainWindowSize;
    }

    public boolean getFollowMainWindowSize()
    {
        return followMainWindowSize;
    }

    public void updateImageFromMain(Dimension size)
    {
        if (followMainWindowSize)
            resizeImage(size);
    }

    private void resizeImage(Dimension size){
        if (image != null) {
            Dimension imgDim = new Dimension(image.getWidth(), image.getHeight());
            double imgRatio = imgDim.width / (double) imgDim.height;
            double ratio = size.width / (double) size.height;
            if (imgRatio > ratio) {
                if (followMainWindowSize)
                    size.width = -1;
                else
                    size.height = -1;
            } else {
                if (followMainWindowSize)
                    size.height = -1;
                else
                    size.width = -1;
            }
            scaled = image.getScaledInstance(size.width, size.height, Image.SCALE_FAST);
            repaint();
        }
    }

    public void paintComponent(Graphics graphics)
    {
        super.paintComponent(graphics);

        if (scaled != null) {
            Graphics2D g2 = (Graphics2D) graphics.create();
            Point p2 = getParent().getLocation();
            Point p1 = getLocation();
            g2.drawImage(scaled, -p1.x -p2.x, -p1.y -p2.y, this);
            g2.dispose();
            graphics.setColor(MainConfig.fading);
            graphics.fillRect(0,0, scaled.getWidth(null), scaled.getHeight(null));
        }
        else
        {
            graphics.setColor(MainConfig.fading);
            graphics.fillRect(0,0, getWidth(), getHeight());
        }

    }
}
