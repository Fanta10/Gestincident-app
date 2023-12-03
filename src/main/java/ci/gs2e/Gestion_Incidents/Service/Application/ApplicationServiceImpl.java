package ci.gs2e.Gestion_Incidents.Service.Application;

import ci.gs2e.Gestion_Incidents.Modele.Logiciel;
import ci.gs2e.Gestion_Incidents.Modele.LogicielPage;
import ci.gs2e.Gestion_Incidents.Modele.LogicielSearchCriteria;
import ci.gs2e.Gestion_Incidents.Repository.AppEnvRepository;
import ci.gs2e.Gestion_Incidents.Repository.ApplicationCriteriaRepository;
import ci.gs2e.Gestion_Incidents.Repository.ApplicationRepository;
import ci.gs2e.Gestion_Incidents.Repository.EnvironnementCriteriaRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
//@AllArgsConstructor
@Service
public class ApplicationServiceImpl implements ApplicationService{
    @Autowired
   private ApplicationRepository applicationRepository;

    @Autowired
    private ApplicationCriteriaRepository applicationCriteriaRepository;

    @Override
    public Logiciel create(Logiciel application) {

        return applicationRepository.save(application);
    }

    @Override
    public Logiciel edit(int idApp,Logiciel logiciel) {
     Logiciel existingLogiciel = applicationRepository.findById(idApp).orElseThrow(() -> new RuntimeException("Logiciel not found with id: " + idApp));

         existingLogiciel.setLibelleApp(logiciel.getLibelleApp());
         existingLogiciel.setDescriptionApp(logiciel.getDescriptionApp());
         existingLogiciel.setRex(logiciel.getRex());


        return applicationRepository.save(existingLogiciel);
    }

    @Override
    public void delete(int idApp) {
        applicationRepository.deleteById(idApp);

    }

    @Override
    public List<Logiciel> listAll() {

        return applicationRepository.findAll();
    }

    @Override
    public Logiciel listByLibelle(Logiciel application) {
        return null;
    }

    @Override
    public Optional<Logiciel> listByLibelle(String libelleApp) {
        return applicationRepository.findByLibelleAppContains(libelleApp);
    }

    @Override
    public Page<Logiciel> getRex(LogicielPage logicielPage, LogicielSearchCriteria logicielSearchCriteria) {
        return applicationCriteriaRepository.findAllFilters(logicielPage, logicielSearchCriteria);

    }


}
