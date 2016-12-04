package users;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import users.models.User;
import users.services.UsersService;

@Controller
public class FileController {
	private static final Logger logger = LoggerFactory
			.getLogger(FileController.class);
	
	 private static final String TOMCAT_HOME_PROPERTY = "catalina.home";
	 private static final String TOMCAT_HOME_PATH = System.getProperty(TOMCAT_HOME_PROPERTY);
	 private static final String AVATAR_PATH = TOMCAT_HOME_PATH + File.separator + "uploadedFiles" + File.separator + "avatars";
	 
	 @Autowired
	private UsersService usersService;

	/**
	 * Upload single file using Spring Controller
	 */
	@RequestMapping(value = "/uploadAvatar", method = RequestMethod.POST)
	public String uploadAvatar(@RequestParam("filename") String name,
			@RequestParam("imgfile") MultipartFile file,HttpSession session) {
		String fileExt=file.getOriginalFilename().split("\\.")[1];
		
		if (!file.isEmpty()) {
			try {
				//Read Uploaded File Content
				byte[] bytes = file.getBytes();
				
				// Creating the directory to store file
				File dir = new File(AVATAR_PATH);
				if (!dir.exists())
					dir.mkdirs();

				// Create the file on server
				File serverFile = new File(dir.getAbsolutePath()
						+ File.separator + name+"."+fileExt);
				BufferedOutputStream stream = new BufferedOutputStream(
						new FileOutputStream(serverFile));
				stream.write(bytes);
				stream.close();

				logger.info("Server File Location="
						+ serverFile.getAbsolutePath());

				/*return "You successfully uploaded file="  + name+"."+fileExt + ". Server File Location="
						+ serverFile.getAbsolutePath();*/
				//String l=(String) session.getAttribute("userLogin");
				User u = User.getCurrentUser(); //(User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	        	if(u!=null){
	        		//User u=usersService.getUserByLogin(l);
	        		u.setHasAvatar(true);
	        		return "redirect:/user-profile/"+u.getLogin();
	        	}
	        	else{
	        		return "redirect:/login";
	        	}
			} catch (Exception e) {
				return "You failed to upload " + name+"."+fileExt + " => " + e.getMessage();
			}
		} else {
			return "You failed to upload " + name+"."+fileExt
					+ " because the file was empty.";
		}
	}
	
	@RequestMapping(value = "/image/{imageName}")
	@ResponseBody
    public byte[] getImage(@PathVariable(value = "imageName") String imageName,HttpSession session) throws IOException {
		File dir = new File(AVATAR_PATH);
		if (!dir.exists())
			dir.mkdirs();
        File serverFile = new File(dir.getAbsolutePath() + File.separator + imageName + ".jpg");
        if(serverFile.exists()){ 
        	
        	return Files.readAllBytes(serverFile.toPath());
        }
        
        else return null;
    }

	
	@RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
	public @ResponseBody
	String uploadFileHandler(@RequestParam("filename") String name,
			@RequestParam("imgfile") MultipartFile file) {
		String fileExt=file.getOriginalFilename().split("\\.")[1];
		if (!file.isEmpty()) {
			try {
				//Read Uploaded File Content
				byte[] bytes = file.getBytes();
				
				// Creating the directory to store file
				String rootPath = System.getProperty("catalina.home");
				File dir = new File(rootPath + File.separator + "uploadedFiles" + File.separator + "avatars");
				if (!dir.exists())
					dir.mkdirs();

				// Create the file on server
				File serverFile = new File(dir.getAbsolutePath()
						+ File.separator + name+"."+fileExt);
				BufferedOutputStream stream = new BufferedOutputStream(
						new FileOutputStream(serverFile));
				stream.write(bytes);
				stream.close();

				logger.info("Server File Location="
						+ serverFile.getAbsolutePath());

				return "You successfully uploaded file="  + name+"."+fileExt + ". Server File Location="
						+ serverFile.getAbsolutePath();
			} catch (Exception e) {
				return "You failed to upload " + name+"."+fileExt + " => " + e.getMessage();
			}
		} else {
			return "You failed to upload " + name+"."+fileExt
					+ " because the file was empty.";
		}
	}
	
	
	///////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Upload multiple file using Spring Controller
	 */
	/*
	@RequestMapping(value = "/uploadMultipleFile", method = RequestMethod.POST)
	public @ResponseBody
	String uploadMultipleFileHandler(@RequestParam("name") String[] names,
			@RequestParam("file") MultipartFile[] files) {

		if (files.length != names.length)
			return "Mandatory information missing";

		String message = "";
		for (int i = 0; i < files.length; i++) {
			MultipartFile file = files[i];
			String name = names[i];
			try {
				byte[] bytes = file.getBytes();

				// Creating the directory to store file
				String rootPath = System.getProperty("catalina.home");
				File dir = new File(rootPath + File.separator + "tmpFiles");
				if (!dir.exists())
					dir.mkdirs();

				// Create the file on server
				File serverFile = new File(dir.getAbsolutePath()
						+ File.separator + name);
				BufferedOutputStream stream = new BufferedOutputStream(
						new FileOutputStream(serverFile));
				stream.write(bytes);
				stream.close();

				logger.info("Server File Location="
						+ serverFile.getAbsolutePath());

				message = message + "You successfully uploaded file=" + name
						+ "";
			} catch (Exception e) {
				return "You failed to upload " + name + " => " + e.getMessage();
			}
		}
		return message;
	}
	*/

}
