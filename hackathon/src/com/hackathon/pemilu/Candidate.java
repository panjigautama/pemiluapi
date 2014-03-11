package com.hackathon.pemilu;


public class Candidate {

	private String id;
	private int tahun;
	private String lembaga;
	private String nama;
	private String jenis_kelamin;
	private String agama;
	private String tempat_lahir;
	private String tanggal_lahir;
	private String status_perkawinan;
	private String nama_pasangan;
	private String jumlah_anak;
	private String kelurahan_tinggal;
	private String kecamatan_tinggal;
	private String kab_kota_tinggal;
	private String provinsi_tinggal;
	private String foto_url;
	private int urutan;
	// private ArrayList<riwayat_organisasi> riwayat_organisasi;
	// private ArrayList<riwayat_pekerjaan> riwayat_pekerjaan;
	// private ArrayList<riwayat_pendidikan> riwayat_pendidikan;
	private provinsi provinsi;
	private dapil dapil;
	private partai partai;

	public Candidate() {

	}

	// public Candidate(String id, int tahun, String lembaga, String nama,
	// String jenis_kelamin, String agama, String tempat_lahir,
	// String tanggal_lahir, String status_perkawinan,
	// String nama_pasangan, String jumlah_anak, String kelurahan_tinggal,
	// String kecamatan_tinggal, String kab_kota_tinggal,
	// String provinsi_tinggal, String foto_url, int urutan) {
	// super();
	// this.id = id;
	// this.tahun = tahun;
	// this.lembaga = lembaga;
	// this.nama = nama;
	// this.jenis_kelamin = jenis_kelamin;
	// this.agama = agama;
	// this.tempat_lahir = tempat_lahir;
	// this.tanggal_lahir = tanggal_lahir;
	// this.status_perkawinan = status_perkawinan;
	// this.nama_pasangan = nama_pasangan;
	// this.jumlah_anak = jumlah_anak;
	// this.kelurahan_tinggal = kelurahan_tinggal;
	// this.kecamatan_tinggal = kecamatan_tinggal;
	// this.kab_kota_tinggal = kab_kota_tinggal;
	// this.provinsi_tinggal = provinsi_tinggal;
	// this.foto_url = foto_url;
	// this.urutan = urutan;
	// }

	// private static class riwayat_pendidikan {
	// private int id;
	// private String ringkasan;
	// }
	//
	// private static class riwayat_pekerjaan {
	// private int id;
	// private String ringkasan;
	// }
	//
	// private static class riwayat_organisasi {
	// private int id;
	// private String ringkasan;
	// }

	private static class provinsi {
		private int id;
		private String nama;
	}

	private static class dapil {
		private String id;
		private String nama;
	}

	private static class partai {
		private String id;
		private String nama;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getTahun() {
		return tahun;
	}

	public void setTahun(int tahun) {
		this.tahun = tahun;
	}

	public String getLembaga() {
		return lembaga;
	}

	public void setLembaga(String lembaga) {
		this.lembaga = lembaga;
	}

	public String getNama() {
		return nama;
	}

	public void setNama(String nama) {
		this.nama = nama;
	}

	public String getJenis_kelamin() {
		return jenis_kelamin;
	}

	public void setJenis_kelamin(String jenis_kelamin) {
		this.jenis_kelamin = jenis_kelamin;
	}

	public String getAgama() {
		return agama;
	}

	public void setAgama(String agama) {
		this.agama = agama;
	}

	public String getTempat_lahir() {
		return tempat_lahir;
	}

	public void setTempat_lahir(String tempat_lahir) {
		this.tempat_lahir = tempat_lahir;
	}

	public String getTanggal_lahir() {
		return tanggal_lahir;
	}

	public void setTanggal_lahir(String tanggal_lahir) {
		this.tanggal_lahir = tanggal_lahir;
	}

	public String getStatus_perkawinan() {
		return status_perkawinan;
	}

	public void setStatus_perkawinan(String status_perkawinan) {
		this.status_perkawinan = status_perkawinan;
	}

	public String getNama_pasangan() {
		return nama_pasangan;
	}

	public void setNama_pasangan(String nama_pasangan) {
		this.nama_pasangan = nama_pasangan;
	}

	public String getJumlah_anak() {
		return jumlah_anak;
	}

	public void setJumlah_anak(String jumlah_anak) {
		this.jumlah_anak = jumlah_anak;
	}

	public String getKelurahan_tinggal() {
		return kelurahan_tinggal;
	}

	public void setKelurahan_tinggal(String kelurahan_tinggal) {
		this.kelurahan_tinggal = kelurahan_tinggal;
	}

	public String getKecamatan_tinggal() {
		return kecamatan_tinggal;
	}

	public void setKecamatan_tinggal(String kecamatan_tinggal) {
		this.kecamatan_tinggal = kecamatan_tinggal;
	}

	public String getKab_kota_tinggal() {
		return kab_kota_tinggal;
	}

	public void setKab_kota_tinggal(String kab_kota_tinggal) {
		this.kab_kota_tinggal = kab_kota_tinggal;
	}

	public String getProvinsi_tinggal() {
		return provinsi_tinggal;
	}

	public void setProvinsi_tinggal(String provinsi_tinggal) {
		this.provinsi_tinggal = provinsi_tinggal;
	}

	public String getFoto_url() {
		return foto_url;
	}

	public void setFoto_url(String foto_url) {
		this.foto_url = foto_url;
	}

	public int getUrutan() {
		return urutan;
	}

	public void setUrutan(int urutan) {
		this.urutan = urutan;
	}

	// public ArrayList<riwayat_organisasi> getRiwayat_organisasi() {
	// return riwayat_organisasi;
	// }
	//
	// public void setRiwayat_organisasi(
	// ArrayList<riwayat_organisasi> riwayat_organisasi) {
	// this.riwayat_organisasi = riwayat_organisasi;
	// }
	//
	// public ArrayList<riwayat_pekerjaan> getRiwayat_pekerjaan() {
	// return riwayat_pekerjaan;
	// }
	//
	// public void setRiwayat_pekerjaan(
	// ArrayList<riwayat_pekerjaan> riwayat_pekerjaan) {
	// this.riwayat_pekerjaan = riwayat_pekerjaan;
	// }
	//
	// public ArrayList<riwayat_pendidikan> getRiwayat_pendidikan() {
	// return riwayat_pendidikan;
	// }
	//
	// public void setRiwayat_pendidikan(
	// ArrayList<riwayat_pendidikan> riwayat_pendidikan) {
	// this.riwayat_pendidikan = riwayat_pendidikan;
	// }

	// public ArrayList<provinsi> getProvinsi() {
	// return provinsi;
	// }
	//
	// public void setProvinsi(ArrayList<provinsi> provinsi) {
	// this.provinsi = provinsi;
	// }
	//
	// public ArrayList<dapil> getDapil() {
	// return dapil;
	// }
	//
	// public void setDapil(ArrayList<dapil> dapil) {
	// this.dapil = dapil;
	// }
	//
	// public ArrayList<partai> getPartai() {
	// return partai;
	// }
	//
	// public void setPartai(ArrayList<partai> partai) {
	// this.partai = partai;
	// }

}
