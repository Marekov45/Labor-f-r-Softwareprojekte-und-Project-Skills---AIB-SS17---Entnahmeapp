package client_aib_labswp_2017_ss_entnahmeapp.View.Model;

/**
 * Created by User on 08.06.2017.
 */
public class User {
    private String username;
    private String password;

    public User(String name, String password){
        this.username = name;
        this.password=password;
        System.out.println(username+password);
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {

        return username;
    }

    public String getPassword() {
        return password;
    }
}
