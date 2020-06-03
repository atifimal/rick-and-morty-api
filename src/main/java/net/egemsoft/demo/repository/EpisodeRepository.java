package net.egemsoft.demo.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import net.egemsoft.demo.model.Episode;

/**
 * EpisodeService'in kullandigi repository. Bolumleri, oynayan karakter sayisina
 * gore siniflandirmak icin ekstradan bir metot daha tanimlandi. Query
 * anotasyonuyla elle sorgu yazildi.
 * 
 * @author Mehmet Atif Imal
 *
 */
public interface EpisodeRepository extends PagingAndSortingRepository<Episode, Long> {

	/**
	 * Bolumlerde oynayan karakter sayisina gore gruplama ve siralamalar yapilarak
	 * elde edilen tablo, Page.
	 * @param paging Service katmanindan gelen pagination sablonu
	 * @return Sorgu sonucu donen tablo Page tipinde service katmanina iletildi.
	 */
	@Query(value = "SELECT * FROM EPISODE U, (SELECT COUNT(CHARACTERS) AS C_CHR, EPISODE_ID AS ID FROM EPISODE_CHARACTERS GROUP BY ID) V WHERE U.ID=V.ID ORDER BY V.C_CHR DESC", nativeQuery = true)
	Page<Episode> findAllOrderByCharacterCount(Pageable paging);
}
