package netbeansprojects.registomatriculaexfinalm.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import netbeansprojects.registomatriculaexfinalm.conexao.BDConection;
import netbeansprojects.registomatriculaexfinalm.models.Cliente;


public class ClienteDao {
    private Connection conexao;
     
    public ClienteDao() throws ClassNotFoundException{
        try{
            BDConection bdconexao=new BDConection();
            bdconexao.createDatabase();
            this.conexao=bdconexao.getConnection();
        }catch(SQLException sqlexc){
           Logger.getLogger(ClienteDao.class.getName()).log(Level.SEVERE, null, sqlexc); 
        }
    }

    public void inserir(Cliente cliente) throws SQLException{
        String sql="INSERT INTO Cliente(matricula,nome,idade,sexo) VALUES(?,?,?,?)";
        try(PreparedStatement ps=conexao.prepareStatement(sql)) {
          ps.setInt(1, cliente.getMatricula());  
          ps.setString(2, cliente.getNome());
          ps.setInt(3, cliente.getIdade());
          ps.setString(4, cliente.getSexo());

          int rowsAffected = ps.executeUpdate(); 
          if (rowsAffected > 0) {
              System.out.println("Inserido com sucesso");
          } else {
              System.out.println("Nenhum dado foi inserido.");
          }
        }catch(SQLException sqlexc){
            Logger.getLogger(ClienteDao.class.getName()).log(Level.SEVERE, null, sqlexc);  
        }
    }

    public void actualizar(Cliente cliente) throws SQLException{
        String sql="UPDATE CLIENTE SET nome = ?, idade=?,sexo=?  WHERE matricula = ?";
        try(PreparedStatement ps=conexao.prepareStatement(sql)){
            ps.setString(1, cliente.getNome());
            ps.setInt(2, cliente.getIdade());
            ps.setString(3, cliente.getSexo());
            ps.setInt(4, cliente.getMatricula());

            int rowsAffected = ps.executeUpdate();
        if (rowsAffected > 0) {
            System.out.println("Atualizado com sucesso");
        } else {
            System.out.println("Nenhum dado foi atualizado."); }
        }catch(SQLException sqlexc){
            Logger.getLogger(ClienteDao.class.getName()).log(Level.SEVERE, null, sqlexc);
        }
    }

    public List<Cliente> all(){
        List<Cliente> clientes=new ArrayList<>();
        String sql="SELECT *FROM Cliente";
        try(PreparedStatement ps=conexao.prepareStatement(sql)){
            ResultSet result=ps.executeQuery();
            while (result.next()) {
                Cliente cliente=new Cliente();
                cliente.setMatricula(result.getInt("matricula"));
                cliente.setNome(result.getString("nome"));
                cliente.setIdade(result.getInt("idade"));
                cliente.setSexo(result.getString("sexo"));
                clientes.add(cliente);
            }
        }catch(SQLException sqlexc){
            Logger.getLogger(ClienteDao.class.getName()).log(Level.SEVERE, "Erro de mysql", sqlexc);
        }
        return clientes;
    }  

    public void deletar(int matricula) throws SQLException {
        String sql = "DELETE FROM Cliente WHERE matricula = ?";
        try (PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setInt(1, matricula); // A matrícula é usada para identificar o cliente a ser deletado
    
            // Executa a exclusão
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Deletado com sucesso");
            } else {
                System.out.println("Nenhum dado foi deletado.");
            }
        } catch (SQLException sqlexc) {
            Logger.getLogger(ClienteDao.class.getName()).log(Level.SEVERE, null, sqlexc);
        }
    }
    
}
