package net.egemsoft.demo.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import net.egemsoft.demo.model.Character;
import net.egemsoft.demo.model.Episode;
import net.egemsoft.demo.model.Location;
import net.egemsoft.demo.model.Report;
import net.egemsoft.demo.service.CharacterService;
import net.egemsoft.demo.service.EpisodeService;
import net.egemsoft.demo.service.LocationService;

/**
 * @author Mehmet Atif Imal
 *
 */
@Controller
public class WebController extends Thread {

	@Autowired
	CharacterService characterService;

	@Autowired
	EpisodeService episodeService;

	@Autowired
	LocationService locationService;

	/**
	 * Endpoint'lere yapilan hatali/hatasiz request sayılarini tutmak icin
	 * kullanilan array
	 */
	int[] REQUEST_COUNTER = { 0, 0, 0 };

	/**
	 * Paginate edilmis karakter tablosunu ve gerekli datalari View katmanina gonderen metot
	 * 
	 * @param page Hedeflenen sayfa numarasi
	 * @param size Bir sayfada olmasi gereken kayit sayisi
	 * @param sort Siralama olcutu
	 * @return Datalar View katmanina ulasiyor
	 */
	@GetMapping("/character")
	public ModelAndView getAllCharacters(@RequestParam(defaultValue = "1") Integer page,
			@RequestParam(defaultValue = "20") Integer size, @RequestParam(defaultValue = "id") String sort) {
		REQUEST_COUNTER[0]++;
		// Page tipinde bir obje olusturuldu, pagination yapilmis data, Page tipindeki
		// objeye
		// atandi. Data'lari depolamak icin bir Map tanimlandi.

		Page<Character> pages = characterService.getAllCharacters(page, size, sort);
		Map<String, Object> model = new LinkedHashMap<String, Object>();

		int numOfPages = pages.getTotalPages();

		// View katmaninda kullanilacak datalar Map tipinde bir objeye aktariliyor.
		// Paginate edilmis Page objesi, siralama tipi ve sayfa sayisi gonderiliyor.
		model.put("sortName", sort);
		model.put("pages", pages);
		model.put("numOfPages", numOfPages);

		if (numOfPages > 0) {
			List<Integer> pageNumbers = IntStream.rangeClosed(1, numOfPages).boxed().collect(Collectors.toList());
			model.put("pageNumbers", pageNumbers);
		}

		// indexOfCharacter.html'e model isimli Map'teki datalar gonderiliyor.
		return new ModelAndView("indexOfCharacter", model);
	}

	/**
	 * Spesifik bir karakteri id ile bulan metot
	 * 
	 * @param id Bulunmak istenen karaktere ait id
	 * @return Datalar View katmanina ulasiyor
	 */
	@GetMapping("/character/{id}")
	public ModelAndView findCharacterById(@PathVariable Long id) {
		REQUEST_COUNTER[0]++;
		// episode endpoint'i cagirildiginda, View katmanina Page tipinde bir obje
		// gonderildi,
		// HTML ona gore duzenlendi. Burada ayni yapiyi kullanabilmek icin yine benzer
		// bir objeyle
		// gondermek icin liste olusturuldu ve tek kayit bir listeye eklendi.
		List<Character> list = new ArrayList<Character>();
		list.add(characterService.findById(id).get());
		Map<String, Object> model = new LinkedHashMap<String, Object>();

		// View katmaninda kullanilacak datalar Map tipinde bir objeye aktariliyor.
		// Paging islemi yapilmayacak, sayfa sayisi 0 gonderiliyor.
		// indexOfCharacter.html'e model isimli Map'teki datalar gonderiliyor.
		model.put("pages", list);
		model.put("numOfPages", 0);
		return new ModelAndView("indexOfCharacter", model);
	}

