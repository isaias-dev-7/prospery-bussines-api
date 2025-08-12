package com.isaias.prospery_bussines_api.user.specification;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.criteria.Predicate;

import org.springframework.data.jpa.domain.Specification;

import com.isaias.prospery_bussines_api.common.dtos.PaginDto;
import com.isaias.prospery_bussines_api.user.entity.UserEntity;
import com.isaias.prospery_bussines_api.user.enums.UserField;

public class UserSpecification {

    public static Specification<UserEntity> filters(PaginDto dto) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (!dto.getSearchTerm().isBlank()) {
                String term = "%" + dto.getSearchTerm().toLowerCase() + "%";

                Predicate usernameMatch = cb.like(
                             cb.lower(root.get(String.valueOf(UserField.username))),
                             term
                           );
                Predicate phoneMatch = cb.like(
                             cb.lower(root.get(String.valueOf(UserField.phone))), 
                             term
                           );
                Predicate emailMatch = cb.like(
                             cb.lower(root.get(String.valueOf(UserField.email))), 
                             term
                           );

                predicates.add(cb.or(usernameMatch, phoneMatch, emailMatch));
            }
            
            if (!dto.getRole().isBlank()) {
                predicates.add(cb.equal(root.get(String.valueOf(UserField.role)), dto.getRole()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
