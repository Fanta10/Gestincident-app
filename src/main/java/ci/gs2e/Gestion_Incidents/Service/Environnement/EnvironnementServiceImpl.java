package ci.gs2e.Gestion_Incidents.Service.Environnement;

import ci.gs2e.Gestion_Incidents.Modele.*;
import ci.gs2e.Gestion_Incidents.Repository.EnvironnementCriteriaRepository;
import ci.gs2e.Gestion_Incidents.Repository.EnvironnementRepository;
import ci.gs2e.Gestion_Incidents.Repository.RexCriteriaRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class EnvironnementServiceImpl implements EnvironnementService{
    EnvironnementRepository environnementRepository;

    @Autowired
    private EnvironnementCriteriaRepository environnementCriteriaRepository;

    @Override
    public Environnement create(Environnement environnement)
    {
        return environnementRepository.save(environnement);
    }

    @Override
    public Environnement edit(Environnement environnement) {
        Environnement existingEnv = environnementRepository.findById(environnement.getIdEnv()).orElse(null);
        existingEnv.setLibelleEnv(environnement.getLibelleEnv());


        return environnementRepository.save(existingEnv);
    }

    @Override
    public void delete(int idEnv) {
        environnementRepository.deleteById(idEnv);

    }

    @Override
    public List<Environnement> listAll() {

        return environnementRepository.findAll();
    }

    @Override
    public Page<Environnement> getEnvironnementPagination(Integer pageNumber, Integer pageSize, String sort) {
            Pageable pageable = null;
            if (sort != null) {
                // with sorting
                pageable = PageRequest.of(pageNumber, pageSize, Sort.Direction.ASC, sort);
            } else {
                // without sorting
                pageable = PageRequest.of(pageNumber, pageSize);
            }
            return environnementRepository.findAll(pageable);

        //return null;
    }

    @Override
    public Optional<Environnement> listByLibelle(String libelleEnv) {

        return environnementRepository.findByLibelleEnvContains(libelleEnv);
    }

    @Override
    public Page<Environnement> getRex(EnvironnementPage environnementPage, EnvironnementSearchCriteria environnementSearchCriteria) {
        return environnementCriteriaRepository.findAllFilters(environnementPage, environnementSearchCriteria);
    }







}
