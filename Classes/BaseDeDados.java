package Classes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class BaseDeDados {
    //URl para conectar com o MySQl
    private static final String URL = "jdbc:mysql://localhost:3306/SGE1?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
    private static final String user = "root"; 
    private static final String password = ""; 

    public static Connection getConnection() throws SQLException {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
        }catch(ClassNotFoundException e) {
            System.out.println("Driver JDBC do MySQL não foi encontrado nas bibliotecas");
        }
        return DriverManager.getConnection(URL, user, password);
    }

    //esse metodo ira testar a conecxao com a base de dados SGE
    public static void testeDeConecxao() {
        try(Connection conct = getConnection()) {
            if (conct != null) {
                System.out.println("A Conexão estabelecida com a base de dados");
            }
        }catch(SQLException c){
            System.out.println("Houve uma falha ao conectar com a base de dados: " + c.getMessage());
            System.out.println("Verifique se o MySQL está ativo no XAMPP e se executou o script de criação da BD");
        }
    }
}

