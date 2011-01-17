/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package uk.icat3.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * This entity class is implementation of the Digital Object Identifier server
 * information.
 * @author Mr. Srikanth Nagella
 */
@Entity
@Table(name = "DOI_SERVER")
@NamedQueries({
    @NamedQuery(name = "DoiServer.findAll", query = "SELECT d FROM DoiServer d"),
    @NamedQuery(name = "DoiServer.findById", query = "SELECT d FROM DoiServer d WHERE d.id = :id"),
    @NamedQuery(name = "DoiServer.findByServerName", query = "SELECT d FROM DoiServer d WHERE d.serverName = :serverName"),
    @NamedQuery(name = "DoiServer.findByServerUrl", query = "SELECT d FROM DoiServer d WHERE d.serverUrl = :serverUrl")})
public class DoiServer implements Serializable {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy=GenerationType.SEQUENCE,generator="ICAT_AUTHORISATION_SEQ")
    private Long id;
    @Column(name = "SERVER_NAME")
    private String serverName;
    @Column(name = "SERVER_URL")
    private String serverUrl;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "doiServer")
    private Collection<Doi> doiCollection;

    public DoiServer() {
    }

    public DoiServer(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public String getServerUrl() {
        return serverUrl;
    }

    public void setServerUrl(String serverUrl) {
        this.serverUrl = serverUrl;
    }

    public Collection<Doi> getDoiCollection() {
        return doiCollection;
    }

    public void setDoiCollection(Collection<Doi> doiCollection) {
        this.doiCollection = doiCollection;
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
        if (!(object instanceof DoiServer)) {
            return false;
        }
        DoiServer other = (DoiServer) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "uk.icat3.entity.DoiServer[id=" + id + "]";
    }

}
