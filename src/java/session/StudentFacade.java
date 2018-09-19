/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.Competition;
import entity.Student;
import java.util.Collection;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author 陆宇恒
 */
@Stateless
public class StudentFacade extends AbstractFacade<Student> {

    @PersistenceContext(unitName = "sports-meetPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public StudentFacade() {
        super(Student.class);
    }
    
    public Collection<Competition> getCompetitionCollection(entity.Student stu) {
        return stu.getCompetitionCollection();
    }

    public void setCompetitionCollection(entity.Student stu, Collection<Competition> competitionCollection) {
        stu.setCompetitionCollection(competitionCollection);
        getEntityManager().merge(stu);
        getEntityManager().flush();
    }
}
