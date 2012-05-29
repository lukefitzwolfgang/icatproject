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
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import org.apache.log4j.Logger;
import uk.icat3.entity.Datafile;
import uk.icat3.search.DatafileSearch;

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

  @GET
  @Path("{facil}/{inst}")
  @Produces({"application/xml", "application/json"})
  public LastRunConverter findLastRun(@PathParam("inst") String inst, @Context HttpServletRequest requestContext) {
    String yourIP = requestContext.getRemoteAddr().toString();
    log.info("Beginning findLastRun IP: " + yourIP);
    String query = "SELECT MAX(datafile_parameter.numeric_value) "
            + "FROM datafile_parameter, datafile, dataset, investigation "
            + "WHERE dataset.investigation_id = investigation.id AND dataset.id = datafile.dataset_id "
            + "AND datafile.id = datafile_parameter.datafile_id AND datafile_parameter.name = 'run_number' "
            + "AND investigation.instrument = ?";
    log.info("findLastRun query = " + query);
    BigDecimal lastRun = (BigDecimal) getEntityManager().createNativeQuery(query).setParameter(1, inst).getSingleResult();
    return new LastRunConverter(lastRun);
  }
  
  @GET
  @Path("{facil}/{inst}/{run}")
  @Produces({"application/xml", "application/json"})
  public DatafileLocation findFileLocation(@PathParam("inst") String inst, @PathParam("run") String run, @QueryParam("contains") String contains, @QueryParam("ext") String ext, @Context HttpServletRequest requestContext) {
    String yourIP = requestContext.getRemoteAddr().toString();
    log.info("Beginning findFileLocation from IP: " + yourIP);
    Collection<String> instruments = new ArrayList<String>();
    instruments.add(inst);
    float runNumber = Float.parseFloat(run);
    Collection<Datafile> files = DatafileSearch.searchByRunNumber(RestfulConstant.RESTFUL_USER, instruments, runNumber, runNumber, em);
    Iterator it = files.iterator();
    ArrayList<String> list = new ArrayList<String>();
    while (it.hasNext()) {
      Datafile file = (Datafile) it.next();
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
    log.info("Ending getFileLocation()");
    return new DatafileLocation(list);
  }

  @GET
  @Path("facil/{facil}/inst/{inst}/run/{run}")
  @Produces({"application/xml", "application/json"})
  public DatafileKeys findFiveKeys(@PathParam("facil") String facil, @PathParam("inst") String inst, @PathParam("run") String run, @Context HttpServletRequest requestContext) {
    String yourIP = requestContext.getRemoteAddr().toString();
    log.info("Beginning findFiveKeys IP: " + yourIP);
    Collection<String> instruments = new ArrayList<String>();
    instruments.add(inst);
    float runNumber = Float.parseFloat(run);
    Collection<Datafile> datafiles = DatafileSearch.searchByRunNumber(RestfulConstant.RESTFUL_USER, instruments, runNumber, runNumber, em);
    Iterator it = datafiles.iterator();
    while (it.hasNext()) {
      Datafile datafile = (Datafile) it.next();
      return new DatafileKeys(datafile, run);
    }
    return new DatafileKeys();
  }

//  @GET
//  @Path("startRun/{startRun}/endRun/{endRun}")
//  @Produces({"application/xml", "application/json"})
//  public Collection<Datafile> findByRunNumber(@PathParam("startRun") String startRun, @PathParam("endRun") String endRun) {
//    log.info("Beginning DatafileFacadeREST.searchByRunNumber()");
//    Collection<String> instruments = new ArrayList<String>();
//    instruments.add("PG3");
//    Collection<Datafile> files = DatafileSearch.searchByRunNumber(RestfulConstant.RESTFUL_USER, instruments, Float.valueOf(startRun.trim()).floatValue(), Float.valueOf(endRun.trim()).floatValue(), em);
//    log.info("Ending DatafileFacadeREST.searchByRunNumber()");
//    return files;
//  }


  @java.lang.Override
  protected EntityManager getEntityManager() {
    return em;
  }

}
