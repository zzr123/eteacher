package com.turing.eteacher.util;

import java.io.File;
import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.turing.eteacher.model.CustomFile;

public class FileUtil {

	public static String getRootPath(){
		return FileUtil.class.getResource("/").getPath().replace("WEB-INF/classes", "");
	}
	
	public static String getFileStorePath(){
		return FileUtil.class.getResource("/").getPath().replace("WEB-INF/classes", "") + "/upload/";
	}
	
	public static String getSuffixes(String fileName){
		String suffixes = "";
		if(StringUtil.isNotEmpty(fileName)&&fileName.indexOf(".")!=-1){
			suffixes = fileName.substring(fileName.lastIndexOf("."));
		}
		return suffixes;
	}
	
	public static CustomFile genCustomFile(MultipartFile file, String dataId) throws IllegalStateException, IOException{
		CustomFile customFile = null;
		if(!file.isEmpty()){
			String pathRoot = FileUtil.getRootPath();
			String serverName = CustomIdGenerator.generateShortUuid() + FileUtil.getSuffixes(file.getOriginalFilename());
	        String path="/upload/"+serverName;
	        file.transferTo(new File(pathRoot+path));
	        customFile = new CustomFile();
	        customFile.setDataId(dataId);
	        customFile.setFileName(file.getOriginalFilename());
	        customFile.setServerName(serverName);
		}
		return customFile;
	}
}
