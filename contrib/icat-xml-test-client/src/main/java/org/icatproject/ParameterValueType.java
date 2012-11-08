
package org.icatproject;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for parameterValueType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="parameterValueType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="DATE_AND_TIME"/>
 *     &lt;enumeration value="NUMERIC"/>
 *     &lt;enumeration value="STRING"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "parameterValueType")
@XmlEnum
public enum ParameterValueType {

    DATE_AND_TIME,
    NUMERIC,
    STRING;

    public String value() {
        return name();
    }

    public static ParameterValueType fromValue(String v) {
        return valueOf(v);
    }

}
