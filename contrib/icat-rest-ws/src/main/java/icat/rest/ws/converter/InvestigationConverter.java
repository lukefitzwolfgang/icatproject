/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package icat.rest.ws.converter;

import javax.xml.bind.annotation.XmlRootElement;
import org.apache.log4j.Logger;
import org.icatproject.core.entity.Investigation;

/**
 *
 * @author 3qr
 */
@XmlRootElement(name = "metadata")
public class InvestigationConverter extends Investigation {

  private static Logger log = Logger.getLogger(InvestigationConverter.class);

  public InvestigationConverter() {
  }

  public InvestigationConverter(Investigation inv) {
    super.setFacility(inv.getFacility());
    super.setInstrument(inv.getInstrument());
    super.setName(inv.getName());
    super.setTitle(inv.getTitle());
    super.setVisitId(inv.getVisitId());
    super.setType(inv.getType());
    super.setParameters(inv.getParameters());
  }
}
