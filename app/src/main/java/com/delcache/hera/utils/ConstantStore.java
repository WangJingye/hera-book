package com.delcache.hera.utils;


/**
 * 常量类
 */
public class ConstantStore {
	
	private String appName = "";
	
	private String rootPath = "";
	private String imageCachePath = "";
	private String fileDownloadPath = "";
	
	private int screenWidth = 0;
	private int screenHeight = 0;
	
	private String version = "";
	
	public static final String SP_NAME = "sp_base";
	
	private static ConstantStore _constantStore = null;
	
	public static ConstantStore getInstance() {
		if (_constantStore == null) {
			_constantStore = new ConstantStore();
		}
		return _constantStore;
	}
	
	private ConstantStore(){
		
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getRootPath() {
		return rootPath;
	}

	public void setRootPath(String rootPath) {
		this.rootPath = rootPath;
	}

	public String getImageCachePath() {
		return imageCachePath;
	}

	public void setImageCachePath(String imageCachePath) {
		this.imageCachePath = imageCachePath;
	}

	public String getFileDownloadPath() {
		return fileDownloadPath;
	}

	public void setFileDownloadPath(String fileDownloadPath) {
		this.fileDownloadPath = fileDownloadPath;
	}

	public int getScreenWidth() {
		return screenWidth;
	}

	public void setScreenWidth(int screenWidth) {
		this.screenWidth = screenWidth;
	}

	public int getScreenHeight() {
		return screenHeight;
	}

	public void setScreenHeight(int screenHeight) {
		this.screenHeight = screenHeight;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

}
