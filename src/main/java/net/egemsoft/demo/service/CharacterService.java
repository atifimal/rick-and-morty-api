package net.egemsoft.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import net.egemsoft.demo.model.Character;
import net.egemsoft.demo.repository.CharacterRepository;

/**
 * Karakter bilgileri ve endpoint'iyle ilgilenen service katmani
 * 
 * @author Mehmet Atif Imal
 *
 */
@Service
public class CharacterService {

	@Autowired
	private CharacterRepository characterRepository;

	public CharacterService(CharacterRepository characterRepository) {
		this.characterRepository = characterRepository;
	}

	public Iterable<Character> save(List<Character> characters) {
		return characterRepository.saveAll(characters);
	}

	public Optional<Character> findById(Long id) {
		return characterRepository.findById(id);
	}

	/**
	 * Karakterleri istenilen bilgilere gore paginate eden ve controller'a ileten
	 * servis
	 * 
	 * @param page Hedeflenen sayfa numarasi
	 * @param size Bir sayfada olmasi gereken kayit sayisi
	 * @param sort Siralama olcutu
	 * @return Datalar Controller'a aktariliyor.
	 */
	public Page<Character> getAllCharacters(Integer page, Integer size, String sort) {
		// Istenilen sayfa, boyut ve siralama bilgilerine gore paging olusturarak ilgili
		// Page'i donuyor.
		// Ilk sayfa index'i sifir oldugu icin - 1 islemi yapildi.
		Pageable paging = PageRequest.of(page - 1, size, Sort.by(sort));
		Page<Character> pagedResult;

		// Siralamanin bolume gore olmasi istendiginde, repository'de manuel olarak
		// olusturulan
		// query calisiyor ve o tablo uzerinden islem yaparak donuyor.
		if (sort.equals("episode")) {
			Pageable pagingReady = PageRequest.of(page - 1, size);
			pagedResult = characterRepository.findAllOrderByEpisodeCount(pagingReady);
		} else
			pagedResult = characterRepository.findAll(paging);

		return pagedResult;
	}

}
