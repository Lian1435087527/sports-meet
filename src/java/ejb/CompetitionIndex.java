/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import java.io.Serializable;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;

/**
 *
 * @author 陆宇恒
 */
@Named(value = "competitionIndex")
@SessionScoped
public class CompetitionIndex implements Serializable{

    /**
     * Creates a new instance of CompetitionIndex
     */
    public CompetitionIndex() {
    }
    private String name;
    private int total;
    private int rest;
    private boolean selected;

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getRest() {
        return rest;
    }

    public void setRest(int rest) {
        this.rest = rest;
    }

    public String getTimeRange() {
        return timeRange;
    }

    public void setTimeRange(String timeRange) {
        this.timeRange = timeRange;
    }

    public entity.Competition getRaw() {
        return raw;
    }

    public void setRaw(entity.Competition raw) {
        this.raw = raw;
    }
    private String timeRange;
    private entity.Competition raw;
}
