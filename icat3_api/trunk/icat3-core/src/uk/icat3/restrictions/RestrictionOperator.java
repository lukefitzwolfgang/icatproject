/*
 * This code is developed in Institut Laue-Langevin (France).
 * Its goal is the implementation of parameter search into ICAT Web Service
 * 
 * Created on 19 nov. 2010
 */

package uk.icat3.restrictions;

/**
 *
 * @author cruzcruz
 */
public enum RestrictionOperator {
     /** Restriction value greater that a value.*/
    GREATER_THAN,
    /** Restriction value lower that a value */
    LESS_THAN,
    /** Restriction greater or equal that a value */
    GREATER_EQUAL,
    /** Restriction value less or equal that a value */
    LESS_EQUAL,
    /** Restriction (=) */
    EQUAL,
    /** Restriction (%value%) */
    CONTAIN,
    /** Restriction (value%) */
    START_WITH,
    /** Restriction (%value) */
    END_WITH,
    /** Restriction IN (value, value2, value3, ..) */
    IN;

    public String getRestriction(String value) {
        if (this == RestrictionOperator.GREATER_THAN)
            return "> " + value;

        if (this == RestrictionOperator.LESS_THAN)
            return "< " + value;

        if (this == RestrictionOperator.GREATER_EQUAL)
            return ">= " + value;

        if (this == RestrictionOperator.LESS_EQUAL)
            return "<= " + value;

        if (this == RestrictionOperator.EQUAL)
            return "= " + value;

        if (this == RestrictionOperator.CONTAIN)
            return "LIKE '%" + value + "%'";

        if (this == RestrictionOperator.START_WITH)
            return "LIKE '" + value + "%'";

        if (this == RestrictionOperator.END_WITH)
            return "LIKE '%" + value + "'";

        if (this == RestrictionOperator.IN)
            return "IN (" + value + ")";
        
        return "";
    }
}
