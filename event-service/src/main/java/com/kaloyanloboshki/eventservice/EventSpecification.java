package com.kaloyanloboshki.eventservice;

import com.kaloyanloboshki.eventservice.model.dto.EventFilter;
import com.kaloyanloboshki.eventservice.model.entity.Event;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class EventSpecification {

    public static Specification<Event> withFilter(EventFilter filter) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filter.getCategory() != null)
                predicates.add(cb.equal(root.get("category"), filter.getCategory()));

            if (filter.getLocation() != null)
                predicates.add(cb.equal(root.get("location"), filter.getLocation()));

            if (filter.getStartsAt() != null)
                predicates.add(cb.greaterThanOrEqualTo(root.get("startsAt"), filter.getStartsAt()));

            if (filter.getEndsAt() != null)
                predicates.add(cb.lessThanOrEqualTo(root.get("endsAt"), filter.getEndsAt()));

            if (filter.getPrice() != null)
                predicates.add(cb.equal(root.get("price"), filter.getPrice()));

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
