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
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import org.apache.log4j.Logger;
import uk.icat3.entity.Investigation;
import uk.icat3.exceptions.BadParameterException;
import uk.icat3.exceptions.IcatInternalException;
import uk.icat3.exceptions.InsufficientPrivilegesException;
import uk.icat3.search.InvestigationSearch;
import uk.icat3.search.Search;

/**
 *
 * @author 3qr
 */
@Stateless
@Path("experiment/")
public class InvestigationFacadeREST extends AbstractFacade<Investigation> {

  @PersistenceContext(unitName = "icat4PU")
  private EntityManager em;
  private static Logger log = Logger.getLogger(InvestigationFacadeREST.class);

  public InvestigationFacadeREST() {
    super(Investigation.class);
  }

  @GET
  @Path("{facil}")
  @Produces({"application/xml", "application/json"})
  public InstrumentConverter getInstruments(@PathParam("facil") String facility) {
    Collection<String> list = InvestigationSearch.listAllInstruments(em);
    return new InstrumentConverter(list);
  }

  @GET
  @Path("{facil}/{inst}")
  @Produces({"application/xml", "application/json"})
  public ProposalConverter getProposals(@PathParam("facil") String facility, @PathParam("inst") String instrument, @Context HttpServletRequest requestContext) {
    try {
      String yourIP = requestContext.getRemoteAddr().toString();
      log.info("Beginning getProposals: " + yourIP);
      String query = "Investigation [instrument = ':instrument']";
      query = query.replace(":instrument", instrument);
      List<?> results = Search.search(RestfulConstant.RESTFUL_USER, query, em);
      ArrayList<String> list = new ArrayList<String>();
      Iterator iter = results.iterator();
      while (iter.hasNext()) {
        Investigation inv = (Investigation) iter.next();
        list.add(inv.getInvNumber());
      }
      log.info("Ending getProposals, found " + list.size() + " proposals for instrment " + instrument);
      return new ProposalConverter(list);
    } catch (BadParameterException ex) {
      log.error("In getProposals: got BadParameterException: " + ex.getMessage());
      return new ProposalConverter();
    } catch (IcatInternalException ex) {
      log.error("In getProposals: got IcatInternalException: " + ex.getMessage());
      return new ProposalConverter();
    } catch (InsufficientPrivilegesException ex) {
      log.error("In getProposals: got InsufficientPrivilegesException: " + ex.getMessage());
      return new ProposalConverter();
    }
  }

  @GET
  @Path("{facil}/{inst}/{prop}")
  @Produces({"application/xml", "application/json"})
  public RunConverter getRuns(@PathParam("facil") String facility, @PathParam("inst") String instrument, @PathParam("prop") String proposal, @Context HttpServletRequest requestContext) {
    try {
      String yourIP = requestContext.getRemoteAddr().toString();
      log.info("Beginning getProposals: " + yourIP);
      String query = "Investigation [instrument = ':instrument' AND invNumber = ':proposal' AND invParamName = 'run_number_range' ]";
      query = query.replace(":instrument", instrument).replace(":proposal", proposal);
      List<?> results = Search.search(RestfulConstant.RESTFUL_USER, query, em);
      ArrayList<String> list = new ArrayList<String>();
      Iterator iter = results.iterator();
      while (iter.hasNext()) {
        Investigation inv = (Investigation) iter.next();
        list.add(inv.getInvParamValue());
      }
      log.info("Ending getProposals, found " + list.size() + " proposals for instrment " + instrument);
      return new RunConverter(list);
    } catch (BadParameterException ex) {
      log.error("In getProposals: got BadParameterException: " + ex.getMessage());
      return new RunConverter();
    } catch (IcatInternalException ex) {
      log.error("In getProposals: got IcatInternalException: " + ex.getMessage());
      return new RunConverter();
    } catch (InsufficientPrivilegesException ex) {
      log.error("In getProposals: got InsufficientPrivilegesException: " + ex.getMessage());
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
      String query = "Investigation [instrument = ':instrument' AND invNumber = ':proposal']";
      query = query.replace(":instrument", instrument).replace(":proposal", proposal);
      List<?> results = Search.search(RestfulConstant.RESTFUL_USER, query, em);
      log.info("Ending getMeta, found " + results.size() + " proposals for instrment " + instrument);
      if (results.size() != 1) {
        return new InvestigationConverter();
      } else {
        Iterator iter = results.iterator();
        Investigation inv = (Investigation) iter.next();
        return new InvestigationConverter(inv);

      }
    } catch (BadParameterException ex) {
      log.error("In getMeta: got BadParameterException: " + ex.getMessage());
      return new InvestigationConverter();
    } catch (IcatInternalException ex) {
      log.error("In getMeta: got IcatInternalException: " + ex.getMessage());
      return new InvestigationConverter();
    } catch (InsufficientPrivilegesException ex) {
      log.error("In getMeta: got InsufficientPrivilegesException: " + ex.getMessage());
      return new InvestigationConverter();
    }
  }

  
  @Override
  protected EntityManager getEntityManager() {
    return em;
  }
}
