package com.lk.pearson.packaging.service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import com.lk.pearson.converter.util.ConverterHelper;
import com.lk.pearson.domain.Output;

public class PackagingManager {
	
	private static final String tempFileStorageLocationName = "FinalOutput";
	private static final String filePathSeparator = "\\";
	
	public byte[] createZipFile(List<Output> outputList, String tokenId) throws IOException {

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ZipOutputStream zos = new ZipOutputStream(baos);
		ZipEntry entry = new ZipEntry(tokenId + ".zip");

		for (Output output : outputList) {
			String format = output.getOutputFormat().toString().toLowerCase();
			String zipEntryName = output.getOutputFileName()+"."+format;
			entry.setSize(output.getOutputasByteArray().length);
			ZipEntry zipEntry = new ZipEntry(zipEntryName);
			zos.putNextEntry(zipEntry);
			zos.write(output.getOutputasByteArray());
		}
		zos.closeEntry();
		zos.close();

		return baos.toByteArray();
	}
	
	private static void addDirectory(ZipOutputStream zout, String sourceDir, File fileSource) throws IOException {
		if (fileSource.isDirectory()) {
			// Add the directory to the zip entry...
			String path = getRelativePath(sourceDir, fileSource);
			if (path.trim().length() > 0) {
				ZipEntry ze = new ZipEntry(getRelativePath(sourceDir, fileSource));
				zout.putNextEntry(ze);
				zout.closeEntry();
			}

			File[] files = fileSource.listFiles();
			System.out.println("Adding directory " + fileSource.getName());
			for (int i = 0; i < files.length; i++) {
				if (files[i].isDirectory()) {
					addDirectory(zout, sourceDir, files[i]);
				} else {

					System.out.println("Adding file " + files[i].getName());
					byte[] buffer = new byte[1024];
					FileInputStream fin = new FileInputStream(files[i]);
					zout.putNextEntry(new ZipEntry(getRelativePath(sourceDir, files[i])));
					int length;

					while ((length = fin.read(buffer)) > 0) {
						zout.write(buffer, 0, length);
					}
					zout.closeEntry();
					fin.close();
				}
			}
		}
	}

	public static String getRelativePath(String sourceDir, File file) {
		String path = file.getPath().substring(sourceDir.length());
		if (path.startsWith(File.pathSeparator)) {
			path = path.substring(1);
		}
		return path;
	}

}
