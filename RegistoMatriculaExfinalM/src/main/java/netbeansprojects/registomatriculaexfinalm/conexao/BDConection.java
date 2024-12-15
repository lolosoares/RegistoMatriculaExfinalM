package netbeansprojects.registomatriculaexfinalm.conexao;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
public class BDConection {
    private String Host;
    private String BD;
    private String user;
    private String password;
    private String porta;

    public BDConection() {
        this.Host="localhost";
        this.BD = "RegistroMatricula";
        this.user = "poo";
        this.password ="10000008";
        this.porta = "3306";
    }

    public Connection getConnection() throws ClassNotFoundException, SQLException{
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection("jdbc:mysql://"+ Host +":" + porta +"/"+ BD +"?user="+ user+ "&password=" + password + "&noAccessToProcedureBodies=true");
    }
    
    public void createDatabase() throws SQLException{
        try(Connection conn=DriverManager.getConnection("jdbc:mysql://"+ Host +":" + porta +"/"+ BD +"?user="+ user+ "&password=" + password + "&noAccessToProcedureBodies=true");
            Statement stm=conn.createStatement()){
                stm.executeUpdate("CREATE DATABASE IF NOT EXISTS"+" "+BD);
                System.out.println("Criada com sucesso ou ja existe: "+BD);

        }
    }

    public String getHost() {
        return Host;
    }

    public void setHost(String host) {
        Host = host;
    }

    public String getBD() {
        return BD;
    }

    public void setBD(String bD) {
        BD = bD;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPorta() {
        return porta;
    }

    public void setPorta(String porta) {
        this.porta = porta;
    }

    
}

