package ci.gs2e.Gestion_Incidents.Repository;

import ci.gs2e.Gestion_Incidents.Modele.Incident;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IncidentRepository extends JpaRepository<Incident,Integer> {
    @Query(value = "SELECT t.resolution FROM incident t  WHERE id_inc = :lib AND id_env = :idenv AND id_app= :idapp", nativeQuery = true)
    String LoadFile(@Param("lib") int lib, @Param("idenv") int idenv, @Param("idapp") int idapp);
}
