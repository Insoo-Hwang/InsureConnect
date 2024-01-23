package com.example.InsureConnect.Config.Paging;

import com.example.InsureConnect.Entity.Planner;
import com.example.InsureConnect.Entity.Review;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class ReviewSpecification {
    public static Specification<Review> buildSpecification(String search, String criteria) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if ("title".equals(criteria)) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("title")), "%" + search.toLowerCase() + "%"));
            } else if ("content".equals(criteria)) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("content")), "%" + search.toLowerCase() + "%"));
            } else if ("planner".equals(criteria)) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("planner").get("plannerNickname")), "%" + search.toLowerCase() + "%"));
            }

            query.orderBy(criteriaBuilder.desc(root.get("edit")));

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
    }
