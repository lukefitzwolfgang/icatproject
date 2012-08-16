/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package icat.rest.ws.converter;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import org.apache.log4j.Logger;
import org.icatproject.core.entity.Dataset;
import org.icatproject.core.entity.Investigation;

/**
 *
 * @author 3qr
 */
@XmlRootElement(name = "icat-run")
public class DatafileKeys {

  private static Logger log = Logger.getLogger(DatafileKeys.class);
  @XmlElement
  private String facil;
  @XmlElement
  private String inst;
  @XmlElement
  private String prop;
  @XmlElement
  private String coll;
  @XmlElement
  private String run;

  public DatafileKeys() {
  }

  public DatafileKeys(Dataset dataset) {
    Investigation investigation = dataset.getInvestigation();
    this.facil = investigation.getFacility().getName();
    this.inst = investigation.getInstrument().getName();
    this.prop = investigation.getName();
    this.coll = investigation.getVisitId();
    this.run = dataset.getName();
  }
}
