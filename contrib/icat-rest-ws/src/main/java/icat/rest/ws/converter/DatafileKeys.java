/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package icat.rest.ws.converter;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import org.apache.log4j.Logger;
import uk.icat3.entity.Datafile;
import uk.icat3.entity.Investigation;

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

  public DatafileKeys(Datafile datafile, String run) {
    Investigation investigation = datafile.getDataset().getInvestigation();
    this.facil = investigation.getFacility();
    this.inst = investigation.getInstrument();
    this.prop = investigation.getInvNumber();
    this.coll = investigation.getVisitId();
    this.run = run;
  }
}
