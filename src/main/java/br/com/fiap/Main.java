package br.com.fiap;

import br.com.fiap.domain.entity.Area;
import br.com.fiap.domain.entity.Documento;
import br.com.fiap.domain.entity.TipoDocumento;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import javax.swing.*;
import java.sql.SQLException;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {


        EntityManagerFactory factory = Persistence.createEntityManagerFactory("oracle");

        EntityManager manager = factory.createEntityManager();


        // Area area = addArea(manager);


        // TipoDocumento tipo = addTipoDeDocumento(manager);


        Documento doc = new Documento();
        doc.setNumero("284739753");
        doc.setValidade(LocalDate.now().plusYears(5));
        doc.setTipo(findById(2L, manager));

        manager.getTransaction().begin();
        manager.persist(doc);
        manager.getTransaction().commit();


        System.out.println(doc);

        manager.close();
        factory.close();

    }


    static TipoDocumento findById(Long id, EntityManager manager) {
        return manager.find(TipoDocumento.class, id);
    }

    private static TipoDocumento addTipoDeDocumento(EntityManager manager) {

        String nome = JOptionPane.showInputDialog("Tipo de Documento");
        TipoDocumento tipo = new TipoDocumento();
        tipo.setNome(nome);


        boolean salvou = false;

        do {
            try {
                manager.getTransaction().begin();
                manager.persist(tipo);
                manager.getTransaction().commit();
                System.out.println(tipo);
                salvou = true;
            } catch (Exception ex) {

                String erro = """
                        Erro!
                        Não foi possível salvar o Tipo de Documento.
                                            
                        """ + ex.getMessage();

                System.err.println(erro);

                JOptionPane.showMessageDialog(null, erro);
            }
        } while (salvou == false);


        return tipo;
    }

    private static Area addArea(EntityManager manager) {
        Area area = new Area();

        boolean salvou = false;

        do {
            String nome = JOptionPane.showInputDialog("Nome da Área");
            String descricao = JOptionPane.showInputDialog("Descrição da Área");

            area.setNome(nome).setDescricao(descricao);

            try {
                manager.getTransaction().begin();
                manager.persist(area);
                manager.getTransaction().commit();
                System.out.println(area);
                salvou = true;
            } catch (Exception ex) {
                System.out.println("Não foi possível salvar a área. verifique se já existe área com este nome: " + ex.getMessage());
                JOptionPane.showMessageDialog(null, "Não foi possível salvar a área. verifique se já existe área com este nome: " + ex.getMessage());
            }

        } while (salvou == false);

        return area;
    }
}