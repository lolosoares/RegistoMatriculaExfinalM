package netbeansprojects.registomatriculaexfinalm.test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import netbeansprojects.registomatriculaexfinalm.dao.ClienteDao;
import netbeansprojects.registomatriculaexfinalm.models.Cliente;

public class Test{
    public Test() throws ClassNotFoundException, SQLException{
        ClienteDao clienteDao=new ClienteDao();

        //teste de insercao
        Cliente cliente=new Cliente(20231198,"Robson Soares",23,"M");
        clienteDao.inserir(cliente);
        

        //teste de actualizar
        Cliente clientea=new Cliente(20231198,"Robson Atonio",25,"M");
        clienteDao.actualizar(clientea);

        //teste de get all
        List<Cliente> clientes=new ArrayList<>();
        clientes=clienteDao.all();
        System.out.println(clientes);

        //teste do delete
        clienteDao.deletar(20231198);
    }
}
