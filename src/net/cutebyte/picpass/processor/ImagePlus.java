package net.cutebyte.picpass.processor;

import java.awt.image.BufferedImage;

/**
 * Created by hopskocz on 11.05.15.
 */
public class ImagePlus {
    private BufferedImage image;

    public ImagePlus() {
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public BufferedImage getImage() {
        return image;
    }

    public String extractMsg() {
        int width = image.getWidth(), height = image.getHeight();

        String data = "";

        for (int i = 0; i<width; i++) {
            for (int j = 0; j<height; j++) {
                String[] temporary = String.format("%32s", Integer.toBinaryString(image.getRGB(i,j))).replace(' ', '0').split("(?<=\\G........)");
                data += temporary[1].charAt(temporary[1].length() - 1);
                data += temporary[2].charAt(temporary[2].length() - 1);
                data += temporary[3].charAt(temporary[3].length() - 1);
            }
        }

        String result = binaryToString(data);

        return result;
    }

    public void storeMsg(String msg) {
        int width = image.getWidth(), height = image.getHeight();

        String data = stringToBinary(msg);
        int counter = 0;
        int mask = 0;

        for (int i = 0; i<width; i++) {
            for (int j = 0; j<height; j++) {
                mask = 0;
                for (int w = 2; w>=0; w--) {
                    if (counter < data.length()) {
                        mask |= (Integer.valueOf(data.charAt(counter++)+"") << (8 * w));
                    }
                }
                int temp = (image.getRGB(i,j) & 0b11111111111111101111111011111110) | mask;
                image.setRGB(i,j,temp);
            }
        }
    }

    public String stringToBinary(String input) {
        String output = "";

        byte[] temporary = input.getBytes();
        String temp = "";

        for (int i = 0; i<temporary.length; i++) {
            temp = String.format("%8s", Integer.toBinaryString(temporary[i])).replace(' ', '0');
            output += temp;
        }

        return output;
    }

    public String binaryToString(String input) {
        String output = "";

        String[] temporary = input.split("(?<=\\G........)");

        for (int i = 0; i<temporary.length; i++) {
            output += (char)(Integer.parseInt(temporary[i],2));
        }
        return output;
    }
}
