package imtiaz.sktech.mytutoron.persistence.specification;

import imtiaz.sktech.mytutoron.persistence.entity.UserEntity;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

public final class UserSpecification {

    private UserSpecification() {}

    public static Specification<UserEntity> getSpecification(Map<String, Object> specificParameters) {
        return ((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            for (Map.Entry<String, Object> entry : specificParameters.entrySet()) {
                String filterBy = entry.getKey();
                String filterWith = entry.getValue().toString();

                if (filterWith != null && !filterWith.isEmpty()) {
                    Class<?> type = root.get(filterBy).getJavaType();

                    if (type.equals(Long.class)) {
                        predicates.add(criteriaBuilder.equal(root.get(filterBy), Long.valueOf(filterWith)));
                    }
                    else if (type.equals(Boolean.class)) {
                        predicates.add(criteriaBuilder.equal(root.get(filterBy), Boolean.valueOf(filterWith)));
                    }
                    else if (type.equals(String.class)) {
                        predicates.add(criteriaBuilder.like(criteriaBuilder.upper(root.get(filterBy)), "%" + filterWith.toUpperCase() + "%"));
                    }
                    else if (type.equals(UUID.class)) {
                        predicates.add(criteriaBuilder.equal(root.get(filterBy), UUID.fromString(filterWith)));
                    }
                    else if (type.equals(LocalDateTime.class)) {
                        LocalDate localDate = LocalDate.parse(filterWith);
                        LocalDateTime startDateTime = LocalDateTime.of(localDate, LocalTime.MIN);
                        LocalDateTime endDateTime = LocalDateTime.of(localDate, LocalTime.MAX);
                        predicates.add(criteriaBuilder.between(root.get(filterBy), startDateTime, endDateTime));
                    }
//                    else if (type.isEnum()) {
//                        Enum<?> enumValue = Enum.valueOf((Class<Enum>) type, filterWith);
//                        predicates.add(criteriaBuilder.equal(root.get(filterBy), enumValue));
//                    }
                    else {
                        predicates.add(criteriaBuilder.equal(root.get(filterBy), filterWith));
                    }
                }
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[]{}));
        });
    }
}
