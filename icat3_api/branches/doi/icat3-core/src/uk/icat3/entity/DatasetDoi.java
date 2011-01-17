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
 *
 * This entity class is implementation of Datafile Digital Object Identifier. This
 * inherits from Doi class.
 * NOTE: Have used Table Per Subclass Inheritance Strategy.
 * @author Mr. Srikanth Nagella
 */
@Entity
@Table(name = "DATASET_DOI")
@NamedQueries({
    @NamedQuery(name = "DatasetDoi.findAll", query = "SELECT d FROM DatasetDoi d"),
    @NamedQuery(name = "DatasetDoi.findById", query = "SELECT d FROM DatasetDoi d WHERE d.id = :id"),
})
public class DatasetDoi extends Doi implements Serializable {

    @ManyToOne
    private Dataset dataset;
    
    public DatasetDoi() {
        super();
    }

    public DatasetDoi(Long id){
        super(id);
    }

    public Dataset getDataset() {
        return dataset;
    }

    public void setDataset(Dataset dataset) {
        this.dataset = dataset;
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
        if (!(object instanceof DatasetDoi)) {
            return false;
        }
        DatasetDoi other = (DatasetDoi) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "uk.icat3.entity.DatasetDoi[id=" + id + "]";
    }

    @Override
    public boolean isValid(EntityManager manager) throws ValidationException{
        //Check if the dataset is valid
        Dataset dset = getDataset();
        return dset.isValid(manager, false);
    }
}
