package client_aib_labswp_2017_ss_entnahmeapp.View.Model;

import client_aib_labswp_2017_ss_entnahmeapp.View.Controller.LoginController;

/**
 * Created by User on 06.06.2017.
 */
public class LoginModel implements LoginController{

    public boolean login (String name, String password){
        if(name.equals("lea")&& password.equals("123")){
            return true;
        }else{
            return false;
        }
    }
}
