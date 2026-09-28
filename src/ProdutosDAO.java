import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class ProdutosDAO {

    Connection conn;
    PreparedStatement pstm;
    ResultSet rs;

    // CADASTRAR PRODUTO
    public boolean cadastrarProduto(ProdutosDTO produto) {

        String sql = "INSERT INTO produtos (nome, valor, status) VALUES (?, ?, ?)";

        try {

            conn = new conectaDAO().connectDB();

            pstm = conn.prepareStatement(sql);

            pstm.setString(1, produto.getNome());
            pstm.setInt(2, produto.getValor());
            pstm.setString(3, produto.getStatus());

            pstm.executeUpdate();

            return true;

        } catch (Exception e) {

            e.printStackTrace();
            return false;
        }
    }

    // LISTAR TODOS OS PRODUTOS
    public ArrayList<ProdutosDTO> listarProdutos() {

        ArrayList<ProdutosDTO> lista = new ArrayList<>();

        String sql = "SELECT * FROM produtos";

        try {

            conn = new conectaDAO().connectDB();

            pstm = conn.prepareStatement(sql);

            rs = pstm.executeQuery();

            while (rs.next()) {

                ProdutosDTO produto = new ProdutosDTO();

                produto.setId(rs.getInt("id"));
                produto.setNome(rs.getString("nome"));
                produto.setValor(rs.getInt("valor"));
                produto.setStatus(rs.getString("status"));

                lista.add(produto);
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return lista;
    }

    // VENDER PRODUTO
    public boolean venderProduto(int codigo) {

        String sql = "UPDATE produtos SET status = ? WHERE id = ?";

        try {

            conn = new conectaDAO().connectDB();

            pstm = conn.prepareStatement(sql);

            pstm.setString(1, "Vendido");
            pstm.setInt(2, codigo);

            int resultado = pstm.executeUpdate();

            return resultado > 0;

        } catch (Exception e) {

            e.printStackTrace();
            return false;
        }
    }

    // LISTAR SOMENTE PRODUTOS VENDIDOS
    public ArrayList<ProdutosDTO> listarProdutosVendidos() {

        ArrayList<ProdutosDTO> lista = new ArrayList<>();

        String sql = "SELECT * FROM produtos WHERE status = ?";

        try {

            conn = new conectaDAO().connectDB();

            pstm = conn.prepareStatement(sql);

            pstm.setString(1, "Vendido");

            rs = pstm.executeQuery();

            while (rs.next()) {

                ProdutosDTO produto = new ProdutosDTO();

                produto.setId(rs.getInt("id"));
                produto.setNome(rs.getString("nome"));
                produto.setValor(rs.getInt("valor"));
                produto.setStatus(rs.getString("status"));

                lista.add(produto);
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return lista;
    }
}