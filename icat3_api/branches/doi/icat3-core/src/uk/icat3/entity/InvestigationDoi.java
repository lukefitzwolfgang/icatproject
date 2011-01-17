/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package uk.icat3.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import uk.icat3.exceptions.ValidationException;

/**
 * This entity class is implementation of Investigation Digital Object Identifier. This
 * inherits from Doi class.
 * NOTE: Have used Table Per Subclass Inheritance Strategy.
 * @author Mr. Srikanth Nagella
 */
@Entity
@Table(name = "INVESTIGATION_DOI")
@NamedQueries({
    @NamedQuery(name = "InvestigationDoi.findAll", query = "SELECT i FROM InvestigationDoi i"),
    @NamedQuery(name = "InvestigationDoi.findById", query = "SELECT i FROM InvestigationDoi i WHERE i.id = :id"),
})
public class InvestigationDoi extends Doi implements Serializable {

    @ManyToOne
    private Investigation investigation;
    public InvestigationDoi() {
        super();
    }

    public InvestigationDoi(Long id) {
        super(id);
    }

    public Investigation getInvestigation() {
        return investigation;
    }

    public void setInvestigation(Investigation investigation) {
        this.investigation = investigation;
    }


    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof InvestigationDoi)) {
            return false;
        }
        InvestigationDoi other = (InvestigationDoi) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "uk.icat3.entity.InvestigationDoi[id=" + id + "]";
    }

    @Override
    public boolean isValid(EntityManager manager) throws ValidationException{
        //Check if the investigation is valid
        Investigation inv = getInvestigation();
        return inv.isValid(manager, false);
    }

}
