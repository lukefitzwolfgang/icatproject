/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package uk.icat3.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlSeeAlso;
import uk.icat3.exceptions.ValidationException;

/**
 *
 * This entity class is base class for Digital Object Identifier.
 * NOTE: Have used Table Per Subclass Inheritance Strategy. 
 * @author Mr. Srikanth Nagella
 */
@Entity
@Inheritance(strategy=InheritanceType.JOINED)
@DiscriminatorColumn(name="DCOL",discriminatorType=DiscriminatorType.STRING)
@Table(name = "DOI")
@NamedQueries({
    @NamedQuery(name = "Doi.findAll", query = "SELECT d FROM Doi d"),
    @NamedQuery(name = "Doi.findById", query = "SELECT d FROM Doi d WHERE d.id = :id"),
    @NamedQuery(name = "Doi.findByName", query = "SELECT d FROM Doi d WHERE d.name = :name"),
    @NamedQuery(name = "Doi.findByNameAndServer", query = "SELECT d FROM Doi d WHERE d.name = :name AND d.doiServer.serverName = :serverName")
})
@XmlSeeAlso({InvestigationDoi.class,DatasetDoi.class,DatafileDoi.class})
public class Doi implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE,generator="ICAT_AUTHORISATION_SEQ")
    @Column(name = "ID")
    protected Long id;
    @Column(name = "NAME")
    protected String name;
    @JoinColumn(name = "SERVER_ID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    protected DoiServer doiServer;

    public Doi() {
    }

    public Doi(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DoiServer getDoiServer() {
        return doiServer;
    }

    public void setDoiServer(DoiServer doiServer) {
        this.doiServer = doiServer;
    }

    /**
     * This returns whether the class is valid, should be overridden by the
     * subclass
     * @return
     */
    public boolean isValid(EntityManager manager) throws ValidationException{
        return false;
    }
}
