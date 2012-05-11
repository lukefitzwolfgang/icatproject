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
 * This entity class is implementation of Dataset Digital Object Identifier. This
 * inherits from Doi class.
 * NOTE: Have used Table Per Subclass Inheritance Strategy.
 * @author Mr. Srikanth Nagella
 */
@Entity
@Table(name = "DATAFILE_DOI")
@NamedQueries({
    @NamedQuery(name = "DatafileDoi.findAll", query = "SELECT d FROM DatafileDoi d"),
    @NamedQuery(name = "DatafileDoi.findById", query = "SELECT d FROM DatafileDoi d WHERE d.id = :id")})
public class DatafileDoi extends Doi implements Serializable {

    @ManyToOne
    private Datafile datafile;

    public DatafileDoi() {
        super();
    }

    public DatafileDoi(Long id) {
        super(id);
    }

    public Datafile getDatafile() {
        return datafile;
    }

    public void setDatafile(Datafile datafile) {
        this.datafile = datafile;
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
        if (!(object instanceof DatafileDoi)) {
            return false;
        }
        DatafileDoi other = (DatafileDoi) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "uk.icat3.entity.DatafileDoi[id=" + id + "]";
    }

    @Override
    public boolean isValid(EntityManager manager) throws ValidationException{
        //Check if the datafile is valid
        Datafile dfile = getDatafile();
        return dfile.isValid(manager, false);
    }
}
