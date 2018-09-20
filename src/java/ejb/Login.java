/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import java.io.Serializable;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import session.AccountFacade;
import session.StudentFacade;

/**
 *
 * @author 陆宇恒
 */
@Named(value = "login")
@SessionScoped
public class Login implements Serializable{

    @EJB
    private AccountFacade accountFacade;
    @EJB
    private StudentFacade studentFacade;
    
    private String name;
    private String password;
    private String errmsg = "";

    public String getErrmsg() {
        return errmsg;
    }
    
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public Login() {
    }
    
    public String login(){
        try {
            Integer id =Integer.parseInt(name);
            String password_truth = accountFacade.findPasswordById(id);
            if(password.equals(password_truth)){
                HttpSession session = (HttpSession)FacesContext
                        .getCurrentInstance()
                        .getExternalContext()
                        .getSession(true);
                session.setAttribute("cur_stu_id", id);
                return "success";
            }
            errmsg="用户名或密码不正确";
            return "failed";
        } catch (NumberFormatException e) {
            errmsg="用户名或密码不正确";
            return "failed";
        } catch (Exception e){
            errmsg="未知错误";
            return "failed";
        }
    }
}
