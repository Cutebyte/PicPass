package net.cutebyte.picpass.processor;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * Created by hopskocz on 02.05.15.
 */
public class ImagePanel extends JPanel {
    ImagePlus image;

    public ImagePanel() {
        super();
        image = new ImagePlus();
        setLayout(null);
    }

    public ImagePanel(String imagePath) {
        super();
        setLayout(null);
        try {
            this.image.setImage(ImageIO.read(new File(imagePath)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setImage(String imagePath) {
        try {
            this.image.setImage(ImageIO.read(new File(imagePath)));
            this.repaint();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveImage(String imagePath) {
        try {
            ImageIO.write(image.getImage(), "png", new File(imagePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (image != null)
            g.drawImage(image.getImage(), 0, 0, getWidth(), getHeight(), this);
    }

    public ImagePlus getImage() {
        return image;
    }
}