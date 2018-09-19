/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author 陆宇恒
 */
@Entity
@Table(name = "competition")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Competition.findAll", query = "SELECT c FROM Competition c")
    , @NamedQuery(name = "Competition.findByIdcompetition", query = "SELECT c FROM Competition c WHERE c.idcompetition = :idcompetition")
    , @NamedQuery(name = "Competition.findByName", query = "SELECT c FROM Competition c WHERE c.name = :name")
    , @NamedQuery(name = "Competition.findByHeadcount", query = "SELECT c FROM Competition c WHERE c.headcount = :headcount")
    , @NamedQuery(name = "Competition.findByType", query = "SELECT c FROM Competition c WHERE c.type = :type")
    , @NamedQuery(name = "Competition.findByStartTime", query = "SELECT c FROM Competition c WHERE c.startTime = :startTime")
    , @NamedQuery(name = "Competition.findByEndTime", query = "SELECT c FROM Competition c WHERE c.endTime = :endTime")})
public class Competition implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idcompetition")
    private Integer idcompetition;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "name")
    private String name;
    @Basic(optional = false)
    @NotNull
    @Column(name = "headcount")
    private int headcount;
    @Basic(optional = false)
    @NotNull
    @Column(name = "type")
    private int type;
    @Basic(optional = false)
    @NotNull
    @Column(name = "start_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startTime;
    @Basic(optional = false)
    @NotNull
    @Column(name = "end_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endTime;
    @JoinTable(name = "student_join_competition", joinColumns = {
        @JoinColumn(name = "competition_idcompetition", referencedColumnName = "idcompetition")}, inverseJoinColumns = {
        @JoinColumn(name = "student_idstudent", referencedColumnName = "idstudent")})
    @ManyToMany(cascade=CascadeType.MERGE)
    private Collection<Student> studentCollection;
    @JoinColumn(name = "category_idcategory", referencedColumnName = "idcategory")
    @ManyToOne(optional = false)
    private Category categoryIdcategory;

    public Competition() {
    }

    public Competition(Integer idcompetition) {
        this.idcompetition = idcompetition;
    }

    public Competition(Integer idcompetition, String name, int headcount, int type, Date startTime, Date endTime) {
        this.idcompetition = idcompetition;
        this.name = name;
        this.headcount = headcount;
        this.type = type;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Integer getIdcompetition() {
        return idcompetition;
    }

    public void setIdcompetition(Integer idcompetition) {
        this.idcompetition = idcompetition;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHeadcount() {
        return headcount;
    }

    public void setHeadcount(int headcount) {
        this.headcount = headcount;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    @XmlTransient
    public Collection<Student> getStudentCollection() {
        return studentCollection;
    }

    public void setStudentCollection(Collection<Student> studentCollection) {
        this.studentCollection = studentCollection;
    }

    public Category getCategoryIdcategory() {
        return categoryIdcategory;
    }

    public void setCategoryIdcategory(Category categoryIdcategory) {
        this.categoryIdcategory = categoryIdcategory;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idcompetition != null ? idcompetition.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Competition)) {
            return false;
        }
        Competition other = (Competition) object;
        if ((this.idcompetition == null && other.idcompetition != null) || (this.idcompetition != null && !this.idcompetition.equals(other.idcompetition))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Competition[ idcompetition=" + idcompetition + " ]";
    }
    
}
