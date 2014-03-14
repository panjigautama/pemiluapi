package com.hackathon.pemilu;

public class Area {

	private String kind;
	private String id;
	private String nama;
	private String lembaga;

	public Area(String kind, String id, String nama, String lembaga) {
		super();
		this.kind = kind;
		this.id = id;
		this.nama = nama;
		this.lembaga = lembaga;
	}

	public String getKind() {
		return kind;
	}

	public void setKind(String kind) {
		this.kind = kind;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNama() {
		return nama;
	}

	public void setNama(String nama) {
		this.nama = nama;
	}

	public String getLembaga() {
		return lembaga;
	}

	public void setLembaga(String lembaga) {
		this.lembaga = lembaga;
	}

}
