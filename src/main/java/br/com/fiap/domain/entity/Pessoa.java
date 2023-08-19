package br.com.fiap.domain.entity;


import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "TB_PESSOA")
public class Pessoa {

    @Id
    @Column(name = "ID_PESSOA")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_PESSOA")
    @SequenceGenerator(name = "SQ_PESSOA", sequenceName = "SQ_PESSOA")
    private
    Long id;
    @Column(name = "NM_PESSOA", nullable = false)
    private
    String nome;


    @Column(name = "DT_NASCIMENTO", nullable = false)
    private
    LocalDate nascimento;

    public Pessoa() {
    }

    public Pessoa(Long id, String nome, LocalDate nascimento) {
        this.id = id;
        this.nome = nome;
        this.nascimento = nascimento;
    }


    public Long getId() {
        return id;
    }

    public Pessoa setId(Long id) {
        this.id = id;
        return this;
    }

    public String getNome() {
        return nome;
    }

    public Pessoa setNome(String nome) {
        this.nome = nome;
        return this;
    }

    public LocalDate getNascimento() {
        return nascimento;
    }

    public Pessoa setNascimento(LocalDate nascimento) {
        this.nascimento = nascimento;
        return this;
    }

    @Override
    public String toString() {
        return id + " - " + nome;
    }
}