	/**
	 * Paginate edilmis bolumler tablosunu ve gerekli datalari View katmanina gonderen metot
	 * 
	 * @param page Hedeflenen sayfa numarasi
	 * @param size Bir sayfada olmasi gereken kayit sayisi
	 * @param sort Siralama olcutu
	 * @return Datalar View katmanina ulasiyor
	 * @see getAllCharacters
	 */
	@GetMapping("/episode")
	public ModelAndView getAllEpisodes(@RequestParam(defaultValue = "1") Integer page,
			@RequestParam(defaultValue = "20") Integer size, @RequestParam(defaultValue = "id") String sort) {
		REQUEST_COUNTER[1]++;
		Page<Episode> pages = episodeService.getAllEpisodes(page, size, sort);
		Map<String, Object> model = new LinkedHashMap<String, Object>();

		int numOfPages = pages.getTotalPages();

		model.put("sortName", sort);
		model.put("pages", pages);
		model.put("numOfPages", numOfPages);

		if (numOfPages > 0) {
			List<Integer> pageNumbers = IntStream.rangeClosed(1, numOfPages).boxed().collect(Collectors.toList());
			model.put("pageNumbers", pageNumbers);
		}

		return new ModelAndView("indexOfEpisode", model);
	}

	/**
	 * Spesifik bir bolumu id ile bulan metot
	 * 
	 * @param id Bulunmak istenen bolume ait id
	 * @return Datalar View katmanina ulasiyor
	 * @see findCharacterById
	 */
	@GetMapping("/episode/{id}")
	public ModelAndView findEpisodeById(@PathVariable Long id) {
		REQUEST_COUNTER[1]++;
		List<Episode> list = new ArrayList<Episode>();
		list.add(episodeService.findById(id).get());
		Map<String, Object> model = new LinkedHashMap<String, Object>();
		model.put("pages", list);
		model.put("numOfPages", 0);
		return new ModelAndView("indexOfEpisode", model);
	}

	/**
	 * Paginate edilmis lokasyon tablosunu ve gerekli datalari View katmanina gonderen metot
	 * 
	 * @param page Hedeflenen sayfa numarasi
	 * @param size Bir sayfada olmasi gereken kayit sayisi
	 * @param sort Siralama olcutu
	 * @return Datalar View katmanina ulasiyor
	 * @see getAllCharacters
	 */
	@GetMapping("/location")
	public ModelAndView getAllLocations(@RequestParam(defaultValue = "1") Integer page,
			@RequestParam(defaultValue = "20") Integer size, @RequestParam(defaultValue = "id") String sort) {
		REQUEST_COUNTER[2]++;
		Page<Location> pages = locationService.getAllLocations(page, size, sort);
		Map<String, Object> model = new LinkedHashMap<String, Object>();

		int numOfPages = pages.getTotalPages();

		model.put("sortName", sort);
		model.put("pages", pages);
		model.put("numOfPages", numOfPages);

		if (numOfPages > 0) {
			List<Integer> pageNumbers = IntStream.rangeClosed(1, numOfPages).boxed().collect(Collectors.toList());
			model.put("pageNumbers", pageNumbers);
		}

		return new ModelAndView("indexOfLocation", model);
	}

	/**
	 * Spesifik bir lokasyonu id ile bulan metot
	 * 
	 * @param id Bulunmak istenen karaktere ait id
	 * @return Datalar View katmanina ulasiyor
	 * @see findCharacterById
	 */
	@GetMapping("/location/{id}")
	public ModelAndView findLocationById(@PathVariable Long id) {
		REQUEST_COUNTER[2]++;
		List<Location> list = new ArrayList<Location>();
		list.add(locationService.findById(id).get());
		Map<String, Object> model = new LinkedHashMap<String, Object>();
		model.put("pages", list);
		model.put("numOfPages", 0);
		return new ModelAndView("indexOfLocation", model);
	}

	/**
	 * Endpointlerin cagirilma sayilarini tutan array, yine bir endpoint ile
	 * gosteriliyor
	 * 
	 * @return Datalar View katmanina ulasiyor
	 */
	@GetMapping("/report")
	public ModelAndView getNamesForThreading() {
		List<Report> report = new ArrayList<Report>();
		report.add(new Report("/character", REQUEST_COUNTER[0]));
		report.add(new Report("/episode", REQUEST_COUNTER[1]));
		report.add(new Report("/location", REQUEST_COUNTER[2]));

		Map<String, Object> model = new LinkedHashMap<String, Object>();
		model.put("list", report);
		return new ModelAndView("indexOfReport", model);
	}

}