/*
 * StudyInvestigationPK.java
 *
 * Created on 08 February 2007, 09:48
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package uk.icat3.entity;

import java.io.Serializable;
import java.math.BigInteger;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * Primary Key class StudyInvestigationPK for entity class StudyInvestigation
 * 
 * @author gjd37
 */
@Embeddable
public class StudyInvestigationPK implements Serializable {

    @Column(name = "STUDY_ID", nullable = false)
    private BigInteger studyId;

    @Column(name = "INVESTIGATION_ID", nullable = false)
    private BigInteger investigationId;
    
    /** Creates a new instance of StudyInvestigationPK */
    public StudyInvestigationPK() {
    }

    /**
     * Creates a new instance of StudyInvestigationPK with the specified values.
     * @param investigationId the investigationId of the StudyInvestigationPK
     * @param studyId the studyId of the StudyInvestigationPK
     */
    public StudyInvestigationPK(BigInteger investigationId, BigInteger studyId) {
        this.investigationId = investigationId;
        this.studyId = studyId;
    }

    /**
     * Gets the studyId of this StudyInvestigationPK.
     * @return the studyId
     */
    public BigInteger getStudyId() {
        return this.studyId;
    }

    /**
     * Sets the studyId of this StudyInvestigationPK to the specified value.
     * @param studyId the new studyId
     */
    public void setStudyId(BigInteger studyId) {
        this.studyId = studyId;
    }

    /**
     * Gets the investigationId of this StudyInvestigationPK.
     * @return the investigationId
     */
    public BigInteger getInvestigationId() {
        return this.investigationId;
    }

    /**
     * Sets the investigationId of this StudyInvestigationPK to the specified value.
     * @param investigationId the new investigationId
     */
    public void setInvestigationId(BigInteger investigationId) {
        this.investigationId = investigationId;
    }

    /**
     * Returns a hash code value for the object.  This implementation computes 
     * a hash code value based on the id fields in this object.
     * @return a hash code value for this object.
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (this.investigationId != null ? this.investigationId.hashCode() : 0);
        hash += (this.studyId != null ? this.studyId.hashCode() : 0);
        return hash;
    }

    /**
     * Determines whether another object is equal to this StudyInvestigationPK.  The result is 
     * <code>true</code> if and only if the argument is not null and is a StudyInvestigationPK object that 
     * has the same id field values as this object.
     * @param object the reference object with which to compare
     * @return <code>true</code> if this object is the same as the argument;
     * <code>false</code> otherwise.
     */
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof StudyInvestigationPK)) {
            return false;
        }
        StudyInvestigationPK other = (StudyInvestigationPK)object;
        if (this.investigationId != other.investigationId && (this.investigationId == null || !this.investigationId.equals(other.investigationId))) return false;
        if (this.studyId != other.studyId && (this.studyId == null || !this.studyId.equals(other.studyId))) return false;
        return true;
    }

    /**
     * Returns a string representation of the object.  This implementation constructs 
     * that representation based on the id fields.
     * @return a string representation of the object.
     */
    @Override
    public String toString() {
        return "uk.icat3.entity.StudyInvestigationPK[investigationId=" + investigationId + ", studyId=" + studyId + "]";
    }
    
}
