import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.WritableImage;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;

/**
 * Created by hp on 10-04-2017.
 */
public class ImageHelper {

    public static final int SMALL_ICON=0;
    public static final int BIG_ICON=1;

    public static WritableImage getIcon(FileTreeItem fileItem, int size) {
        if (size==SMALL_ICON)return getSmallIcon(fileItem);
        else if(size==BIG_ICON) return getBigIcon(fileItem);
        return null;
    }

    private static WritableImage getSmallIcon(FileTreeItem fileItem) {
        ImageIcon icon = (ImageIcon) FileSystemView.getFileSystemView().getSystemIcon(fileItem.getFile());
        java.awt.Image image = icon.getImage();
        BufferedImage bufferedImage = ImageHelper.toBufferedImage(image);
        return SwingFXUtils.toFXImage(bufferedImage, null);
    }
    private static WritableImage getBigIcon(FileTreeItem fileItem) {
        ImageIcon icon=null;
        try {
            icon = new ImageIcon(sun.awt.shell.ShellFolder.getShellFolder( fileItem.getFile() ).getIcon( true ) );

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        java.awt.Image image = icon.getImage();
        BufferedImage bufferedImage = ImageHelper.toBufferedImage(image);
        return SwingFXUtils.toFXImage(bufferedImage, null);
    }
    private static BufferedImage toBufferedImage(Image img)
    {
        if (img instanceof BufferedImage)
        {
            return (BufferedImage) img;
        }

        // Create a buffered image with transparency
        BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

        // Draw the image on to the buffered image
        Graphics2D bGr = bimage.createGraphics();
        bGr.drawImage(img, 0, 0, null);
        bGr.dispose();

        // Return the buffered image
        return bimage;
    }
}
