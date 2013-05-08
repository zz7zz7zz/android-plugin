/**
 * 
 */
package com.open.androidplugin.plugin;

import java.io.Serializable;

/**
 * Represents a plugin
 * @author yanglonghui
 *
 */
public final class PluginBean implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String id;//插件的ID号
	private String authorID;//插件的作者
	private String typeID;//插件的类型
    private String name;//插件的名字
    private String icon;//插件的图标
    private String description;//插件描述
    private String updateTime;//插件最后更新的时间
    private String signature;//插件的签名
    private String version="";//插件的版本
    
    private String path;//插件的路径(本地)
    private String fileName;//插件的文件名
    private String className;//插件的类名
    
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getAuthorID() {
		return authorID;
	}
	public void setAuthorID(String authorID) {
		this.authorID = authorID;
	}
	public String getTypeID() {
		return typeID;
	}
	public void setTypeID(String typeID) {
		this.typeID = typeID;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	public String getSignature() {
		return signature;
	}
	public void setSignature(String signature) {
		this.signature = signature;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
