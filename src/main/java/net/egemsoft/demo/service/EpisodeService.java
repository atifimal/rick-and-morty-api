package net.egemsoft.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import net.egemsoft.demo.model.Episode;
import net.egemsoft.demo.repository.EpisodeRepository;

/**
 * Bolum bilgileri ve endpoint'iyle ilgilenen service katmani
 * 
 * @author Mehmet Atif Imal
 *
 */
@Service
public class EpisodeService {

	@Autowired
	private EpisodeRepository episodeRepository;

	public EpisodeService(EpisodeRepository episodeRepository) {
		this.episodeRepository = episodeRepository;
	}

	public Iterable<Episode> save(List<Episode> episodes) {
		return episodeRepository.saveAll(episodes);
	}

	public Optional<Episode> findById(Long id) {
		return episodeRepository.findById(id);
	}

	/**
	 * Bolumleri istenilen bilgilere gore paginate eden ve controller'a ileten servis
	 * @param page Hedeflenen sayfa numarasi
	 * @param size Bir sayfada olmasi gereken kayit sayisi
	 * @param sort Siralama olcutu
	 * @return Datalar Controller'a aktariliyor.
	 */
	public Page<Episode> getAllEpisodes(Integer page, Integer size, String sort) {
		// Istenilen sayfa, boyut ve siralama bilgilerine gore paging olusturarak ilgili Page'i donuyor.
		// Ilk sayfa index'i sifir oldugu icin - 1 islemi yapildi.
		Pageable paging = PageRequest.of(page - 1, size, Sort.by(sort));
		Page<Episode> pagedResult;
		
		// Siralamanin karaktere gore olmasi istendiginde, repository'de manuel olarak olusturulan
		// query calisiyor ve o tablo uzerinden islem yaparak donuyor.
		if (sort.equals("character")) {
			Pageable pagingReady = PageRequest.of(page - 1, size);
			pagedResult = episodeRepository.findAllOrderByCharacterCount(pagingReady);
		} else
			pagedResult = episodeRepository.findAll(paging);

		return pagedResult;
	}
}
