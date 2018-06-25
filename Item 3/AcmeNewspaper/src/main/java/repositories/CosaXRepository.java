
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.CosaX;

@Repository
public interface CosaXRepository extends JpaRepository<CosaX, Integer> {

	@Query("select c from CosaX c where c.newspaper.id=?1 and c.draftMode=0 and (c.publicationDate = null or c.publicationDate<CURRENT_timestamp)")
	Collection<CosaX> findCosasX(int NewspaperId);

	@Query("select c from CosaX c where c.administrator.id=?1")
	Collection<CosaX> findByAdministrator(int administratorId);

	@Query("select c from CosaX c where c.newspaper.id=?1")
	Collection<CosaX> findByNewspaper(int newspaperId);

}
