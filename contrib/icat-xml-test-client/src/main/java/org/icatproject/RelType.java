
package org.icatproject;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for relType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="relType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="MANY"/>
 *     &lt;enumeration value="ONE"/>
 *     &lt;enumeration value="ATTRIBUTE"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "relType")
@XmlEnum
public enum RelType {

    MANY,
    ONE,
    ATTRIBUTE;

    public String value() {
        return name();
    }

    public static RelType fromValue(String v) {
        return valueOf(v);
    }

}
