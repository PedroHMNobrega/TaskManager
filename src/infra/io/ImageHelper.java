package infra.io;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class ImageHelper
{
    public BufferedImage getIconImage(String path)
    {
        BufferedImage img = null;
        try {
            img = ImageIO.read(getClass().getResource(path));
        } catch (Exception e) {
            System.out.println("Erro ao abrir o icone");
        }
        return img;
    }
}