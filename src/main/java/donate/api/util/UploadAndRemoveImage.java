package donate.api.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import donate.api.exception.BadRequestException;
import donate.store.entity.User;

@Component
public class UploadAndRemoveImage {
	private static final String uploadDir = System.getProperty("user.dir")+"/src/main/resources/static/";
	
	public String uploadImage(MultipartFile file, String src) {
		String imageUUID;
		if(!file.isEmpty()) {
			int position = file.getContentType().indexOf("/");
			String type = file.getContentType().substring(position+1);
			imageUUID = UUID.randomUUID()+"."+type;
			Path fileNameAndPath = Paths.get(uploadDir+src, imageUUID);
			
			try {
				Files.write(fileNameAndPath, file.getBytes());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			imageUUID = "noimage.png";
		}
		return imageUUID;
		
	}
	
	public void deleteImage(String imageName, String src) {
		if(imageName!=null) {
			if(!imageName.equals("noimage.png")) {
				File file = new File(uploadDir+src+imageName);
				if(file.exists()) {
					file.delete();
				}
			}
		}
	}
	
	public byte[] getImage(String imageName, String src) {
		File file = new File(uploadDir+src+imageName);
		Path path = Paths.get(file.toURI());
		try {
			return Files.readAllBytes(path);
		} catch (IOException e) {
			e.printStackTrace();
			throw new BadRequestException();
		}
	}
	
}
