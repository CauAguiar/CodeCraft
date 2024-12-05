package com.example.demo.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nome; // Nome do curso, ex.: "Java para Iniciantes"

    @Column(nullable = false)
    private String descricao; // Descrição geral do curso

    @Column(name = "nivel", nullable = false)
    private String nivel; // Nível do curso (ex.: "Básico", "Intermediário", "Avançado")

    @Column(name = "categoria", nullable = false)
    private String categoria; // Categoria do curso (ex.: "Linguagens de Programação", "Banco de Dados")

    @Column(name = "icone")
    private String icone; // URL ou identificador de ícone representando o curso

    @Column(name = "duracao_total", nullable = false)
    private Integer duracaoTotal; // Duração total do curso em minutos

    @Column(name = "habilitado", nullable = false)
    private Boolean habilitado; // Indica se o curso está ativo e disponível

    @OneToMany(mappedBy = "curso", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference
    private List<Unidade> unidades; // Relação com as unidades do curso

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getIcone() {
        return icone;
    }

    public void setIcone(String icone) {
        this.icone = icone;
    }

    public Integer getDuracaoTotal() {
        return duracaoTotal;
    }

    public void setDuracaoTotal(Integer duracaoTotal) {
        this.duracaoTotal = duracaoTotal;
    }

    public Boolean getHabilitado() {
        return habilitado;
    }

    public void setHabilitado(Boolean habilitado) {
        this.habilitado = habilitado;
    }

    public List<Unidade> getUnidades() {
        return unidades;
    }

    public void setUnidades(List<Unidade> unidades) {
        this.unidades = unidades;
    }
}
