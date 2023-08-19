package br.com.fiap;

import br.com.fiap.domain.entity.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import javax.swing.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class Main {
    public static void main(String[] args) {


        EntityManagerFactory factory = Persistence.createEntityManagerFactory( "maria-db" );

        EntityManager manager = factory.createEntityManager();

        // addArea(manager);
        //TipoDocumento tipo = addTipoDeDocumento(manager);
        //addPessoa( manager );
        //addDocumento( manager );
        // aberturaDeChamado( manager );


        manager.close();
        factory.close();

    }

    private static void aberturaDeChamado(EntityManager manager) {
        List<Pessoa> pessoas = manager.createQuery( "FROM Pessoa " ).getResultList();

        Pessoa solicitante = (Pessoa) JOptionPane.showInputDialog(
                null,
                "Solicitante",
                "Selecione",
                JOptionPane.QUESTION_MESSAGE,
                null,
                pessoas.toArray(),
                pessoas.get( 0 )
        );

        List<Area> areas = manager.createQuery( "FROM Area " ).getResultList();

        Area area = (Area) JOptionPane.showInputDialog(
                null,
                "Área",
                "Selecione",
                JOptionPane.QUESTION_MESSAGE,
                null,
                areas.toArray(),
                areas.get( 0 )
        );

        String titulo = JOptionPane.showInputDialog( "Titulo do Problema" );

        String descricao = JOptionPane.showInputDialog( "Descreva o problema" );


        Chamado chamado = new Chamado();
        chamado.setAbertura( LocalDateTime.now() )
                .setArea( area )
                .setTitulo( titulo )
                .setDescricao( descricao )
                .setSolicitante( solicitante );

        manager.getTransaction().begin();
        manager.persist( chamado );
        manager.getTransaction().commit();
    }

    private static void addPessoa(EntityManager manager) {
        Pessoa pessoa = new Pessoa();

        String nome = JOptionPane.showInputDialog( "Nome da Pessoa" );

        String nascimento = JOptionPane.showInputDialog( "Data de Nascimento no formato DD/MM/AAAA" );
        int dia = Integer.parseInt( nascimento.substring( 0, 2 ) );
        int mes = Integer.parseInt( nascimento.substring( 3, 5 ) );
        int ano = Integer.parseInt( nascimento.substring( 6, 10 ) );

        pessoa.setNome( nome ).setNascimento( LocalDate.of( ano, mes, dia ) );

        manager.getTransaction().begin();
        manager.persist( pessoa );
        manager.getTransaction().commit();
    }

    private static void addDocumento(EntityManager manager) {

        Documento doc = new Documento();

        List<TipoDocumento> tipos = manager.createQuery( "FROM TipoDocumento" ).getResultList();

        TipoDocumento tipoSelecionado = (TipoDocumento) JOptionPane.showInputDialog(
                null,
                "Selectione o Tipo de Documento",
                "Seleção de Tipo de Documento",
                JOptionPane.QUESTION_MESSAGE,
                null,
                tipos.toArray(),
                tipos.get( 0 )
        );

        doc.setTipo( tipoSelecionado );


        String numero = JOptionPane.showInputDialog( "Número do Documento" );

        doc.setNumero( numero );


        String validade = JOptionPane.showInputDialog( "Data de validade no formato DD/MM/AAAA" );
        int dia = Integer.parseInt( validade.substring( 0, 2 ) );
        int mes = Integer.parseInt( validade.substring( 3, 5 ) );
        int ano = Integer.parseInt( validade.substring( 6, 10 ) );


        doc.setValidade( LocalDate.of( ano, mes, dia ) );


        List<Pessoa> pessoas = manager.createQuery( "FROM Pessoa" ).getResultList();

        Pessoa pessoaSelecionada = (Pessoa) JOptionPane.showInputDialog(
                null,
                "Selectione a Pessoa",
                "Seleção de Pessoa",
                JOptionPane.QUESTION_MESSAGE,
                null,
                pessoas.toArray(),
                pessoas.get( 0 )
        );

        doc.setPessoa( pessoaSelecionada );

        manager.getTransaction().begin();
        manager.persist( doc );
        manager.getTransaction().commit();


        System.out.println( doc );
    }


    static TipoDocumento findById(Long id, EntityManager manager) {
        return manager.find( TipoDocumento.class, id );
    }

    private static TipoDocumento addTipoDeDocumento(EntityManager manager) {

        String nome = JOptionPane.showInputDialog( "Tipo de Documento" );
        TipoDocumento tipo = new TipoDocumento();
        tipo.setNome( nome );


        boolean salvou = false;

        do {
            try {
                manager.getTransaction().begin();
                manager.persist( tipo );
                manager.getTransaction().commit();
                System.out.println( tipo );
                salvou = true;
            } catch (Exception ex) {

                String erro = """
                        Erro!
                        Não foi possível salvar o Tipo de Documento.
                                            
                        """ + ex.getMessage();

                System.err.println( erro );

                JOptionPane.showMessageDialog( null, erro );
            }
        } while (salvou == false);


        return tipo;
    }

    private static Area addArea(EntityManager manager) {
        Area area = new Area();

        boolean salvou = false;

        do {
            String nome = JOptionPane.showInputDialog( "Nome da Área" );
            String descricao = JOptionPane.showInputDialog( "Descrição da Área" );

            area.setNome( nome ).setDescricao( descricao );

            try {
                manager.getTransaction().begin();
                manager.persist( area );
                manager.getTransaction().commit();
                System.out.println( area );
                salvou = true;
            } catch (Exception ex) {
                System.out.println( "Não foi possível salvar a área. verifique se já existe área com este nome: " + ex.getMessage() );
                JOptionPane.showMessageDialog( null, "Não foi possível salvar a área. verifique se já existe área com este nome: " + ex.getMessage() );
            }

        } while (salvou == false);

        return area;
    }
}