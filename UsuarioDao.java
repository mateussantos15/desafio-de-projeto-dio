package model.dao;

import connection.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import model.bean.UsuarioBEAN;

/**
 *
 * @author Mateus
 */
public class UsuarioDao {

    public boolean create(UsuarioBEAN user) {
        boolean chekCadastro = false;
        Connection conn;
        PreparedStatement stmt = null;
        conn = ConnectionFactory.getConnection();

        try {
            stmt = conn.prepareStatement("INSERT INTO tb_usuario(id_email,nome,estado,cidade,qualificacao,cep,senha)VALUES(?, ?, ?, ?, ?, ?, ?)");
//            ConnectionFactory.stmt.setString(1, user.getCpf());
            stmt.setString(1, user.getEmail());
            stmt.setString(2, user.getNome());
            stmt.setString(3, user.getEstado());
            stmt.setString(4, user.getCidade());
            stmt.setString(5, user.getQualificacao());
            stmt.setString(6, user.getCep());
            stmt.setString(7, user.getSenha());

            stmt.executeUpdate();

            JOptionPane.showMessageDialog(null, "Salvo com Sucesso!");
            chekCadastro = true;

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao Salvar!" + ex);

        } finally {
            ConnectionFactory.closeConnection(conn, stmt);
        }
        return chekCadastro;
    }

    public boolean excluirUsuario(String id_usuario) {
        boolean chek = false;
        int confirmar;

        confirmar = JOptionPane.showConfirmDialog(null, "Você Realmente deseja execultar essa Operação?", "Excluir Conta de Usuario", 0);

        if (confirmar == 0) {
            Connection conn;
            PreparedStatement stmt = null;
            conn = ConnectionFactory.getConnection();

            try {
                stmt = conn.prepareStatement("delete from tb_usuario where id_email = ?");
                stmt.setString(1, id_usuario);
                stmt.execute();
                chek = true;

            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Erro ao Excluir!");
                Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex);

            } finally {
                ConnectionFactory.closeConnection(conn, stmt);
            }

        } else {
            chek = false;
        }
        return chek;
    }

    public void updateUsuario(UsuarioBEAN user) {
        Connection conn;
        PreparedStatement stmt = null;
        conn = ConnectionFactory.getConnection();

        try {
            stmt = conn.prepareStatement("update tb_usuario set nome = ?, estado = ?, cidade = ?, cep = ?, senha = ? where id_email = ?");
            stmt.setString(1, user.getNome());
            stmt.setString(2, user.getEstado());
            stmt.setString(3, user.getCidade());
            stmt.setString(4, user.getCep());
            stmt.setString(5, user.getSenha());
            stmt.setString(6, user.getEmail());

            stmt.execute();
            JOptionPane.showMessageDialog(null, "Alterado com Sucesso!");

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao Alterar!");
            Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex);

        } finally {
            ConnectionFactory.closeConnection(conn, stmt);
        }
    }
}
