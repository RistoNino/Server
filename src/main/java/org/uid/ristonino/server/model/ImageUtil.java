package org.uid.ristonino.server.model;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;

public class ImageUtil {

    public void copyImage(Path src, Path dest) {
        try {
            Files.copy(src, dest);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String encodeImageToBase64(String imagePath) throws IOException {
        try {

            InputStream is = ImageUtil.class.getResourceAsStream(imagePath);

            if (is == null) {
                throw new IOException("Immagine non trovata " + imagePath);
            }
            BufferedImage img = ImageIO.read(is);

            ByteArrayOutputStream b = new ByteArrayOutputStream();
            ImageIO.write(img, "png", b);
            byte[] imgBytes = b.toByteArray();

            String img64 = Base64.getEncoder().encodeToString(imgBytes);

            return img64;
        }
        catch (IOException e) {
            throw new IOException(e);
        }
    }
}
