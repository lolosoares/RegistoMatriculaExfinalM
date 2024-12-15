/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package netbeansprojects.registomatriculaexfinalm.models;

/**
 *
 * @author antoniosoares
 */
public class Cliente {
    private int matricula;
    private String nome;
    private int idade;
    private String sexo;

    public Cliente(int matricula, String nome, int idade, String sexo) {
        this.matricula = matricula;
        this.nome = nome;
        this.idade = idade;
        this.sexo = sexo;
    }

    public Cliente(){}

    public int getMatricula() {
        return matricula;
    }

    public void setMatricula(int matricula) {
        this.matricula = matricula;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    @Override
    public String toString() {
        return "Cliente [matricula=" + matricula + ", nome=" + nome + ", idade=" + idade + ", sexo=" + sexo + "]";
    }
    
}

