/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package icat.rest.ws.converter;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import org.apache.log4j.Logger;

/**
 *
 * @author 3qr
 */
@XmlRootElement(name = "runs")
public class LastRunConverter {

  private static Logger log = Logger.getLogger(LastRunConverter.class);

  @XmlElement
  private BigDecimal lastRun;

  public LastRunConverter() {
  }

  public LastRunConverter(BigDecimal lastRun) {
    this.lastRun = lastRun;
  }
}
