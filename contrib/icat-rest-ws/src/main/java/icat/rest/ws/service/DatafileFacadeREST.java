
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package icat.rest.ws.service;

import icat.rest.ws.converter.DatafileKeys;
import icat.rest.ws.converter.DatafileLocation;
import java.util.ArrayList;
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
import org.icatproject.core.entity.Investigation;
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
  public DatafileLocation findFiles(@PathParam("inst") String instrument, @PathParam("run") String run, @QueryParam("contains") String contains, @QueryParam("ext") String ext, @Context HttpServletRequest requestContext) {
    String yourIP = requestContext.getRemoteAddr().toString();
    ArrayList<String> list = new ArrayList<String>();
    try {
      log.info("Beginning findFiles from IP: " + yourIP);
      String filename = instrument + "_" + run + "%";
      log.info("filename: " + filename);
      String query = "Datafile.location <-> Datafile [name LIKE ':filename']";
      query = query.replace(":filename", filename);
      SearchResponse results = BeanManager.search(RestfulConstant.RESTFUL_USER, query, em);
      Iterator iter = results.getList().iterator();
      while (iter.hasNext()) {
        String location = (String) iter.next();
        if (contains != null && !contains.isEmpty() && !location.contains(contains)) {
          location = "";
        }
        if (ext != null && !ext.isEmpty() && !location.endsWith(ext)) {
          location = "";
        }
        if (!location.isEmpty()) {
          list.add(location);
        }
      }
      log.info("Ending getFiles()");
      return new DatafileLocation(list);
    } catch (IcatException ex) {
      log.error("In findFiles: IcatException " + ex.getMessage());
    } finally {
      return new DatafileLocation(list);
    }
  }
  
  @GET
  @Path("filename/{filename}")
  @Produces({"application/xml", "application/json"})
  public DatafileLocation findFileLocation(@PathParam("filename") String filename, @Context HttpServletRequest requestContext) {
    String yourIP = requestContext.getRemoteAddr().toString();
    ArrayList<String> list = new ArrayList<String>();
    try {
      log.info("Beginning findFileLocation from IP: " + yourIP);
      String query = "Datafile.location <-> Datafile [name = ':filename']";
      query = query.replace(":filename", filename);
      SearchResponse results = BeanManager.search(RestfulConstant.RESTFUL_USER, query, em);
      Iterator iter = results.getList().iterator();
      while (iter.hasNext()) {
        String location = (String) iter.next();
        if (!location.isEmpty()) {
          list.add(location);
        }
      }
      log.info("Ending getFileLocation()");
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
      //String query = "DISTINCT Dataset [name = ':run'] <-> Investigation <-> Instrument [name = ':inst']";
      String query = "DISTINCT Investigation  <-> Dataset [name = ':run'] <-> Instrument [name = ':inst']";
      query = query.replace(":run", run).replace(":inst", inst);
      System.out.println("query=" + query);
      SearchResponse results = BeanManager.search(RestfulConstant.RESTFUL_USER, query, em);
      if (results.getList().size() == 1) {
        Iterator iter = results.getList().iterator();
        Investigation inv = (Investigation) iter.next();
        log.info("Ending findFiveKeys, found 5 keys");
        return new DatafileKeys(facil, inst, inv.getName(), inv.getVisitId(), run);
      }
      return new DatafileKeys();
    } catch (IcatException ex) {
      log.error("In findFiveKeys: IcatException " + ex.getMessage());
      return new DatafileKeys();
    }
  }

  @Override
  protected EntityManager getEntityManager() {
    return em;
  }
}
