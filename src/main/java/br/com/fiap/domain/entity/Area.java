package br.com.fiap.domain.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "TB_AREA", uniqueConstraints = {
        @UniqueConstraint(name = "UK_NM_AREA", columnNames = {"NM_AREA"})
})
public class Area {

    @Id
    @Column(name = "ID_AREA")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_AREA")
    @SequenceGenerator(name = "SQ_AREA", sequenceName = "SQ_AREA")
    private Long id;

    @Column(name = "NM_AREA", nullable = false)
    private String nome;

    @Column(name = "DS_AREA")
    private String descricao;

    public Area() {
    }

    public Area(Long id, String nome, String descricao) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
    }

    public Long getId() {
        return id;
    }

    public Area setId(Long id) {
        this.id = id;
        return this;
    }

    public String getNome() {
        return nome;
    }

    public Area setNome(String nome) {
        this.nome = nome;
        return this;
    }

    public String getDescricao() {
        return descricao;
    }

    public Area setDescricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    @Override
    public String toString() {
        return "Area{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", descricao='" + descricao + '\'' +
                '}';
    }
}
