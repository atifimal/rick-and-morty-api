package net.egemsoft.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import net.egemsoft.demo.model.Location;
import net.egemsoft.demo.repository.LocationRepository;

/**
 * Karakter bilgileri ve endpoint'iyle ilgilenen service katmani
 * 
 * @author Mehmet Atif Imal
 *
 */
@Service
public class LocationService {

	@Autowired
	private LocationRepository locationRepository;

	public LocationService(LocationRepository locationRepository) {
		this.locationRepository = locationRepository;
	}

	public Iterable<Location> save(List<Location> locations) {
		return locationRepository.saveAll(locations);
	}

	public Optional<Location> findById(Long id) {
		return locationRepository.findById(id);
	}

	/**
	 * Lokasyonlari istenilen bilgilere gore paginate eden ve controller'a ileten
	 * servis
	 * 
	 * @param page Hedeflenen sayfa numarasi
	 * @param size Bir sayfada olmasi gereken kayit sayisi
	 * @param sort Siralama olcutu
	 * @return Datalar Controller'a aktariliyor.
	 */
	public Page<Location> getAllLocations(Integer page, Integer size, String sort) {
		Pageable paging = PageRequest.of(page - 1, size, Sort.by(sort));
		Page<Location> pagedResult = locationRepository.findAll(paging);
		return pagedResult;
	}
}
