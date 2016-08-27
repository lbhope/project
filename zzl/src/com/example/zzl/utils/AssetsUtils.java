package com.example.zzl.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;

public class AssetsUtils {
	public static File copy(Context context, String filename, String dir)
			throws IOException {

		InputStream source = context.getAssets().open(filename);
		File destinationFile = new File(dir, filename);
		if (destinationFile.exists()) {
			destinationFile.delete();
		}
		destinationFile.createNewFile();

		OutputStream destination = new FileOutputStream(destinationFile);
		byte[] buffer = new byte[1024];
		int nread;

		while ((nread = source.read(buffer)) != -1) {
			if (nread == 0) {
				nread = source.read();
				if (nread < 0)
					break;
				destination.write(nread);
				continue;
			}
			destination.write(buffer, 0, nread);
		}
		destination.close();

		return destinationFile;
	}

}
