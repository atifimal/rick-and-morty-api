package net.egemsoft.demo.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import net.egemsoft.demo.model.Location;

/**
 * LocationService'in kullandigi repository
 * 
 * @author Mehmet Atif Imal
 *
 */
public interface LocationRepository extends PagingAndSortingRepository<Location, Long> {

}
