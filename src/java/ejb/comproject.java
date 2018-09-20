/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import session.CompetitionFacade;
import session.StudentFacade;

/**
 *
 * @author Administrator
 */
@Named(value = "comproject")
@Dependent
public class comproject {

    /**
     * Creates a new instance of comproject
     */
    @EJB
    private StudentFacade studentfacade;
    @EJB
    private CompetitionFacade competitionfacade;

    public String Getproname(int ID_competition) {

        List<String> Return;
        Return = new ArrayList<String>();

        competitionfacade.findAll().forEach((entity.Competition competition) -> {
            if (competition.getIdcompetition() == ID_competition) {
                Return.add(competition.getName());
                int join_cnt = competitionfacade.getJoinCntById(competition.getIdcompetition());
                Integer Rest = competition.getHeadcount() - join_cnt;
                Return.add("  剩余人数：" + Rest.toString());
                Return.add("   开始时间："+competition.getStartTime().toString().substring(0,20));
                Return.add("   结束时间："+competition.getEndTime().toString().substring(0,20));
            }
        });
        return Return.toString();
    }

    public List<entity.Student> GetproStudent(int ID_competition) {
        List<entity.Student> Return;
        Return = new ArrayList<entity.Student>();

        entity.Competition P = new entity.Competition();
        competitionfacade.findAll().forEach((entity.Competition competition) -> {
            if (competition.getIdcompetition() == ID_competition) {
                P.setCategoryIdcategory(competition.getCategoryIdcategory());
                P.setEndTime(competition.getEndTime());
                P.setHeadcount(competition.getHeadcount());
                P.setIdcompetition(ID_competition);
                P.setName(competition.getName());
                P.setStartTime(competition.getStartTime());
                P.setStudentCollection(competition.getStudentCollection());
                P.setType(competition.getType());
            }

        });
        studentfacade.findAll().forEach((student) -> {
            if (student.getCompetitionCollection().contains(P)) {
                Return.add(student);
            }

        });
        return Return;

    }

    public comproject() {
    }

}
