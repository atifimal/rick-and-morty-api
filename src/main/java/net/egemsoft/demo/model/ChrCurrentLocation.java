package net.egemsoft.demo.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * Karakterlerin su anki konumunun bilgilerini karsilayan/kaydeden tablo modeli, embeddable.
 * @author Mehmet Atif Imal
 *
 */
@Embeddable
public class ChrCurrentLocation {

	@Column(name = "CURR_LOC_NAME")
	private String name;

	@Column(name = "CURR_LOC_URL")
	private String url;

	public ChrCurrentLocation() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
