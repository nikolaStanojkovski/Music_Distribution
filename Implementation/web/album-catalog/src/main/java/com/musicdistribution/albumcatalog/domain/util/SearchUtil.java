package com.musicdistribution.albumcatalog.domain.util;

import org.hibernate.SessionFactory;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.type.Type;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public final class SearchUtil {

    public static <T> Expression<String> convertToPredicateExpression(String initialString, Root<T> entityRoot) {
        String[] parts = initialString.split("_");
        Path<String> predicate = entityRoot.get(parts[0]);
        for (int i = 1; i < parts.length; i++) {
            predicate = (parts[i].equals("id"))
                    ? predicate.get(parts[i]).get("id")
                    : predicate.get(parts[i]);
        }
        return predicate;
    }

    public static List<String> convertToSearchParams(List<String> searchParams,
                                                     List<String> entityParams) {
        return searchParams.stream().filter(param -> !param.isBlank())
                .filter(param -> Arrays.stream(param.split("_"))
                        .anyMatch(p -> entityParams.stream()
                                .anyMatch(e -> e.equals(p))))
                .collect(Collectors.toList());
    }

    public static List<String> buildSearchParams(List<String> searchParams, String rootClassName, SessionFactory sessionFactory) {
        List<String> parameters = getParameters(new ArrayList<>(), rootClassName, sessionFactory);
        return convertToSearchParams(searchParams, parameters);
    }

    private static List<String> getParameters(List<String> params, String rootClassName, SessionFactory sessionFactory) {
        ClassMetadata classMetadata = sessionFactory.getClassMetadata(rootClassName);
        for (String propertyName : getPropertyNames(classMetadata)) {
            Type propertyType = classMetadata.getPropertyType(propertyName);
            params.add(propertyName);
            if (propertyType.isEntityType()) {
                params.addAll(getParameters(new ArrayList<>(),
                        propertyType.getReturnedClass().getName(),
                        sessionFactory));
            }
        }
        String classId = classMetadata.getIdentifierPropertyName();
        if (params.contains(classId)) {
            params.add(classId);
        }
        return params;
    }

    private static String[] getPropertyNames(ClassMetadata classMetadata) {
        try {
            return classMetadata.getPropertyNames();
        } catch (Exception e) {
            return new String[]{};
        }
    }
}
