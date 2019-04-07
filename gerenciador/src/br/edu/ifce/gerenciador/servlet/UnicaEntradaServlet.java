package br.edu.ifce.gerenciador.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.edu.ifce.gerenciador.acao.Acao;
import br.edu.ifce.gerenciador.acao.AlteraEmpresa;
import br.edu.ifce.gerenciador.acao.ListaEmpresas;
import br.edu.ifce.gerenciador.acao.MostraEmpresa;
import br.edu.ifce.gerenciador.acao.NovaEmpresa;
import br.edu.ifce.gerenciador.acao.RemoveEmpresa;

@WebServlet("/entrada")
public class UnicaEntradaServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void service(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {

		String paramAcao = request.getParameter("acao");
		String nomeDaAcao;
		nomeDaAcao = null;

		try {
			Class classe = Class.forName("br.edu.ifce.gerenciador.acao." + paramAcao);
			Acao acao = (Acao) classe.newInstance();
			
			nomeDaAcao = acao.executa(request, response);
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		
		String[] acoes = nomeDaAcao.split(":");
		
		if(acoes[0].equals("forward")) {
			RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/view/"+acoes[1]);
			rd.forward(request, response);
		}else{
			response.sendRedirect(acoes[1]);
		}

    }

}