package com.demo.asset.service.models;

import java.time.LocalDateTime;

public class Asset {
	private String id;
	private String name;
	private int type;
	private String description;
	private LocalDateTime created;
	
	public Asset(String id, String name, int type, String description, LocalDateTime created) {
		this.id = id;
		this.name = name;
		this.type = type;
		this.description = description;
		this.created = created;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public LocalDateTime getCreated() {
		return created;
	}
	public void setCreated(LocalDateTime created) {
		this.created = created;
	}
	@Override
	public String toString() {
		return id + "," + name + "," + type + "," + description + "," + created;
	}
}
