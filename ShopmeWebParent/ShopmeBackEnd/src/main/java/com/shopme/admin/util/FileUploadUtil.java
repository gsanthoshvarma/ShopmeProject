package com.shopme.admin.util;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.web.multipart.MultipartFile;

public class FileUploadUtil {
	
	
	public static void FileUpload(String filename,String imageDir,MultipartFile multipartFile) throws IOException {
		Path uploadPath = Paths.get(imageDir);
		if(!Files.exists(uploadPath)){
			Files.createDirectory(uploadPath);
		}
		try(InputStream inputStream = multipartFile.getInputStream()){
			Path completeFilePath = uploadPath.resolve(filename);
			Files.copy(inputStream, completeFilePath,StandardCopyOption.REPLACE_EXISTING);
		}catch (IOException e) {
			throw new IOException("Unable upload image into server "+ filename , e);
		}
		
	}

}
