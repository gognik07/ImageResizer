import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class Main {
    public static void main(String[] args) {
        String srcFolder = "/users/gognik/Desktop/src";
        String dstFolder = "/users/gognik/Desktop/dst";

        int newWidth = 300;

        File srcDir = new File(srcFolder);

        File[] files = srcDir.listFiles();

        //start

        int countProcessors = Runtime.getRuntime().availableProcessors();

        if (countProcessors >= files.length) {
            for (File file : files) {
                File[] partFiles = new File[1];
                partFiles[0] = file;
                new ImageResizer(partFiles, newWidth, dstFolder).start();
            }
            return;
        }

        int countFilesInStep = files.length / countProcessors;
        for (int i = 0; i < countProcessors - 1; i++) {
            File[] partFiles = new File[countFilesInStep];
            System.arraycopy(files, i * countFilesInStep, partFiles, 0, partFiles.length);
            new ImageResizer(partFiles, newWidth, dstFolder).start();
        }

        int remainderArray = files.length - (countProcessors - 1) * countFilesInStep;
        File[] partFiles = new File[remainderArray];
        System.arraycopy(files, files.length - remainderArray, partFiles, 0, partFiles.length);
        new ImageResizer(partFiles, newWidth, dstFolder).start();

    }
}
