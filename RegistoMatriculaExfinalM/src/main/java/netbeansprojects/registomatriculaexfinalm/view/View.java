package netbeansprojects.registomatriculaexfinalm.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import netbeansprojects.registomatriculaexfinalm.controller.ClientController;
import netbeansprojects.registomatriculaexfinalm.models.Cliente;

import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;

public class View extends JFrame {
    private ClientController clientController;
    private DefaultTableModel tableModel;
    private JTable table;

    public View() throws ClassNotFoundException, SQLException {
        clientController = new ClientController(); // Inicializa o controlador
        configureWindow();
        JPanel panelInicial = createInitialPanel();
        add(panelInicial);
        setVisible(true);
    }

    private void configureWindow() {
        setTitle("Gestor de Sistema");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private JPanel createInitialPanel() {
        JPanel panelInicial = new JPanel(new BorderLayout());
        JLabel mensagem = new JLabel("Bem-vindo Gestor", JLabel.CENTER);
        mensagem.setFont(new Font("Arial", Font.BOLD, 24));
        panelInicial.add(mensagem, BorderLayout.CENTER);

        JButton btnIniciar = createButton("Iniciar", new Color(50, 205, 50), Color.GRAY);
        panelInicial.add(btnIniciar, BorderLayout.SOUTH);
        btnIniciar.addActionListener(e -> mostrarPainelOpcoes());

        return panelInicial;
    }

    private JButton createButton(String text, Color background, Color foreground) {
        JButton button = new JButton(text);
        button.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        button.setBackground(background);
        button.setForeground(foreground);
        button.setFocusPainted(false);
        return button;
    }

    private void mostrarPainelOpcoes() {
        getContentPane().removeAll();
        JPanel panelOpcoes = new JPanel(new GridLayout(3, 1, 10, 10));

        JButton btnCadastrar = createButton("Cadastrar", new Color(100, 149, 237), Color.BLACK);
        JButton btnTodos = createButton("Todos", new Color(70, 130, 180), Color.BLACK);
        JButton btnRemover = createButton("Remover", new Color(255, 69, 0), Color.BLACK);

        panelOpcoes.add(btnCadastrar);
        panelOpcoes.add(btnTodos);
        panelOpcoes.add(btnRemover);
        add(panelOpcoes);

        btnCadastrar.addActionListener(e -> mostrarFormularioCadastro());
        btnTodos.addActionListener(e -> mostrarTabelaClientes());
        btnRemover.addActionListener(e -> mostrarFormularioRemocao());
        revalidate();
        repaint();
    }

    private void mostrarFormularioRemocao() {
        getContentPane().removeAll();
        JPanel panelRemocao = createFormPanel("Remover Cliente");

        JTextField txtMatricula = new JTextField();
        addFormField(panelRemocao, "Matrícula:", txtMatricula);
        JButton btnRemover = createButton("Remover", new Color(255, 69, 0), Color.GRAY);
        panelRemocao.add(btnRemover);

        JButton voltarbtn = createButton("Voltar", new Color(50, 205, 50), Color.GRAY);
        panelRemocao.add(voltarbtn, BorderLayout.SOUTH);
        voltarbtn.addActionListener(e -> mostrarPainelOpcoes());

        btnRemover.addActionListener(e -> {
            String matricula = txtMatricula.getText();
            int matriculaint=Integer.parseInt(matricula);
            if (!matricula.isEmpty()) {
                 try {
                    clientController.removercliente(matriculaint);
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
                    JOptionPane.showMessageDialog(this, "Cliente removido com sucesso!");
                    txtMatricula.setText(""); // Limpa o campo
                }
                
            else {
                JOptionPane.showMessageDialog(this, "Por favor, preencha a matrícula.");
            }
        });

        add(panelRemocao);
        revalidate();
        repaint();
    }

    private void mostrarFormularioCadastro() {
        getContentPane().removeAll();
        JPanel panelFormulario = createFormPanel("Cadastrar Cliente");

        JTextField txtMatricula = new JTextField();
        JTextField txtNome = new JTextField();
        JTextField txtIdade = new JTextField();
        JTextField txtSexo = new JTextField();

        addFormField(panelFormulario, "Matrícula:", txtMatricula);
        addFormField(panelFormulario, "Nome:", txtNome);
        addFormField(panelFormulario, "Idade:", txtIdade);
        addFormField(panelFormulario, "Sexo (M/F):", txtSexo);
        JButton btnSalvar = createButton("Cadastrar", new Color(50, 205, 50), Color.GRAY);
        panelFormulario.add(btnSalvar);

        JButton voltarbtn = createButton("Voltar", new Color(50, 205, 50), Color.GRAY);
        panelFormulario.add(voltarbtn);
        voltarbtn.addActionListener(e -> mostrarPainelOpcoes());
        btnSalvar.addActionListener(e -> {
            clientController.cadastrarCliente(txtMatricula.getText(), txtNome.getText(), txtIdade.getText(), txtSexo.getText());
            JOptionPane.showMessageDialog(null, "Cliente cadastrado com sucesso!");
            clearFormFields(txtMatricula, txtNome, txtIdade, txtSexo);
        });

        add(panelFormulario);
        revalidate();
        repaint();
    }

    private JPanel createFormPanel(String title) {
        JPanel panelFormulario = new JPanel(new GridLayout(5, 2, 10, 10));
        panelFormulario.setBorder(BorderFactory.createTitledBorder(title));
        return panelFormulario;
    }

    private void addFormField(JPanel panel, String labelText, JTextField textField) {
        panel.add(new JLabel(labelText));
        panel.add(textField);
    }

    private void clearFormFields(JTextField... fields) {
        for (JTextField field : fields) {
            field.setText("");
        }
    }

    private void mostrarTabelaClientes() {
        getContentPane().removeAll();
        JPanel panelTabela = new JPanel(new BorderLayout());

        tableModel = new DefaultTableModel(new Object[]{"Matrícula", "Nome", "Idade", "Sexo"}, 0);
        table = new JTable(tableModel);
        carregarDadosTabela();

        // Filtro de matrícula
        JPanel filtroPanel = new JPanel();
        JTextField txtFiltroMatricula = new JTextField(10);
        JButton btnPesquisar = createButton("Pesquisar", new Color(50, 205, 50), Color.GRAY);
        filtroPanel.add(new JLabel("Pesquisar Matrícula:"));
        filtroPanel.add(txtFiltroMatricula);
        filtroPanel.add(btnPesquisar);

        btnPesquisar.addActionListener(e -> {
            String matricula = txtFiltroMatricula.getText();
            if (!matricula.isEmpty()) {
                carregarDadosTabelaFiltrados(matricula);
            } else {
                carregarDadosTabela();
            }
        });

        panelTabela.add(filtroPanel, BorderLayout.NORTH);
        panelTabela.add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel botoes=new JPanel(new BorderLayout());
        JButton btnAtualizar = new JButton("Atualizar Cliente");
        botoes.add(btnAtualizar, BorderLayout.EAST);

        JButton voltarbtn = createButton("Voltar", new Color(50, 205, 50), Color.GRAY);
        botoes.add(voltarbtn, BorderLayout.WEST);
        panelTabela.add(botoes,BorderLayout.SOUTH);
        voltarbtn.addActionListener(e -> mostrarPainelOpcoes());
        btnAtualizar.addActionListener(e -> {
            try {
                atualizarClienteSelecionado();
            } catch (SQLException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        });

        add(panelTabela);
        revalidate();
        repaint();
    }
    private void carregarDadosTabelaFiltrados(String matricula) {
        try {
            tableModel.setRowCount(0);
            ResultSet rs = clientController.pesquisarClientePorMatricula(matricula);
            while (rs.next()) {
                String nome = rs.getString("nome");
                String idade = rs.getString("idade");
                String sexo = rs.getString("sexo");
                tableModel.addRow(new Object[]{matricula, nome, idade, sexo});
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro ao carregar dados filtrados.");
        }
    }

    private void carregarDadosTabela() {
        try {
            tableModel.setRowCount(0);
            ResultSet rs = clientController.buscarTodosClientes();
            while (rs.next()) {
                String matricula = rs.getString("matricula");
                String nome = rs.getString("nome");
                String idade = rs.getString("idade");
                String sexo = rs.getString("sexo");
                tableModel.addRow(new Object[]{matricula, nome, idade, sexo});
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro ao carregar dados da tabela.");
        }
    }

    private void atualizarClienteSelecionado() throws SQLException {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            String matricula = table.getValueAt(selectedRow, 0).toString();
            int matriculaint=Integer.parseInt(matricula);
            String nome = table.getValueAt(selectedRow, 1).toString();
            String idade = table.getValueAt(selectedRow,2).toString();
            int idadeint=Integer.parseInt(idade);
            String sexo = table.getValueAt(selectedRow, 3).toString();
            clientController.actualizarcliente(new Cliente(matriculaint,nome,idadeint,sexo)); 

            JOptionPane.showMessageDialog(this, "Atualizado cliente com matrícula: " + matricula);
        } else {
            JOptionPane.showMessageDialog(null, "Por favor, selecione um cliente.");
        }
    }
}
