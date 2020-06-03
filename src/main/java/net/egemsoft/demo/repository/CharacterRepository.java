package net.egemsoft.demo.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import net.egemsoft.demo.model.Character;

/**
 * CharacterService'in kullandigi repository. Karakterleri, oynadigi bolum
 * sayisina gore siniflandirmak icin ekstradan bir metot daha tanimlandi. Query
 * anotasyonuyla elle sorgu yazildi.
 * 
 * @author Mehmet Atif Imal
 *
 */
public interface CharacterRepository extends PagingAndSortingRepository<Character, Long> {

	/**
	 * Karakterin oynadigi bolum sayisina gore gruplama ve siralamalar yapilarak
	 * elde edilen tablo, Page.
	 * @param paging Service katmanindan gelen pagination sablonu
	 * @return Sorgu sonucu donen tablo Page tipinde service katmanina iletildi.
	 */
	@Query(value = "SELECT * FROM CHARACTER U, (SELECT COUNT(EPISODE) AS C_EPI, CHARACTER_ID AS ID FROM CHARACTER_EPISODE GROUP BY ID) V WHERE U.ID=V.ID ORDER BY V.C_EPI DESC", nativeQuery = true)
	Page<Character> findAllOrderByEpisodeCount(Pageable paging);
}
