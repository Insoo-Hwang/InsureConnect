package com.example.InsureConnect.Config.Paging;

import com.example.InsureConnect.Entity.Planner;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.criteria.Predicate;

public class PlannerSpecification {
    public static Specification<Planner> buildSpecification(String search, String criteria, String sort) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if ("title".equals(criteria)) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("promotion").get("title")), "%" + search.toLowerCase() + "%"));
            } else if ("content".equals(criteria)) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("promotion").get("content")), "%" + search.toLowerCase() + "%"));
            } else if ("category".equals(criteria)) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("connectCategory").get("category").get("categoryName")), "%" + search.toLowerCase() + "%"));
            }

            predicates.add(criteriaBuilder.equal(root.get("status"), "permit"));

            query.groupBy(root.get("id"), root.join("promotion").get("write"));

            if ("write".equals(sort)) {
                query.orderBy(criteriaBuilder.desc(root.get("promotion").get("write")));
            } else if ("rating".equals(sort)) {
                query.orderBy(criteriaBuilder.desc(criteriaBuilder.coalesce(criteriaBuilder.avg(root.join("review").get("rate")), 0)));
            } else if ("count".equals(sort)) {
                query.orderBy(criteriaBuilder.desc(criteriaBuilder.size(root.get("review"))));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

}
