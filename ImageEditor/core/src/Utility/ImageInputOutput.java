package Utility;

import java.io.FileOutputStream;
import java.io.IOException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.imageeditor.EditWindow;
import com.mygdx.imageeditor.Util;

public class ImageInputOutput {
	public static ImageInputOutput Instance;
	private byte[] _fileHeader;
	private Pixmap _pixels;
	public String ImageFolderLocation;

	public ImageInputOutput() {
		Instance = this;
	}

	public Pixmap loadImage(String filePath) {
		ImageFolderLocation = scrapeFolderLocation(filePath);

		byte[] bytes = Gdx.files.internal(filePath).readBytes();
		int[] fileIntData = Util.unsignBytes(bytes);

		if (fileIntData[0] != 'B' || fileIntData[1] != 'M') {
			System.out.println(filePath + " is NOT a bitmap image");
			return null;
		}

		int startPoint = Util
				.bytesToInt(new int[] { fileIntData[10], fileIntData[11], fileIntData[12], fileIntData[13] });
		_fileHeader = new byte[startPoint];
		System.arraycopy(bytes, 0, _fileHeader, 0, startPoint);
		int width = Util.bytesToInt(new int[] { fileIntData[18], fileIntData[19], fileIntData[20], fileIntData[21] });
		int height = Util.bytesToInt(new int[] { fileIntData[22], fileIntData[23], fileIntData[24], fileIntData[25] });
		int bytesPerPixel = Util.bytesToInt(new int[] { fileIntData[28], fileIntData[29] }) / 8;

		if (bytesPerPixel != 3) {
			System.out.println("Unsupported image pixel format. Incorrect bits per pixel");
			return null;
		}

		Pixmap pixels = new Pixmap(width, height, Format.RGBA8888);
		new Pixmap(width, height, Format.RGBA8888);
		int x = 0, y = height;

		for (int i = startPoint; i < fileIntData.length - 2; i += 3) {
			int b = fileIntData[i];
			int g = fileIntData[i + 1];
			int r = fileIntData[i + 2];

			float normalizedR = (r & 0xFF) / 255f;
			float normalizedG = (g & 0xFF) / 255f;
			float normalizedB = (b & 0xFF) / 255f;

			pixels.setColor(normalizedR, normalizedG, normalizedB, 1);
			pixels.drawPixel(x, y - 1);

			x++;
			if (x >= width) {
				x = 0;
				y--;
			}
		}
		_pixels = pixels;
		return pixels;
	}

	public void saveImage(String filePath) throws IOException {
		FileOutputStream output = new FileOutputStream(filePath);
		byte[] color;
		byte[] colorData = new byte[_pixels.getWidth() * _pixels.getHeight() * 3];
		 Pixmap doodle = Util.scalePixmap( EditWindow.Instance.DoodleMap, new
		 Vector2(_pixels.getWidth(), _pixels.getHeight()));
		int colorIndex = 0;
		for (int y = _pixels.getHeight() - 1; y >= 0; y--) {
			for (int x = 0; x < _pixels.getWidth(); x++) {
				color = Util.intToSignedBytes(_pixels.getPixel(x, y));
				colorData[colorIndex] = color[2];
				colorData[colorIndex + 1] = color[1];
				colorData[colorIndex + 2] = color[0];
				colorIndex += 3;
			}
		}

		Pixmap doodle1 = EditWindow.Instance.DoodleMap;
		colorIndex = 0;
		for (int y = doodle1.getHeight() - 1; y >= 0; y--) {
			for (int x = 0; x < doodle1.getWidth(); x++) {
				color = Util.intToSignedBytes(doodle1.getPixel(x, y));
				if(color[3] != -1) {colorIndex += 3; continue; }
				colorData[colorIndex] = color[2];
				colorData[colorIndex + 1] = color[1];
				colorData[colorIndex + 2] = color[0];
				colorIndex += 3;
			}
		}


		
		output.write(_fileHeader);
		output.write(colorData);
		output.close();
	}
	
	private String scrapeFolderLocation(String filePath) {
		StringBuilder builder = new StringBuilder(filePath);
		for(int i = filePath.length() - 1; i >= 0; i--) {
		if(filePath.charAt(i) != '\\') continue;
		return builder.substring(0,i);
		}
		return null;
		}

	

}
