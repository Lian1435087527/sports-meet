/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import com.sun.codemodel.JExpr;
import entity.Category;
import entity.Student;
import session.CategoryFacade;
import session.CompetitionFacade;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.component.html.HtmlSelectBooleanCheckbox;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.persistence.Entity;
import javax.servlet.http.HttpSession;
import session.StudentFacade;

/**
 *
 * @author 陆宇恒
 */
@Named(value = "competition")
@SessionScoped
public class Competition implements Serializable {
    @EJB
    private CompetitionFacade competitionFacade;
    @EJB
    private CategoryFacade categoryFacade;
    @EJB
    private StudentFacade studentFacade;
    
    public Competition() {
    } 

    private List<entity.Category> categories;
    private Map<entity.Category,List<CompetitionIndex>> compIndexMap=new HashMap<>();
    
    @PostConstruct
    public void init(){
        categories = this.getCategories();
        categories.forEach((category) -> {
            filter_category.put(category.getName(), true);
        });
        filter_joined = false;
        filter_canjoin = false;
        
        categories.forEach((ca)->{
            compIndexMap.put(ca, this.getCompetitionsByCategory(ca));
        });
    }
    // filters
    private Boolean filter_joined;
    private Boolean filter_canjoin;
    private Map<String, Boolean> filter_category = new HashMap<>();
    public Boolean getFilter_joined() {
        return filter_joined;
    }
    public void setFilter_joined(Boolean filter_joined) {
        this.filter_joined = filter_joined;
    }
    public Boolean getFilter_canjoin() {
        return filter_canjoin;
    }
    public void setFilter_canjoin(Boolean filter_canjoin) {
        this.filter_canjoin = filter_canjoin;
    }
    public Map<String, Boolean> getFilter_category() {
        return filter_category;
    }
    public void setFilter_category(Map<String, Boolean> filter_category) {
        this.filter_category = filter_category;
    }    
    
    public List<entity.Category> getCategories(){
        return categoryFacade.findAll();
    }
    public List<entity.Category> getFilteredCategories(){
        List<Category> observed = categories.stream()
                .filter((Category ca) -> filter_category.get(ca.getName()))
                .collect(Collectors.toList());
        return observed;
    }
    public List<CompetitionIndex> getCompetitionsByCategory(entity.Category category){
        Collection<entity.Competition> competitionsEntity =
                category.getCompetitionCollection();
        List<CompetitionIndex> ret;
        ret = new ArrayList<>();
        competitionsEntity.forEach((competition) -> {
            CompetitionIndex unit = new CompetitionIndex();
            unit.setRaw(competition);
            String namePostfix="";
            if (competition.getType()==1){
                namePostfix="(男)";
            }
            if (competition.getType()==2){
                namePostfix="(女)";
            }
            unit.setName(competition.getName()+namePostfix);
            unit.setTotal(competition.getHeadcount());
            int join_cnt = competitionFacade.getJoinCntById(competition.getIdcompetition());
            unit.setRest(competition.getHeadcount()-join_cnt);
            DateFormat format=new SimpleDateFormat("MM-dd hh:mm");
            unit.setTimeRange(
                    format.format(competition.getStartTime())
                    +" -- "
                    +format.format(competition.getEndTime()));
            unit.setSelected(hasjoin(competition));
            ret.add(unit);
        });
        return ret;
    }
    
    private entity.Student getCurStu(){
        HttpSession session = (HttpSession)FacesContext
                        .getCurrentInstance()
                        .getExternalContext()
                        .getSession(true);
        
        entity.Student stu = (entity.Student)session.getAttribute("cur_stu");
        return stu;
    }
    public List<CompetitionIndex> getFilteredCompetitionsByCategory(entity.Category category){
        List<CompetitionIndex> observed = compIndexMap.get(category).stream()
                .filter((competition_index)-> !filter_joined || hasjoin(competition_index.getRaw()))
                .filter((competition_index)-> !filter_canjoin || sexfit(competition_index.getRaw()))
                .collect(Collectors.toList());
        return observed;
    }
    public boolean hasjoin(entity.Competition competition){
        HttpSession session = (HttpSession)FacesContext
                        .getCurrentInstance()
                        .getExternalContext()
                        .getSession(true);
        
        entity.Student stu = (entity.Student)session.getAttribute("cur_stu");
        boolean joined = stu.getCompetitionCollection().contains(competition);
        
        return joined;
    }
    public boolean sexfit(entity.Competition competition){
        HttpSession session = (HttpSession)FacesContext
                        .getCurrentInstance()
                        .getExternalContext()
                        .getSession(true);
        entity.Student stu = (entity.Student)session.getAttribute("cur_stu");
        Character sex = stu.getSex();
        int type = competition.getType();
        if(sex=='男'){
            return type==1 || type==0;
        }else{
            return type==2 || type==0;
        }
    }
    public void onFilterChange(ValueChangeEvent event){
        HtmlSelectBooleanCheckbox box=(HtmlSelectBooleanCheckbox) event.getComponent();
        String label=box.getLabel();
        if(label.startsWith("filter_category_")){
            this.filter_category.put(label.substring(16), (Boolean)box.getValue());
        }else{
            try {
                this.getClass()
                    .getMethod("set"+label.substring(0,1).toUpperCase()+label.substring(1), Boolean.class)
                    .invoke(this,(Boolean)box.getValue());
            } catch (Exception e) {
            }
        }
        FacesContext.getCurrentInstance().renderResponse();
    }
    private List<entity.Competition> getSelectedCompetitions(){
        List<entity.Competition> selected = new ArrayList<>();
        categories.forEach((ca)->{
            compIndexMap.get(ca).stream()
                    .filter((compI)->compI.isSelected())
                    .map((compI)->compI.getRaw())
                    .forEach((comp)->selected.add(comp));
        });
        return selected;
    }
    public String confirm(){
        entity.Student stu = getCurStu();
        Collection<entity.Competition> joined = studentFacade.getCompetitionCollection(stu);
        List<Integer> joined_id = joined.stream().map((comp)->comp.getIdcompetition()).collect(Collectors.toList());
        List<entity.Competition> cur = getSelectedCompetitions();
        List<Integer> cur_id = joined.stream().map((comp)->comp.getIdcompetition()).collect(Collectors.toList());
        joined.stream()
                .filter((comp)->!cur_id.contains(comp.getIdcompetition()))
                .forEach((comp)->{
                    comp.getStudentCollection().remove(stu);
                    competitionFacade.edit(comp);
                });
        cur.stream()
                .filter((comp)->!joined_id.contains(comp.getIdcompetition()))
                .forEach((comp)->{
                    comp.getStudentCollection().add(stu);
                    competitionFacade.edit(comp);
                });
        return "success";
    }
}
