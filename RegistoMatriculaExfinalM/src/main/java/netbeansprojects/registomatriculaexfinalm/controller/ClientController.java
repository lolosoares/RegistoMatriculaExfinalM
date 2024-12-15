package netbeansprojects.registomatriculaexfinalm.controller;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import netbeansprojects.registomatriculaexfinalm.conexao.BDConection;
import netbeansprojects.registomatriculaexfinalm.dao.ClienteDao;
import netbeansprojects.registomatriculaexfinalm.models.Cliente;
public class ClientController {
    private Connection connection;
    private BDConection bdconexao;
    private ClienteDao clienteDao;

    public ClientController() throws ClassNotFoundException, SQLException {
        bdconexao=new BDConection();
        clienteDao=new ClienteDao();
        this.connection = bdconexao.getConnection();
    }

    // Método para cadastrar um cliente
    public void cadastrarCliente(String matricula, String nome, String idade, String sexo) {
        String sql = "INSERT INTO Cliente (matricula, nome, idade, sexo) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, matricula);
            pstmt.setString(2, nome);
            pstmt.setString(3, idade);
            pstmt.setString(4, sexo);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para buscar todos os clientes
    public ResultSet buscarTodosClientes() {
        String sql = "SELECT * FROM Cliente ORDER BY nome"; // Substitua "clientes" pelo seu nome de tabela
        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            return pstmt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Método para pesquisar cliente por matrícula
    public ResultSet pesquisarClientePorMatricula(String matricula) {
        String sql = "SELECT * FROM Cliente WHERE matricula = ?"; 
        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, matricula);
            return pstmt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void actualizarcliente(Cliente cliente) throws SQLException{
            clienteDao.actualizar(cliente);
    }

    public void removercliente(int matricula) throws SQLException{
        clienteDao.deletar(matricula);

    }
}
