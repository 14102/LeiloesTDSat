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
    ArrayList<ProdutosDTO> listagem = new ArrayList<>();

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

    public ArrayList<ProdutosDTO> listarProdutos() {

        return listagem;
    }
}