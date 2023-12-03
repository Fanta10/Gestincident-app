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
public class ApplicationCriteriaRepository {
    private final EntityManager entityManager;
    private final CriteriaBuilder criteriaBuilder;

    public ApplicationCriteriaRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.criteriaBuilder = entityManager.getCriteriaBuilder();
    }

    public Page<Logiciel> findAllFilters(LogicielPage logicielPage, LogicielSearchCriteria logicielSearchCriteria) {
        CriteriaQuery<Logiciel> criteriaQuery = criteriaBuilder.createQuery(Logiciel.class);
        Root<Logiciel> rexRoot = criteriaQuery.from(Logiciel.class);
        Predicate predicate = getPredicate(logicielSearchCriteria, rexRoot);
        criteriaQuery.where(predicate);
        setOrder(logicielPage, criteriaQuery, rexRoot);
        TypedQuery<Logiciel> typedQuery = entityManager.createQuery(criteriaQuery);
        typedQuery.setFirstResult(logicielPage.getPageNumber() * logicielPage.getPageSize());
        typedQuery.setMaxResults(logicielPage.getPageSize());
        Pageable pageable =getPageable(logicielPage);
        long rexCount =     getRexCount(predicate);
        return  new PageImpl<>(typedQuery.getResultList(),pageable, rexCount);
    }




    private Predicate getPredicate(LogicielSearchCriteria logicielSearchCriteria, Root<Logiciel> rexRoot) {
        List<Predicate> predicates = new ArrayList<>();
        if (Objects.nonNull(logicielSearchCriteria.getLibelleApp())) {
            predicates.add(criteriaBuilder.like(rexRoot.get("libelleEnv"), "%" + logicielSearchCriteria.getLibelleApp()
                    + "%"));
        }
        if (Objects.nonNull(logicielSearchCriteria.getLibelleApp())) {
            predicates.add(criteriaBuilder.like(rexRoot.get("libelleApp"), "%" + logicielSearchCriteria.getLibelleApp()
                    + "%"));

        }
        return criteriaBuilder.and(predicates.toArray(predicates.toArray(new Predicate[0])));
    }

    private void setOrder(LogicielPage logicielPage, CriteriaQuery<Logiciel> criteriaQuery, Root<Logiciel> rexRoot) {
        if(logicielPage.getSortDirection().equals(Sort.Direction.DESC)){
            criteriaQuery.orderBy(criteriaBuilder.desc(rexRoot.get(logicielPage.getSortBy())));
        }
        else {
            criteriaQuery.orderBy(criteriaBuilder.asc(rexRoot.get(logicielPage.getSortBy())));
        }

    }
    private Pageable getPageable(LogicielPage logicielPage) {
        Sort sort = Sort.by(logicielPage.getSortDirection(), logicielPage.getSortBy());
        return PageRequest.of(logicielPage.getPageNumber(), logicielPage.getPageSize(), sort);
    }
    private long getRexCount(Predicate predicate) {
        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        Root<Logiciel> countRoot = countQuery.from(Logiciel.class);
        countQuery.select(criteriaBuilder.count(countRoot)).where(predicate);
        return entityManager.createQuery(countQuery).getSingleResult();

    }
}
