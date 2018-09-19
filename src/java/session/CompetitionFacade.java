/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.Competition;
import java.util.Collection;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author 陆宇恒
 */
@Stateless
public class CompetitionFacade extends AbstractFacade<Competition> {

    @PersistenceContext(unitName = "sports-meetPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CompetitionFacade() {
        super(Competition.class);
    }
    
    public Collection<entity.Student> findStudentsByCompetitionId(Integer id) {
        return getEntityManager()
                 .find(Competition.class, id)
                 .getStudentCollection();
    }
    
    public int getJoinCntById(Integer id){
        return findStudentsByCompetitionId(id).size();
    }
}
