/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import javax.inject.Named;
import javax.enterprise.context.Dependent;


@Named(value = "reset")
@Dependent
public class Reset {

    private String old_password;
    private String new_password;
    private String confirm_password;
    public Reset() {
    }
    
}
