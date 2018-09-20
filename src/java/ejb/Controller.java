/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javax.ejb.EJB;
import session.CompetitionFacade;

/**
 *
 * @author Administrator
 */
@Named(value = "controller")
@SessionScoped
public class Controller implements Serializable {

    private String Pro_name;
    private int Pro_num;
    @EJB
    private CompetitionFacade competitionfacade;
    
    public String GetPro_name(){
        return Pro_name;
    }
     public int GetPro_num(){
        return Pro_num;
    }
     public String SetAndJump(String Temp){
        this.Pro_name=Temp;
        this.Pro_num=ProToInt();
        return "project.xhtml";
    }
    
    public int ProToInt(){
        int ret=0;
        for(entity.Competition c:competitionfacade.findAll()){
            if(c.getName().equals(this.Pro_name)){
                ret=c.getIdcompetition();
                break;
            }
        }
        return ret;
//        competitionfacade.findAll().forEach((competition)->{
//            if(competition.getName()==this.Pro_name)
//                break;
//        };
    }
    /**
     * Creates a new instance of Controller
     */
    public Controller() {
    }
    
}
