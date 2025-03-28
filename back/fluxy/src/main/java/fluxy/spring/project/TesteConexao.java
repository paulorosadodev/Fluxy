import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TesteConexao {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/fluxy_db?useSSL=false&serverTimezone=UTC";
        String usuario = "root";  // Substitua pelo seu usuário
        String senha = "LUANh1432";  // Substitua pela sua senha

        try {
            // Carregar explicitamente o driver JDBC
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Tenta estabelecer a conexão
            try (Connection conexao = DriverManager.getConnection(url, usuario, senha)) {
                System.out.println("✅ Conexão estabelecida com sucesso!");
            } catch (SQLException e) {
                System.out.println("❌ Erro ao conectar: " + e.getMessage());
            }
        } catch (ClassNotFoundException e) {
            System.out.println("❌ Driver não encontrado: " + e.getMessage());
        }
    }
}
