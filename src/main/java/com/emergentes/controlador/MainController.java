/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.emergentes.controlador;

import com.emergentes.modelo.GestorCategoria;
import com.emergentes.modelo.GestorLibros;
import com.emergentes.modelo.categoria;
import com.emergentes.modelo.libro;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Boris Leonel
 */
@WebServlet(name = "MainController", urlPatterns = {"/MainController"})
public class MainController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet MainController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet MainController at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        libro objLibro = new libro();
        categoria objCate = new categoria();
        int id;
        int pos;
        String opcion = request.getParameter("op");
        String opcion2 = request.getParameter("tipo");
        String op = (opcion != null) ? request.getParameter("op") : "view";
        String tipo = (opcion2 != null) ? request.getParameter("tipo") : "view";
        if (tipo.equals("libros")) {
            if (op.equals("nuevo")) {
                HttpSession ses = request.getSession();
                GestorLibros agenda = (GestorLibros) ses.getAttribute("listalibro");
                objLibro.setId(agenda.obtieneId());
                request.setAttribute("op", op);
                request.setAttribute("miLibro", objLibro);
                request.getRequestDispatcher("registro.jsp").forward(request, response);
            }

            if (op.equals("modificar")) {
                id = Integer.parseInt(request.getParameter("id"));
                HttpSession ses = request.getSession();
                GestorLibros agenda = (GestorLibros) ses.getAttribute("listalibro");
                pos = agenda.ubicarTarea(id);
                objLibro = agenda.getLista().get(pos);

                request.setAttribute("op", op);
                request.setAttribute("miLibro", objLibro);
                request.getRequestDispatcher("registro.jsp").forward(request, response);
            }

            if (op.equals("eliminar")) {
                id = Integer.parseInt(request.getParameter("id"));
                HttpSession ses = request.getSession();
                GestorLibros agenda = (GestorLibros) ses.getAttribute("listalibro");
                pos = agenda.ubicarTarea(id);

                agenda.eliminarTarea(pos);
                ses.setAttribute("listalibro", agenda);
                response.sendRedirect("libro.jsp");
            }
        }
        if (tipo.equals("categorias")) {
            if (op.equals("nuevo")) {
                HttpSession ses = request.getSession();
                GestorCategoria agenda = (GestorCategoria) ses.getAttribute("listacate");
                objCate.setId(agenda.obtieneId());
                request.setAttribute("op", op);
                request.setAttribute("miCate", objCate);
                request.getRequestDispatcher("editaCate.jsp").forward(request, response);
            }

            if (op.equals("modificar")) {
                id = Integer.parseInt(request.getParameter("id"));
                HttpSession ses = request.getSession();
                GestorCategoria agenda = (GestorCategoria) ses.getAttribute("listacate");
                pos = agenda.ubicarTarea(id);
                objCate = agenda.getLista().get(pos);

                request.setAttribute("op", op);
                request.setAttribute("miCate", objCate);
                request.getRequestDispatcher("editaCate.jsp").forward(request, response);
            }

            if (op.equals("eliminar")) {
                id = Integer.parseInt(request.getParameter("id"));
                HttpSession ses = request.getSession();
                GestorCategoria agenda = (GestorCategoria) ses.getAttribute("listacate");
                pos = agenda.ubicarTarea(id);

                agenda.eliminarTarea(pos);
                ses.setAttribute("listacate", agenda);
                response.sendRedirect("categoria.jsp");
            }
        }

        
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        libro objTarea = new libro();
        categoria objCate = new categoria();
        int pos;
        String op = request.getParameter("op");
        String tipo = request.getParameter("tipo");
        if (tipo.equals("libros")) {
            if (op.equals("grabar")) {
                objTarea.setId(Integer.parseInt(request.getParameter("id")));
                objTarea.setTitulo(request.getParameter("titulo"));
                objTarea.setAutor(request.getParameter("autor"));
                objTarea.setDisponible(request.getParameter("disponible"));
                objTarea.setCategoria(request.getParameter("categoria"));

                HttpSession ses = request.getSession();
                GestorLibros agenda = (GestorLibros) ses.getAttribute("listalibro");

                String opg = request.getParameter("opg");
                if (opg.equals("nuevo")) {
                    agenda.insertarTarea(objTarea);
                } else {
                    pos = agenda.ubicarTarea(objTarea.getId());
                    agenda.modificarTarea(pos, objTarea);
                }
                ses.setAttribute("listalibro", agenda);
                response.sendRedirect("libro.jsp");
            }
        }
        if (tipo.equals("categorias")) {
            if (op.equals("grabar")) {
                objCate.setId(Integer.parseInt(request.getParameter("id")));
                objCate.setCategoria(request.getParameter("categoria"));


                HttpSession ses = request.getSession();
                GestorCategoria agenda = (GestorCategoria) ses.getAttribute("listacate");

                String opg = request.getParameter("opg");
                if (opg.equals("nuevo")) {
                    agenda.insertarTarea(objCate);
                } else {
                    pos = agenda.ubicarTarea(objCate.getId());
                    agenda.modificarTarea(pos, objCate);
                }
                ses.setAttribute("listacate", agenda);
                response.sendRedirect("categoria.jsp");
            }
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
