package donate.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

public class UploadAndRemoveImage {
	private static final String uploadDir = System.getProperty("user.dir")+"/src/main/resources/static/";
	
	public String uploadImage(MultipartFile file, String src) throws IOException {
		String imageUUID;
		if(!file.isEmpty()) {
			int position = file.getContentType().indexOf("/");
			String type = file.getContentType().substring(position+1);
			imageUUID = UUID.randomUUID()+"."+type;
			Path fileNameAndPath = Paths.get(uploadDir+src, imageUUID);
			
			Files.write(fileNameAndPath, file.getBytes());
		} else {
			imageUUID = "noimage.png";
		}
		return imageUUID;
		
	}
	
	public void deleteImage(String imageName, String src) {
		if(!imageName.equals("noimage.png")) {
		File file = new File(uploadDir+src+imageName);
		if(file.exists()) {
			file.delete();
		}
		}
	}
	
}
