package Controladores;

/**
 *
 * @author armando
 */
public class Database {

    private String url;
    private String driver;
    private String user;
    private String pass;

    public Database() {
        
        //this.url = "jdbc:mysql://localhost:3306/AS?useUnicode=true&characterEncoding=UTF-8";
        this.url = "jdbc:mysql://localhost:3306/AS?useUnicode=true&characterEncoding=UTF-8";
        this.driver = "com.mysql.jdbc.Driver";
        this.user = "root";
        this.pass = "";

    }

    public String getUrl() {
        return url;
    }

    public String getDriver() {
        return driver;
    }

    public String getUser() {
        return user;
    }

    public String getPass() {
        return pass;
    }
}
