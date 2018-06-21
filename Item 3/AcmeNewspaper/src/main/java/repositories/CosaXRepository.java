
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.CosaX;

@Repository
public interface CosaXRepository extends JpaRepository<CosaX, Integer> {

}
