import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class ProdutosDAO {

    Connection conn;
    PreparedStatement prep;
    ResultSet resultset;

    // ==============================
    // CADASTRAR PRODUTO
    // ==============================
    public boolean cadastrarProduto(ProdutosDTO produto) {

        conn = new conectaDAO().connectDB();

        if (conn == null) {
            JOptionPane.showMessageDialog(
                null,
                "ERRO: Não foi possível conectar ao banco uc11."
            );
            return false;
        }

        String sql = "INSERT INTO produtos (nome, valor, status) VALUES (?, ?, ?)";

        try {

            prep = conn.prepareStatement(sql);

            prep.setString(1, produto.getNome());
            prep.setInt(2, produto.getValor());
            prep.setString(3, produto.getStatus());

            int resultado = prep.executeUpdate();

            prep.close();
            conn.close();

            return resultado > 0;

        } catch (SQLException erro) {

            JOptionPane.showMessageDialog(
                null,
                "ERRO AO CADASTRAR:\n" + erro.getMessage()
            );

            return false;
        }
    }

    // ==============================
    // LISTAR TODOS OS PRODUTOS
    // ==============================
    public ArrayList<ProdutosDTO> listarProdutos() {

        ArrayList<ProdutosDTO> lista = new ArrayList<>();

        String sql = "SELECT id, nome, valor, status FROM produtos";

        try {

            conn = new conectaDAO().connectDB();

            if (conn == null) {
                return lista;
            }

            prep = conn.prepareStatement(sql);
            resultset = prep.executeQuery();

            while (resultset.next()) {

                ProdutosDTO produto = new ProdutosDTO();

                produto.setId(resultset.getInt("id"));
                produto.setNome(resultset.getString("nome"));
                produto.setValor(resultset.getInt("valor"));
                produto.setStatus(resultset.getString("status"));

                lista.add(produto);
            }

            resultset.close();
            prep.close();
            conn.close();

        } catch (SQLException erro) {

            JOptionPane.showMessageDialog(
                null,
                "Erro ao listar produtos:\n" + erro.getMessage()
            );
        }

        return lista;
    }

    // ==============================
    // VENDER PRODUTO
    // ==============================
    public boolean venderProduto(int id) {

        conn = new conectaDAO().connectDB();

        if (conn == null) {
            return false;
        }

        String sql = "UPDATE produtos SET status = ? WHERE id = ?";

        try {

            prep = conn.prepareStatement(sql);

            prep.setString(1, "Vendido");
            prep.setInt(2, id);

            int resultado = prep.executeUpdate();

            prep.close();
            conn.close();

            return resultado > 0;

        } catch (SQLException erro) {

            JOptionPane.showMessageDialog(
                null,
                "Erro ao vender produto:\n" + erro.getMessage()
            );

            return false;
        }
    }

    // ==============================
    // LISTAR PRODUTOS VENDIDOS
    // ==============================
    public ArrayList<ProdutosDTO> listarProdutosVendidos() {

        ArrayList<ProdutosDTO> lista = new ArrayList<>();

        String sql =
            "SELECT id, nome, valor, status FROM produtos WHERE status = ?";

        try {

            conn = new conectaDAO().connectDB();

            if (conn == null) {
                return lista;
            }

            prep = conn.prepareStatement(sql);

            prep.setString(1, "Vendido");

            resultset = prep.executeQuery();

            while (resultset.next()) {

                ProdutosDTO produto = new ProdutosDTO();

                produto.setId(resultset.getInt("id"));
                produto.setNome(resultset.getString("nome"));
                produto.setValor(resultset.getInt("valor"));
                produto.setStatus(resultset.getString("status"));

                lista.add(produto);
            }

            resultset.close();
            prep.close();
            conn.close();

        } catch (SQLException erro) {

            JOptionPane.showMessageDialog(
                null,
                "Erro ao listar produtos vendidos:\n"
                + erro.getMessage()
            );
        }

        return lista;
    }
}