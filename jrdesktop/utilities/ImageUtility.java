package jrdesktop.utilities;

import java.awt.image.*;
import java.io.*;
import java.util.Iterator;

import javax.imageio.*;
import javax.imageio.stream.FileImageOutputStream;

/**
 * ImageUtility.java
 * 
 * @author benbac
 */

public class ImageUtility {

	static {
		ImageIO.setUseCache(false);
	}

	public static BufferedImage read(InputStream in) throws IOException {
		BufferedImage image = null;
		image = ImageIO.read(in);
		if (image == null)
			throw new IOException("Read fails");
		return image;
	}

	public static BufferedImage read(byte[] bytes) {
		try {
			return read(new ByteArrayInputStream(bytes));
		} catch (IOException e) {
			e.getStackTrace();
			return null;
		}
	}

	public static byte[] toByteArray(BufferedImage image) {
		try {
			// ByteArrayOutputStream out = new ByteArrayOutputStream();
			// ImageIO.write(image, "jpeg", out); // write without compression

			
			// image compress code added
			Iterator<ImageWriter> i = ImageIO.getImageWritersByFormatName("jpeg");

			// Just get the first JPEG writer available
			ImageWriter jpegWriter = i.next();

			// Set the compression quality to 0.5
			ImageWriteParam param = jpegWriter.getDefaultWriteParam();
			param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
			param.setCompressionQuality(0.5f);

			// Write the image to a file
			FileImageOutputStream outcomp = new FileImageOutputStream(new File("image.jpg"));
			jpegWriter.setOutput(outcomp);
			jpegWriter.write(null, new IIOImage(image, null, null), param);
			jpegWriter.dispose();
			outcomp.close();

			ByteArrayOutputStream out = new ByteArrayOutputStream();
			ImageIO.write(image, "jpeg", out); // write without compression

			return out.toByteArray();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
