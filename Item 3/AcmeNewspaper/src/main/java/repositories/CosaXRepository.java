
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.CosaX;

@Repository
public interface CosaXRepository extends JpaRepository<CosaX, Integer> {

	@Query("select c from CosaX c where c.newspaper.id=?1 and (c.publicationDate is null or c.publicationDate>CURRENT_timestamp")
	Collection<CosaX> findCosasX(int NewspaperId);
}
