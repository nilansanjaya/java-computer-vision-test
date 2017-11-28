import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.videoio.VideoCapture;        

public class DetectFaceFromWebCam extends JPanel{

    BufferedImage image;

    public static void main (String args[]) throws InterruptedException{
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        DetectFaceFromWebCam t = new DetectFaceFromWebCam();
        VideoCapture camera = new VideoCapture(0);
        FaceDetector face = new FaceDetector();

        Mat frame = new Mat();
        camera.read(frame); 

        if(!camera.isOpened()){
            System.out.println("Error");
        }
        else {                  
            while(true){        

                if (camera.read(frame)){

                    BufferedImage image = face.MatToBufferedImage(frame);

                    //t.window(image, "Original Image", 0, 0);

                    t.window(face.detectFace(image), "Face Detected", 40, 60);

                    break;
                }
            }   
        }
        camera.release();
    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(image, 0, 0, this);
    }

    public DetectFaceFromWebCam() {
    }

    public DetectFaceFromWebCam(BufferedImage img) {
        image = img;
    }   

    //Show image on window
    public void window(BufferedImage img, String text, int x, int y) {
        JFrame frame0 = new JFrame();
        frame0.getContentPane().add(new DetectFaceFromWebCam(img));
        frame0.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame0.setTitle(text);
        frame0.setSize(img.getWidth(), img.getHeight() + 30);
        frame0.setLocation(x, y);
        frame0.setVisible(true);
    }

    //Load an image
    public BufferedImage loadImage(String file) {
        BufferedImage img;

        try {
            File input = new File(file);
            img = ImageIO.read(input);

            return img;
        } catch (Exception e) {
            System.out.println("erro");
        }

        return null;
    }

    //Save an image
    public void saveImage(BufferedImage img) {        
        try {
            File outputfile = new File("Images/new.png");
            ImageIO.write(img, "png", outputfile);
        } catch (Exception e) {
            System.out.println("error");
        }
    }

    //Grayscale filter - unused
    public BufferedImage grayscale(BufferedImage img) {
        for (int i = 0; i < img.getHeight(); i++) {
            for (int j = 0; j < img.getWidth(); j++) {
                Color c = new Color(img.getRGB(j, i));

                int red = (int) (c.getRed() * 0.299);
                int green = (int) (c.getGreen() * 0.587);
                int blue = (int) (c.getBlue() * 0.114);

                Color newColor =
                        new Color(
                        red + green + blue,
                        red + green + blue,
                        red + green + blue);

                img.setRGB(j, i, newColor.getRGB());
            }
        }

        return img;
    }
}