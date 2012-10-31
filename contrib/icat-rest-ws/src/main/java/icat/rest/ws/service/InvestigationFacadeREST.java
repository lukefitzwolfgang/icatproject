/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package icat.rest.ws.service;

import icat.rest.ws.converter.InstrumentConverter;
import icat.rest.ws.converter.InvestigationConverter;
import icat.rest.ws.converter.ProposalConverter;
import icat.rest.ws.converter.RunConverter;

import java.util.ArrayList;
import java.util.Iterator;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

import org.icatproject.core.entity.Investigation;
import org.icatproject.core.manager.SearchResponse;
import org.icatproject.core.manager.BeanManager;
import org.icatproject.core.IcatException;
import org.icatproject.core.entity.Instrument;

/**
 *
 * @author 3qr
 */
@Stateless
@Path("experiment/")
public class InvestigationFacadeREST extends AbstractFacade<Investigation> {

  @PersistenceContext(unitName = "icat4PU")
  private EntityManager em;
  private static final Logger log = Logger.getLogger(InvestigationFacadeREST.class);

  public InvestigationFacadeREST() {
    super(Investigation.class);
  }

  @GET
  @Path("{facil}")
  @Produces({"application/xml", "application/json"})
  public InstrumentConverter getInstruments(@PathParam("facil") String facility, @Context HttpServletRequest requestContext) {
    ArrayList<String> list = new ArrayList<String>();
    try {
      String yourIP = requestContext.getRemoteAddr().toString();
      log.info("Beginning getInstruments(): " + yourIP);
      String query = "Instrument.name ORDER BY name <-> Facility [ name = ':facility']";
      query = query.replace(":facility", facility);
      SearchResponse results = BeanManager.search(RestfulConstant.RESTFUL_USER, query, em);
      Iterator iter = results.getList().iterator();
      while (iter.hasNext()) {
        list.add((String) iter.next());
      }
      log.info("Ending getInstruments(): " + list.size());
    } catch (IcatException ex) {
      log.error("In getInstruments: got IcatException " + ex.getMessage());
    } catch (Exception ex) {
      log.error("In getInstruments: got Exception " + ex.getMessage());
    } finally {
      return new InstrumentConverter(list);
    }
  }

  @GET
  @Path("{facil}/{inst}")
  @Produces({"application/xml", "application/json"})
  public ProposalConverter getProposals(@PathParam("facil") String facility, @PathParam("inst") String instrument, @Context HttpServletRequest requestContext) {
    try {
      String yourIP = requestContext.getRemoteAddr().toString();
      log.info("Beginning getProposals: " + yourIP);
      String query = "DISTINCT Investigation.name ORDER BY name <-> Instrument [ name = ':instrument']";
      query = query.replace(":instrument", instrument);
      SearchResponse results = BeanManager.search(RestfulConstant.RESTFUL_USER, query, em);
      ArrayList<String> list = new ArrayList<String>();
      Iterator iter = results.getList().iterator();
      while (iter.hasNext()) {
        list.add((String) iter.next());
      }
      log.info("Ending getProposals, found " + list.size() + " proposals for instrment " + instrument);
      return new ProposalConverter(list);
    } catch (IcatException ex) {
      log.error("In getProposals: got IcatException " + ex.getMessage());
      return new ProposalConverter();
    }
  }

  @GET
  @Path("{facil}/{inst}/{prop}")
  @Produces({"application/xml", "application/json"})
  public RunConverter getRuns(@PathParam("facil") String facility, @PathParam("inst") String instrument, @PathParam("prop") String proposal, @Context HttpServletRequest requestContext) {
    try {
      String yourIP = requestContext.getRemoteAddr().toString();
      String runRange = "";
      log.info("Beginning getRuns: " + yourIP);
      //String query = "Investigation INCLUDE Dataset [name = ':proposal'] <-> Instrument [ name = ':instrument'] order by Dataset.name";
      String query = "DISTINCT Dataset.name ORDER BY name <-> Investigation [name = ':proposal'] <-> Instrument [ name = ':instrument']";
      query = query.replace(":instrument", instrument).replace(":proposal", proposal);
      SearchResponse results = BeanManager.search(RestfulConstant.RESTFUL_USER, query, em);
      Iterator iter = results.getList().iterator();
      String oldRunNumber = "";

      boolean firstRun = true;
      boolean inIncrement = false;
      while (iter.hasNext()) {
        String runNumber = (String) iter.next();
        if (firstRun) {
          runRange = runNumber;
          firstRun = false;
        } else {
          if (Long.parseLong(runNumber) == Long.parseLong(oldRunNumber) + 1) {
            if (!inIncrement) {
              runRange = runRange.concat("-");
              inIncrement = true;
            }
          } else {
            if (inIncrement) {
              runRange = runRange.concat(oldRunNumber);
              inIncrement = false;
            }
            runRange = runRange.concat(", ").concat(runNumber);
          }
        }
        oldRunNumber = runNumber;
      }
      //Be careful here, we might have exited in the middle of an incremental range. If so, tack on the last number.
      if (inIncrement) {
        runRange = runRange.concat(oldRunNumber);
      }
      log.info("Ending getRuns, found run range: " + runRange + " for instrment " + instrument + " and proposal " + proposal);
      return new RunConverter(runRange);
    } catch (IcatException ex) {
      log.error("In getRuns: got IcatException " + ex.getMessage());
      return new RunConverter();
    }
  }

  @GET
  @Path("{facil}/{inst}/{prop}/{meta}")
  @Produces({"application/xml", "application/json"})
  public InvestigationConverter getMeta(@PathParam("facil") String facility, @PathParam("inst") String instrument, @PathParam("prop") String proposal, @Context HttpServletRequest requestContext) {
    try {
      String yourIP = requestContext.getRemoteAddr().toString();
      log.info("Beginning getMeta: " + yourIP);
      String query = "Investigation INCLUDE 1 [ name = ':proposal' ] <-> Instrument [ name = ':instrument']";
      query = query.replace(":instrument", instrument).replace(":proposal", proposal);
      SearchResponse results = BeanManager.search(RestfulConstant.RESTFUL_USER, query, em);
      log.info("Ending getMeta, found " + results.getList().size() + " proposals for instrument " + instrument);
      if (results.getList().size() != 1) {
        return new InvestigationConverter();
      } else {
        Iterator iter = results.getList().iterator();
        while (iter.hasNext()) {
          Investigation inv = (Investigation) iter.next();
          return new InvestigationConverter(inv);
        }
        return new InvestigationConverter();
      }
    } catch (IcatException ex) {
      log.error("In getMeta: got IcatException " + ex.getMessage());
      return new InvestigationConverter();
    }
  }

  @Override
  protected EntityManager getEntityManager() {
    return em;
  }
}
