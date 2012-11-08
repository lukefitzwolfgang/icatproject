
package org.icatproject;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for destType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="destType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="PUBSUB"/>
 *     &lt;enumeration value="P2P"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "destType")
@XmlEnum
public enum DestType {

    PUBSUB("PUBSUB"),
    @XmlEnumValue("P2P")
    P_2_P("P2P");
    private final String value;

    DestType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static DestType fromValue(String v) {
        for (DestType c: DestType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
