package ci.gs2e.Gestion_Incidents.Repository;

import ci.gs2e.Gestion_Incidents.Modele.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class EnvironnementCriteriaRepository {
    private final EntityManager entityManager;
    private final CriteriaBuilder criteriaBuilder;

    public EnvironnementCriteriaRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.criteriaBuilder = entityManager.getCriteriaBuilder();
    }

    public Page<Environnement> findAllFilters(EnvironnementPage environnementPage, EnvironnementSearchCriteria environnementSearchCriteria) {
        CriteriaQuery<Environnement> criteriaQuery = criteriaBuilder.createQuery(Environnement.class);
        Root<Environnement> rexRoot = criteriaQuery.from(Environnement.class);
        Predicate predicate = getPredicate(environnementSearchCriteria, rexRoot);
        criteriaQuery.where(predicate);
        setOrder(environnementPage, criteriaQuery, rexRoot);
        TypedQuery<Environnement> typedQuery = entityManager.createQuery(criteriaQuery);
        typedQuery.setFirstResult(environnementPage.getPageNumber() * environnementPage.getPageSize());
        typedQuery.setMaxResults(environnementPage.getPageSize());
        Pageable pageable =getPageable(environnementPage);
        long rexCount =     getRexCount(predicate);
        return  new PageImpl<>(typedQuery.getResultList(),pageable, rexCount);
    }




    private Predicate getPredicate(EnvironnementSearchCriteria environnementSearchCriteria, Root<Environnement> rexRoot) {
        List<Predicate> predicates = new ArrayList<>();
        if (Objects.nonNull(environnementSearchCriteria.getLibelleEnv())) {
            predicates.add(criteriaBuilder.like(rexRoot.get("libelleEnv"), "%" + environnementSearchCriteria.getLibelleEnv()
                    + "%"));
        }
        if (Objects.nonNull(environnementSearchCriteria.getLibelleEnv())) {
            predicates.add(criteriaBuilder.like(rexRoot.get("prenom"), "%" + environnementSearchCriteria.getLibelleEnv()
                    + "%"));

        }
        return criteriaBuilder.and(predicates.toArray(predicates.toArray(new Predicate[0])));
    }

    private void setOrder(EnvironnementPage environnementPage, CriteriaQuery<Environnement> criteriaQuery, Root<Environnement> rexRoot) {
        if(environnementPage.getSortDirection().equals(Sort.Direction.DESC)){
            criteriaQuery.orderBy(criteriaBuilder.desc(rexRoot.get(environnementPage.getSortBy())));
        }
        else {
            criteriaQuery.orderBy(criteriaBuilder.asc(rexRoot.get(environnementPage.getSortBy())));
        }

    }
    private Pageable getPageable(EnvironnementPage environnementPage) {
        Sort sort = Sort.by(environnementPage.getSortDirection(), environnementPage.getSortBy());
        return PageRequest.of(environnementPage.getPageNumber(), environnementPage.getPageSize(), sort);
    }
    private long getRexCount(Predicate predicate) {
        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        Root<Environnement> countRoot = countQuery.from(Environnement.class);
        countQuery.select(criteriaBuilder.count(countRoot)).where(predicate);
        return entityManager.createQuery(countQuery).getSingleResult();

    }
}
