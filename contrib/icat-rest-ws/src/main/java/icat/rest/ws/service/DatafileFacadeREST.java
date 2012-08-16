/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package icat.rest.ws.service;

import icat.rest.ws.converter.DatafileKeys;
import icat.rest.ws.converter.DatafileLocation;
import icat.rest.ws.converter.LastRunConverter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import org.apache.log4j.Logger;
import org.icatproject.core.IcatException;
import org.icatproject.core.entity.Datafile;
import org.icatproject.core.entity.Dataset;
import org.icatproject.core.manager.BeanManager;
import org.icatproject.core.manager.SearchResponse;

/**
 *
 * @author 3qr
 */
@Stateless
@Path("datafile/")
public class DatafileFacadeREST extends AbstractFacade<Datafile> {

  @PersistenceContext(unitName = "icat4PU")
  private EntityManager em;
  private static Logger log = Logger.getLogger(DatafileFacadeREST.class);

  public DatafileFacadeREST() {
    super(Datafile.class);
  }

//  @GET
//  @Path("{facil}/{inst}")
//  @Produces({"application/xml", "application/json"})
//  public LastRunConverter findLastRun(@PathParam("inst") String inst, @Context HttpServletRequest requestContext) {
//    String yourIP = requestContext.getRemoteAddr().toString();
//    log.info("Beginning findLastRun IP: " + yourIP);
//    String query = "SELECT MAX(datafile_parameter.numeric_value) "
//            + "FROM datafile_parameter, datafile, dataset, investigation "
//            + "WHERE dataset.investigation_id = investigation.id AND dataset.id = datafile.dataset_id "
//            + "AND datafile.id = datafile_parameter.datafile_id AND datafile_parameter.name = 'run_number' "
//            + "AND investigation.instrument = ?";
//    log.info("findLastRun query = " + query);
//    BigDecimal lastRun = (BigDecimal) getEntityManager().createNativeQuery(query).setParameter(1, inst).getSingleResult();
//    return new LastRunConverter(lastRun);
//  }

  @GET
  @Path("{facil}/{inst}/{run}")
  @Produces({"application/xml", "application/json"})
  public DatafileLocation findFileLocation(@PathParam("inst") String inst, @PathParam("run") String run, @QueryParam("contains") String contains, @QueryParam("ext") String ext, @Context HttpServletRequest requestContext) {
    String yourIP = requestContext.getRemoteAddr().toString();
    ArrayList<String> list = new ArrayList<String>();
    try {
      log.info("Beginning findFileLocation from IP: " + yourIP);
      String query = "Dataset INCLUDE Datafile [name = ':run'] <-> Investigation  <-> Instrument [name = ':inst']";
      query = query.replace(":run", run).replace(":inst", inst);
      SearchResponse results = BeanManager.search(RestfulConstant.RESTFUL_USER, query, em);
      Iterator iter = results.getList().iterator();
      while (iter.hasNext()) {
        Dataset ds = (Dataset) iter.next();
        Iterator iter2 = ds.getDatafiles().iterator();
        while (iter2.hasNext()) {
          Datafile file = (Datafile) iter2.next();
          String fileName = file.getName();
          String location = file.getLocation();
          log.info("getFileLocationImpl: file name + " + file.getName());
          if (contains != null && !contains.isEmpty() && !fileName.contains(contains)) {
            location = "";
          }
          if (ext != null && !ext.isEmpty() && !fileName.endsWith(ext)) {
            location = "";
          }
          if (!location.isEmpty()) {
            list.add(location);
          }
        }
      }
      log.info("Ending getFileLocation()");
      return new DatafileLocation(list);
    } catch (IcatException ex) {
      log.error("In findFileLocation: IcatException " + ex.getMessage());
    } finally {
      return new DatafileLocation(list);
    }
  }

  @GET
  @Path("facil/{facil}/inst/{inst}/run/{run}")
  @Produces({"application/xml", "application/json"})
  public DatafileKeys findFiveKeys(@PathParam("facil") String facil, @PathParam("inst") String inst, @PathParam("run") String run, @Context HttpServletRequest requestContext) {
    try {
      String yourIP = requestContext.getRemoteAddr().toString();
      log.info("Beginning findFiveKeys IP: " + yourIP);
      String query = "Dataset [name = ':run'] <-> Investigation <-> Instrument [name = ':inst']";
      query = query.replace(":run", run).replace(":inst", inst);
      SearchResponse results = BeanManager.search(RestfulConstant.RESTFUL_USER, query, em);
      if (results.getList().size() == 1) {
        Iterator iter = results.getList().iterator();
        Dataset dataset = (Dataset) iter.next();
        return new DatafileKeys(dataset);
      }
      return new DatafileKeys();
    } catch (IcatException ex) {
      log.error("In findFileLocation: IcatException " + ex.getMessage());
      return new DatafileKeys();
    }
  }
  
  @Override
  protected EntityManager getEntityManager() {
    return em;
  }
}
