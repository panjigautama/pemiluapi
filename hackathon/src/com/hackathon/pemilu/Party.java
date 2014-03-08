package com.hackathon.pemilu;

public class Party {

	private String name;
	private String fullName;
	private int id;
	private String imageUrl;

	public Party(String name, String fullName, int id, String imageUrl) {
		super();
		this.name = name;
		this.fullName = fullName;
		this.id = id;
		this.imageUrl = imageUrl;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

}
