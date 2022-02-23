package donate.api.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping
@CrossOrigin("*")
public class WidgetMusicImageController {

	private static final String uploadDir = System.getProperty("user.dir")+"/src/main/resources/static/widget/";

	public static final String GET_WIDGET_IMAGE_BY_IMAGE_NAME = "/api/widget/image/{imageName}"; 
	public static final String GET_WIDGET_MUSIC_BY_MUSIC_NAME = "/api/widget/music/{musicName}"; 
	
	@GetMapping(GET_WIDGET_IMAGE_BY_IMAGE_NAME)
	public byte[] getImageWidget(@PathVariable("imageName") String imageName) throws IOException{
		File file = new File(uploadDir+imageName);
		Path path = Paths.get(file.toURI());
		return Files.readAllBytes(path);
	}
	@GetMapping(GET_WIDGET_MUSIC_BY_MUSIC_NAME)
	public byte[] getMusicWidget(@PathVariable("musicName") String musicName) throws IOException{
		System.out.println(musicName);
		File file = new File(uploadDir+"music/"+musicName);
		Path path = Paths.get(file.toURI());
		return Files.readAllBytes(path);
	}
	
	
}
