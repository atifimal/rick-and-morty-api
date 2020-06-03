package net.egemsoft.demo.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Bolum bilgilerini karsilayan/kaydeden tablo modeli, entity.
 * 
 * @author Mehmet Atif Imal
 *
 */
@Entity
public class Episode {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "EPI_NAME")
	private String name;

	@Column(name = "EPI_AIR")
	private String air_date;

	@Column(name = "EPI_EPI")
	private String episode;

	// (Hatirlatma) AppMain'de datalar okunurken URL'den ID bilgisi alinarak
	// eklenmisti. Bu entity'deki EPI_URL sutunu icin de ayni sey gecerli.
	// Ilk kayitin ilk item'i icin bu bilgi "1",
	// "https://rickandmortyapi.com/api/character/1" degil.
	@ElementCollection(targetClass = String.class)
	private List<String> characters = new ArrayList<String>();

	@Column(name = "EPI_URL")
	private String url;

	@Column(name = "EPI_CRTD")
	private String created;

	public Episode() {
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

	public String getAir_date() {
		return air_date;
	}

	public void setAir_date(String air_date) {
		this.air_date = air_date;
	}

	public String getEpisode() {
		return episode;
	}

	public void setEpisode(String episode) {
		this.episode = episode;
	}

	public List<String> getCharacters() {
		return characters;
	}

	public void setCharacters(List<String> characters) {
		this.characters = characters;
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
