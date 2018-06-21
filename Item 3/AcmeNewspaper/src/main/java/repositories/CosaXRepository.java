
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.CosaX;

@Repository
public interface CosaXRepository extends JpaRepository<CosaX, Integer> {

	@Query("select x from CosaX n where n.newspaper.id=?1 and (x.publicationDate is null or x.publicationDate>CURRENT_timestamp")
	Collection<CosaX> findCosasX(int NewspaperId);
}
