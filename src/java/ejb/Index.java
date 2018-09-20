/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entity.Competition;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import session.CompetitionFacade;

/**
 *
 * @author Administrator
 */
@Named(value = "index")
@Dependent
public class Index {

    @EJB
    private CompetitionFacade competitionfacade;

    public ArrayList<Map<String, String>> GetIndex() {
        ArrayList<Map<String, String>> ret;
        ret = new ArrayList<>();
        competitionfacade.findAll().forEach(new Consumer<Competition>() {

            public void accept(Competition competition) {
                Map<String, String> unit = new HashMap<>();
                unit.put("pro_name", competition.getName());
                unit.put("pro_sum", Integer.toString(competition.getHeadcount()));
                int join_cnt = competitionfacade.getJoinCntById(competition.getIdcompetition());
                unit.put("pro_in", Integer.toString(join_cnt));
                unit.put("pro_rest", Integer.toString(competition.getHeadcount() - join_cnt));
                ret.add(unit);
            }
        });
        return ret;
    }

    /**
     * Creates a new instance of Index
     */
    public Index() {
    }

}
