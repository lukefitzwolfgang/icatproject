
package org.icatproject;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for icatExceptionType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="icatExceptionType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="BAD_PARAMETER"/>
 *     &lt;enumeration value="INTERNAL"/>
 *     &lt;enumeration value="INSUFFICIENT_PRIVILEGES"/>
 *     &lt;enumeration value="NO_SUCH_OBJECT_FOUND"/>
 *     &lt;enumeration value="OBJECT_ALREADY_EXISTS"/>
 *     &lt;enumeration value="SESSION"/>
 *     &lt;enumeration value="VALIDATION"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "icatExceptionType")
@XmlEnum
public enum IcatExceptionType {

    BAD_PARAMETER,
    INTERNAL,
    INSUFFICIENT_PRIVILEGES,
    NO_SUCH_OBJECT_FOUND,
    OBJECT_ALREADY_EXISTS,
    SESSION,
    VALIDATION;

    public String value() {
        return name();
    }

    public static IcatExceptionType fromValue(String v) {
        return valueOf(v);
    }

}
