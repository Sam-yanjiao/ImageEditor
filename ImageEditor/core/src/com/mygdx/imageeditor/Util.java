package com.mygdx.imageeditor;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.math.Vector2;

public class Util {
	private static final int targetWidth = 0;
	private static final Pixmap sourceImage = null;
	public static int bytesToInt(int[] js) {
		int result = 0;
		for(int i = 0; i < js.length; i++) {
		result += (int) js[i] << (8 * i);
		}
		return result;
		}
	public static int[] unsignBytes(byte[] bytes) {
		int[] ints = new int[bytes.length];
		for(int i = 0; i < bytes.length; i++) {
			ints[i] = bytes[i] < 0 ? bytes[i] + 256 : bytes[i];
		}
		return ints;
		}
	public static byte[] intToSignedBytes(int value) {
		byte[] result = new byte[4];
		result[0] =(byte) (value >> 24);
		result[1] = (byte) ((value << 8) >> 24);
		result[2] = (byte) ((value << 16)>> 24);
		result[3] = (byte) ((value << 24)>> 24);
		return result;
	}
	public static void testIntToSignedBytes() {
		byte[] testResults = intToSignedBytes(543152314);
		int[] expectedResults = {32, 95, -40, -70};
		for(int i = 0; i < testResults.length; i++) {
		if((int) testResults[i] != expectedResults[i])
		System.out.println("TEST FAILED! INDEX " + i + " IS "
		+ testResults[i] + " EXPECTED: " + expectedResults[i]);
		}
		}
	public static Pixmap scalePixmap(Pixmap map, Vector2 desiredSize) {
		Pixmap newMap = new Pixmap(targetWidth, targetWidth, Pixmap.Format.RGBA8888);

		for (int x = 0; x < newMap.getWidth(); x++) {
		    for (int y = 0; y < newMap.getHeight(); y++) {		       
		        int sourceX = Math.round((float)x / newMap.getWidth() * targetWidth);
		        int sourceY = Math.round((float)y / newMap.getHeight() * targetWidth);

		        int color = sourceImage.getPixel(sourceX, sourceY);

		        newMap.drawPixel(x, y, color);
		    }
		}

		return newMap;
	}
}
