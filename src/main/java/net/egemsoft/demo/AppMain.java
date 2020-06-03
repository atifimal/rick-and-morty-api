package net.egemsoft.demo;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import net.egemsoft.demo.model.Character;
import net.egemsoft.demo.model.Episode;
import net.egemsoft.demo.model.Location;
import net.egemsoft.demo.service.CharacterService;
import net.egemsoft.demo.service.EpisodeService;
import net.egemsoft.demo.service.LocationService;

/**
 * Spring Boot uygulamasi olarak belirlenen main class, CommandLineRunner'dan
 * implement edildi Uygulanmak istenen adimlar run metodu Override edilerek
 * yazildi. https://rickandmortyapi.com/api/ adresindeki endpoint'lerden JSON
 * bilgileri okundu ve Service katmanina iletildi.
 * 
 * @author Mehmet Atif Imal
 *
 */
@SpringBootApplication
public class AppMain implements CommandLineRunner {
	public static void main(String[] args) {
		SpringApplication.run(AppMain.class);
	}

	@Autowired
	private CharacterService characterService;

	@Autowired
	private EpisodeService episodeService;

	@Autowired
	private LocationService locationService;

	/**
	 * CommandLineRunner interface'inden Override edildi. Islemler bu metot
	 * icerisinde yapildi. URL'den bilgi toplama islemleri bu metotta yapiliyor.
	 */
	@Override
	public void run(String... args) throws Exception {

		// API'dan okunacak bilgileri tutmak icin, Character sinif tipinde bir liste
		// olusturuldu.
		// API adresindeki JSON datasi, ozel bir metot ile okundu ve bir string
		// icerisine eklendi.
		List<Character> listOfCharacters = new ArrayList<Character>();
		String jsonData = readUrl("https://rickandmortyapi.com/api/character");

		System.out.println("Data toplamaya basladi...");

		// Paginate edilmis JSON datasi icerisinde "next" bilgisi bulunursa donguye
		// devam edilecegi
		// icin, dongu bir kez gerceklestirildi.
		do {
			// JSON datasindaki result ve info taglari ayirilarak iki farkli JSON objesine
			// atandi.
			// Kullanim kolayligi acisindan Gson dependency'si tercih edildi.
			JsonObject characterJsonObject = JsonParser.parseString(jsonData).getAsJsonObject();
			JsonArray jsonArrayResults = characterJsonObject.get("results").getAsJsonArray();
			JsonObject jsonObjectInfo = characterJsonObject.get("info").getAsJsonObject();

			Gson gson = new Gson();

			// result tag'i icerisindeki kayitlar kadar ayristirma islemi yapilacak.
			for (int i = 0; i < jsonArrayResults.size(); i++) {

				// result tag'i icerisindeki kayitlar, olusturulan entity modeline uygun
				// olacagi icin bu modele donusturuldu. Episode bilgisi URL olarak alindi.
				// URL'in son kismindaki id bilgisini elde etmek icin '/' karakterine gore
				// ayirarak son kisim alindi ve entity'ye URL olarak degil, id olarak
				// eklenmek uzere degistirildi.
				Character chr = gson.fromJson(jsonArrayResults.get(i), Character.class);
				for (int j = 0; j < chr.getEpisode().size(); j++) {
					String[] x = chr.getEpisode().get(j).split("/");
					chr.getEpisode().set(j, x[x.length - 1]);
				}

				// Benzer URL bilgilerinin bulundugu 3 sutun icin de ayni islemler yapildi.
				// Episode gibi, List tipinde olmadiklari icin for dongusune gerek duymadan
				// cevrildi.
				String[] x = chr.getOrigin().getUrl().split("/");
				chr.getOrigin().setUrl(x[x.length - 1]);

				x = chr.getLocation().getUrl().split("/");
				chr.getLocation().setUrl(x[x.length - 1]);

				x = chr.getUrl().split("/");
				chr.setUrl(x[x.length - 1]);

				listOfCharacters.add(chr);
			}

			// info tag'i icerisindeki next bilgisi bos degilse, URL tekrar okunmak uzere
			// degistirilir.
			// bos olmasi durumunda donguden cikilir.
			if (!jsonObjectInfo.get("next").isJsonNull()) {
				jsonData = readUrl(jsonObjectInfo.get("next").getAsString());
			} else
				break;

			// (Sonsuz dongu tercih etmiyorum, fakat break komutunun calisacagindan emin
			// oldugum icin
			// kullanmaktan cekinmedim.)
		} while (true);

		characterService.save(listOfCharacters);
		System.out.println("Character listesi toplandi, devam ediyor...");

		// Tek bir dongude 3 endpoint de okunabilirdi, karmasikligi azaltmak ve daha
		// okunabilir bir
		// kod icin 3 farkli dongude yazildi. Islemler hemen hemen ayni.
		List<Location> listOfLocations = new ArrayList<Location>();
		jsonData = readUrl("https://rickandmortyapi.com/api/location");

		do {
			JsonObject locationJsonObject = JsonParser.parseString(jsonData).getAsJsonObject();
			JsonArray jsonArrayResults = locationJsonObject.get("results").getAsJsonArray();
			JsonObject jsonObjectInfo = locationJsonObject.get("info").getAsJsonObject();

			Gson gson = new Gson();

			for (int i = 0; i < jsonArrayResults.size(); i++) {

				Location loc = gson.fromJson(jsonArrayResults.get(i), Location.class);

				String[] x = loc.getUrl().split("/");
				loc.setUrl(x[x.length - 1]);

				// Residents bilgisi URL olarak alindi. Karakter id'si URL'den ayiklandi ve isim
				// bilgisi olarak kaydedildi.
				for (int j = 0; j < loc.getResidents().size(); j++) {
					x = loc.getResidents().get(j).split("/");
					loc.getResidents().set(j,
							characterService.findById(Long.parseLong(x[x.length - 1])).get().getName());
				}
				listOfLocations.add(loc);
			}

			if (!jsonObjectInfo.get("next").isJsonNull()) {
				jsonData = readUrl(jsonObjectInfo.get("next").getAsString());
			} else
				break;

		} while (true);

		locationService.save(listOfLocations);
		System.out.println("Location listesi toplandi, devam ediyor...");

		// episode
		List<Episode> listOfEpisodes = new ArrayList<Episode>();
		jsonData = readUrl("https://rickandmortyapi.com/api/episode");

		do {
			JsonObject episodeJsonObject = JsonParser.parseString(jsonData).getAsJsonObject();
			JsonArray jsonArrayResults = episodeJsonObject.get("results").getAsJsonArray();
			JsonObject jsonObjectInfo = episodeJsonObject.get("info").getAsJsonObject();

			Gson gson = new Gson();

			for (int i = 0; i < jsonArrayResults.size(); i++) {
				Episode epi = gson.fromJson(jsonArrayResults.get(i), Episode.class);

				// Characters bilgisi URL olarak alindi. Karakter id'si URL'den ayiklandi ve
				// isim
				// bilgisi olarak kaydedildi.
				for (int j = 0; j < epi.getCharacters().size(); j++) {
					String[] x = epi.getCharacters().get(j).split("/");
					epi.getCharacters().set(j,
							characterService.findById(Long.parseLong(x[x.length - 1])).get().getName());
				}

				String[] x = epi.getUrl().split("/");
				epi.setUrl(x[x.length - 1]);

				listOfEpisodes.add(epi);
			}

			if (!jsonObjectInfo.get("next").isJsonNull()) {
				jsonData = readUrl(jsonObjectInfo.get("next").getAsString());
			} else
				break;

		} while (true);

		episodeService.save(listOfEpisodes);
		System.out.println("Episode listesi toplandi.\nHazır.");

	}

	/**
	 * API'dan JSON bilgilerini toplamak icin kullanilan yardimci metot
	 * 
	 * @param urlString Okunacak URL bilgisi
	 * @return URL'deki bilgiler karakter bazinda okunarak String olarak dondurulur.
	 * @throws Exception Hata
	 */
	private static String readUrl(String urlString) throws Exception {
		BufferedReader reader = null;
		try {
			URL url = new URL(urlString);
			reader = new BufferedReader(new InputStreamReader(url.openStream()));
			StringBuffer buffer = new StringBuffer();
			int read;
			char[] chars = new char[1024];
			while ((read = reader.read(chars)) != -1)
				buffer.append(chars, 0, read);

			return buffer.toString();
		} finally {
			if (reader != null)
				reader.close();
		}
	}
}