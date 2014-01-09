/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package icat.rest.ws.converter;

import java.util.*;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import org.apache.log4j.Logger;
import org.icatproject.core.entity.Dataset;
import org.icatproject.core.entity.Investigation;

/**
 *
 * @author 3qr
 */
@XmlRootElement(name = "proposal")
public class InvestigationDatasetAllConverter extends InvestigationDatasetMetaConverter {
     
  @XmlElement
  private RunsInfoConverter runs;

  private static Logger log = Logger.getLogger(InvestigationDatasetAllConverter.class);

  public InvestigationDatasetAllConverter() {
  }

  public InvestigationDatasetAllConverter(Investigation inv) {
    super(inv);

    ArrayList<RunInfoConverter> list = new ArrayList<RunInfoConverter>();
    Iterator iter = inv.getDatasets().iterator();
    while (iter.hasNext()) {

      Dataset ds = (Dataset) iter.next();
      log.info("shelly ds name: " + ds.getName());
      RunInfoConverter runInfo = new RunInfoConverter(ds);
      list.add(runInfo);
    }

    Collections.sort(list);
    this.runs = new RunsInfoConverter(list);
  }
}