package persist;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import entity.Aluno;

public class AlunoDAO extends ConnectionDAO {
	
	private Connection conn = null;
	
	public AlunoDAO() {
		try {
			conn = getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	//
	
	public void save(Aluno f) throws SQLException {

		PreparedStatement stmt = null;
		try {
			if (f.getId() == null) {
				stmt = conn.prepareStatement(
						"insert into aluno (matricula, nome, email) VALUES (?, ?, ?)",
						Statement.RETURN_GENERATED_KEYS);
			} else {
				stmt = conn.prepareStatement(
						"update aluno set matricula = ?, nome = ?, email = ? where id = ?");
			}
			stmt.setString(1, f.getMatricula());
			stmt.setString(2, f.getNome());
			stmt.setString(3, f.getEmail());
			

			if (f.getId() != null) {
				// Update
				stmt.setLong(4, f.getId());
			}

			int count = stmt.executeUpdate();

			if (count == 0) {
				throw new SQLException("Erro ao inserir o aluno");
			}
			// Se inseriu, ler o id auto incremento
			if (f.getId() == null) {
				Long id = getGeneratedId(stmt);
				f.setId(id);
			}
		} finally {
			if (stmt != null) {
				stmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		}
	}
	
	//
	
	public Aluno getAlunoById(Long id) throws SQLException {

		PreparedStatement stmt = null;

		try {
			conn = getConnection();
			stmt = conn.prepareStatement("select * from aluno where id = ?");

			stmt.setLong(1, id);

			ResultSet rs = stmt.executeQuery();
			if (rs.next()) { // rs.next verifica se a busca restornou algum resultado
				Aluno f = createAluno(rs);
				rs.close();
				return f;
			}
		} finally {
			if (stmt != null) {
				stmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		}
		return null;
	}
	
	//
	
	public static Long getGeneratedId(Statement stmt) throws SQLException {

		ResultSet rs = stmt.getGeneratedKeys();

		if (rs.next()) {
			Long id = rs.getLong(1);
			return id;
		}

		return 0L;
	}
	
	//
	
	public Aluno createAluno(ResultSet rs) throws SQLException {

		Aluno f = null;

		f = new Aluno();
		f.setId(rs.getLong(1));
		f.setMatricula(rs.getString(2));
		f.setNome(rs.getString(3));
		f.setEmail(rs.getString(4));
		

		return f;
	}

}
