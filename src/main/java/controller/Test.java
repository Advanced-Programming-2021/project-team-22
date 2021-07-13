package controller;

import com.talanlabs.avatargenerator.Avatar;
import com.talanlabs.avatargenerator.utils.AvatarUtils;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Random;

public class Test extends Application {

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage stage) {
//        BufferedImage avatar = TriangleAvatar.newAvatarBuilder().build().create("abcvds".hashCode());
//        Image image = convertToFxImage(avatar);
//
//
//        try {
//            File outputfile = new File("src/main/resources/players/saved.png");
//            ImageIO.write(avatar, "png", outputfile);
//        } catch (IOException ignored) {
//        }
//
//        BufferedImage image = null;
//        try {
//            image = ImageIO.read(new File("src/main/resources/players/saved.png"));
//        } catch (IOException ioException) {
//            ioException.printStackTrace();
//        }
//
//        Pane pane = new Pane();
//        pane.getChildren().add(new ImageView(convertToFxImage(image)));
//        Scene scene = new Scene(pane, 500, 500);
//
//        stage.setScene(scene);
//        stage.show();
    }

    public static void showAvatar(Avatar avatar, int w, int h) {
        int size = avatar.getWidth();
        BufferedImage dest = new BufferedImage(size * w, size * h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = dest.createGraphics();
        AvatarUtils.activeAntialiasing(g2);
        Random random = new Random(10);
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                long code = Math.abs(random.nextLong());
                g2.drawImage(avatar.create(code), x * size, y * size, size, size, null);
                System.out.print(code + ", ");
                //g2.setColor(Color.RED);
                //g2.drawRect(x * 128, y * 128, 128, 128);
            }
            System.out.println("");
        }

        g2.dispose();

        showImage(dest);
    }

    public static void showImage(BufferedImage bi) {
        try {
            Path file = Files.createTempFile("img", ".png");
            ImageIO.write(bi, "png", file.toFile());

            Desktop.getDesktop().open(file.toFile());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Image convertToFxImage(BufferedImage image) {
        WritableImage wr = null;
        if (image != null) {
            wr = new WritableImage(image.getWidth(), image.getHeight());
            PixelWriter pw = wr.getPixelWriter();
            for (int x = 0; x < image.getWidth(); x++) {
                for (int y = 0; y < image.getHeight(); y++) {
                    pw.setArgb(x, y, image.getRGB(x, y));
                }
            }
        }

        return new ImageView(wr).getImage();
    }
}