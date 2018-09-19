/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author 陆宇恒
 */
@Entity
@Table(name = "account")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Account.findAll", query = "SELECT a FROM Account a")
    , @NamedQuery(name = "Account.findByPassword", query = "SELECT a FROM Account a WHERE a.password = :password")
    , @NamedQuery(name = "Account.findByStudentIdstudent", query = "SELECT a FROM Account a WHERE a.studentIdstudent = :studentIdstudent")})
public class Account implements Serializable {

    private static final long serialVersionUID = 1L;
    @Size(max = 20)
    @Column(name = "password")
    private String password;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "student_idstudent")
    private Integer studentIdstudent;
    @JoinColumn(name = "student_idstudent", referencedColumnName = "idstudent", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Student student;

    public Account() {
    }

    public Account(Integer studentIdstudent) {
        this.studentIdstudent = studentIdstudent;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getStudentIdstudent() {
        return studentIdstudent;
    }

    public void setStudentIdstudent(Integer studentIdstudent) {
        this.studentIdstudent = studentIdstudent;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (studentIdstudent != null ? studentIdstudent.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Account)) {
            return false;
        }
        Account other = (Account) object;
        if ((this.studentIdstudent == null && other.studentIdstudent != null) || (this.studentIdstudent != null && !this.studentIdstudent.equals(other.studentIdstudent))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Account[ studentIdstudent=" + studentIdstudent + " ]";
    }
    
}
