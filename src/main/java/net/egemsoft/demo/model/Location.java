package net.egemsoft.demo.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Lokasyon bilgilerini karsilayan/kaydeden tablo modeli, entity.
 * 
 * @author Mehmet Atif Imal
 *
 */
@Entity
@Table(name = "location")
public class Location {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "LOC_NAME")
	private String name;

	@Column(name = "LOC_TYPE")
	private String type;

	@Column(name = "LOC_DIM")
	private String dimension;

	// (Hatirlatma) AppMain'de datalar okunurken URL'den ID bilgisi alinarak
	// eklenmisti. Bu entity'deki LOC_URL sutunu icin de ayni sey gecerli.
	// Ilk kayitin ilk item'i icin bu bilgi "38",
	// "https://rickandmortyapi.com/api/character/38" degil.
	@ElementCollection(targetClass = String.class)
	private List<String> residents = new ArrayList<String>();

	@Column(name = "LOC_URL")
	private String url;

	@Column(name = "LOC_CRTD")
	private String created;

	public Location() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDimension() {
		return dimension;
	}

	public void setDimension(String dimension) {
		this.dimension = dimension;
	}

	public List<String> getResidents() {
		return residents;
	}

	public void setResidents(List<String> residents) {
		this.residents = residents;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getCreated() {
		return created;
	}

	public void setCreated(String created) {
		this.created = created;
	}

}
