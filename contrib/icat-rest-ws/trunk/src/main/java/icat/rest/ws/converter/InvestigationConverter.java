/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package icat.rest.ws.converter;

import java.util.ArrayList;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import org.apache.log4j.Logger;


/**
 *
 * @author 3qr
 */
@XmlRootElement(name = "proposals")
public class InvestigationConverter {

  private static Logger log = Logger.getLogger(InvestigationConverter.class);

  @XmlElement
  private ArrayList<String> proposal;

  public InvestigationConverter() {
  }

  public InvestigationConverter(ArrayList<String> list) {
    proposal = list;
  }
  
}
