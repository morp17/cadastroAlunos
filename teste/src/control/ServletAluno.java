package control;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entity.Aluno;
import persist.AlunoDAO;


@WebServlet({ "/aluno/listar", "/aluno/cadastrar", "/aluno/editar", "/aluno/buscar" })
public class ServletAluno extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
       
	private static final String URL_ALUNO = "/aluno/";
	
    public ServletAluno() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    //

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		if (request.getServletPath().equals(URL_ALUNO + "listar")) {
			listar(response);
		} else if (request.getServletPath().equals(URL_ALUNO + "buscar")) {
			buscar(response);
		} else if (request.getServletPath().equals(URL_ALUNO + "excluir")) {
			excluir(response);
		}
	}
	
	//

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (request.getServletPath().equals(URL_ALUNO + "cadastrar")) {
			Aluno aluno = new Aluno();

			if(request.getParameter("id") != null)
				aluno.setId(Long.parseLong(request.getParameter("id")));
			
			aluno.setMatricula(request.getParameter("matricula"));
			aluno.setNome(request.getParameter("nome"));
			aluno.setEmail(request.getParameter("email"));
			
			
			cadastrar(aluno, response);
			
		} else if (request.getServletPath().equals(URL_ALUNO + "editar")) {
			editar(response);
		}
	}

	//
	
	private void cadastrar(Aluno aluno, HttpServletResponse response) throws IOException {
		AlunoDAO bd = new AlunoDAO();
		String msg = "";
		try {
			bd.save(aluno);
			msg = "Aluno cadastrado/editado com sucesso.";
		} catch (SQLException e) {
			e.printStackTrace();
			msg = "Falha ao gravar/editar o aluno";
		}
		
		response.getWriter().append(msg);
	}
	
	//
	
	private void listar(HttpServletResponse response) throws IOException {
		response.getWriter().append("Request via listar");
	}
	
	//
	
	private void buscar(HttpServletResponse response) throws IOException {
		response.getWriter().append("Request via buscar");
	}
	
	//
	
	private void editar(HttpServletResponse response) throws IOException {
		response.getWriter().append("Request via editar");
	}
	
	//

	private void excluir(HttpServletResponse response) throws IOException {
		response.getWriter().append("Request via excluir");
	}


}
