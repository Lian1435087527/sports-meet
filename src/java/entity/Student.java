/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author 陆宇恒
 */
@Entity
@Table(name = "student")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Student.findAll", query = "SELECT s FROM Student s")
    , @NamedQuery(name = "Student.findByIdstudent", query = "SELECT s FROM Student s WHERE s.idstudent = :idstudent")
    , @NamedQuery(name = "Student.findByName", query = "SELECT s FROM Student s WHERE s.name = :name")
    , @NamedQuery(name = "Student.findBySex", query = "SELECT s FROM Student s WHERE s.sex = :sex")
    , @NamedQuery(name = "Student.findByAdmin", query = "SELECT s FROM Student s WHERE s.admin = :admin")})
public class Student implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "idstudent")
    private Integer idstudent;
    @Size(max = 10)
    @Column(name = "name")
    private String name;
    @Column(name = "sex")
    private Character sex;
    @Basic(optional = false)
    @NotNull
    @Column(name = "admin")
    private short admin;
    @ManyToMany(cascade=CascadeType.MERGE,mappedBy = "studentCollection")
    private Collection<Competition> competitionCollection;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "student")
    private Account account;

    public Student() {
    }

    public Student(Integer idstudent) {
        this.idstudent = idstudent;
    }

    public Student(Integer idstudent, short admin) {
        this.idstudent = idstudent;
        this.admin = admin;
    }

    public Integer getIdstudent() {
        return idstudent;
    }

    public void setIdstudent(Integer idstudent) {
        this.idstudent = idstudent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Character getSex() {
        return sex;
    }

    public void setSex(Character sex) {
        this.sex = sex;
    }

    public short getAdmin() {
        return admin;
    }

    public void setAdmin(short admin) {
        this.admin = admin;
    }

    @XmlTransient
    public Collection<Competition> getCompetitionCollection() {
        return competitionCollection;
    }

    public void setCompetitionCollection(Collection<Competition> competitionCollection) {
        this.competitionCollection = competitionCollection;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idstudent != null ? idstudent.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Student)) {
            return false;
        }
        Student other = (Student) object;
        if ((this.idstudent == null && other.idstudent != null) || (this.idstudent != null && !this.idstudent.equals(other.idstudent))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Student[ idstudent=" + idstudent + " ]";
    }
    
}
