package com.musicdistribution.streamingservice.util;

import com.musicdistribution.streamingservice.constant.AlphabetConstants;
import com.musicdistribution.streamingservice.constant.EntityConstants;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.SessionFactory;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.metamodel.spi.MetamodelImplementor;
import org.hibernate.type.Type;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A utility helper class used for search related manipulation.
 */
@Slf4j
public final class SearchUtil {

    /**
     * Private constructor which throws an exception because the class should not be instantiated.
     */
    private SearchUtil() {
        throw new UnsupportedOperationException();
    }

    /**
     * Method used to convert a string to a predicate expression.
     *
     * @param initialString - the string to be converted to a predicate expression.
     * @param entityRoot    - the entity root of the predicate expression.
     * @param <T>           - the type of the entity.
     * @return the generated expression.
     */
    public static <T> Expression<String> convertToPredicateExpression(String initialString, Root<T> entityRoot) {
        String[] parts = initialString.split(AlphabetConstants.UNDERSCORE);
        Path<String> predicate = entityRoot.get(parts[0]);
        for (int i = 1; i < parts.length; i++) {
            predicate = (parts[i].equals(EntityConstants.ID))
                    ? predicate.get(parts[i]).get(EntityConstants.ID)
                    : predicate.get(parts[i]);
        }
        return predicate;
    }

    /**
     * Method used to convert search parameters to OR predicates.
     *
     * @param searchParams - the search parameters from which the predicates are to be generated.
     * @param entityRoot   - the entity root of the predicate expression.
     * @param cb           - the criteria builder on which the predicates are to be applied to.
     * @param searchTerm   - the term which will be used in the search predicates.
     * @param <T>          - the type of the entity.
     * @return an array of the generated 'OR' predicates.
     */
    public static <T> Predicate convertToOrPredicates(List<String> searchParams,
                                                      Root<T> entityRoot,
                                                      CriteriaBuilder cb,
                                                      String searchTerm) {
        return cb.or(searchParams.stream()
                .map(param -> cb.like(SearchUtil.convertToPredicateExpression(param, entityRoot).as(String.class),
                        String.format("%%%s%%", searchTerm)))
                .toArray(Predicate[]::new));
    }

    /**
     * Method used to generate search parameters given the entity parameters.
     *
     * @param searchParams - the initial search parameters list.
     * @param entityParams - the entity parameters list.
     * @return a list of the converted search parameters.
     */
    public static List<String> convertToSearchParams(List<String> searchParams,
                                                     List<String> entityParams) {
        return searchParams.stream().filter(param -> !param.isBlank())
                .filter(param -> Arrays.stream(param.split(AlphabetConstants.UNDERSCORE))
                        .anyMatch(p -> entityParams.stream()
                                .anyMatch(e -> e.equals(p))))
                .collect(Collectors.toList());
    }

    /**
     * Method used to build search parameters from the root class.
     *
     * @param searchParams   - the parameters that are to be transformed.
     * @param rootClassName  - the class name of the root entity.
     * @param sessionFactory - the session factory from which the property names from the entity are to be read from.
     * @return a list of the formatted search parameters.
     */
    public static List<String> buildSearchParams(List<String> searchParams, String rootClassName, SessionFactory sessionFactory) {
        List<String> parameters = getParameters(new ArrayList<>(), rootClassName, sessionFactory);
        return convertToSearchParams(searchParams, parameters);
    }

    /**
     * Method used for reading parameters from a given root entity class.
     *
     * @param params         - the list which is to be populated with the parameters.
     * @param rootClassName  - the class name of the root entity.
     * @param sessionFactory - the session factory from which the property names from the entity are to be read from.
     * @return a list of the formatted search parameters recursively.
     */
    private static List<String> getParameters(List<String> params, String rootClassName, SessionFactory sessionFactory) {
        MetamodelImplementor metamodel = (MetamodelImplementor) sessionFactory.getMetamodel();
        ClassMetadata classMetadata = (ClassMetadata) metamodel.entityPersister(rootClassName);

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

    /**
     * Method used to read the names of the properties from a given class metadata.
     *
     * @param classMetadata - the class metadata from which the property names are to be read from.
     * @return an array with the property names.
     */
    private static String[] getPropertyNames(ClassMetadata classMetadata) {
        try {
            return classMetadata.getPropertyNames();
        } catch (Exception e) {
            log.error(e.getMessage());
            return new String[]{};
        }
    }
}
