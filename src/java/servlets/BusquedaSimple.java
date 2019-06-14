package servlets;

import controller.Mensaje;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import jade.core.Profile;
import jade.util.leap.Properties;
import jade.wrapper.gateway.JadeGateway;
import java.util.ArrayList;
import javax.servlet.http.HttpSession;


@WebServlet(name = "Servlet", urlPatterns = {"/Servlet"})
public class BusquedaSimple extends HttpServlet{  
    private JadeGateway gateway = null;
    Properties pp = new Properties();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        try{
        doPost(request, response);
        }catch(Exception e){
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            HttpSession respuesta = request.getSession(true);
            request.getSession().removeAttribute("listado");
            request.getSession().removeAttribute("ky");
            String keywords = new String(request.getParameter("keywords").getBytes("ISO-8859-1"),"UTF-8"); 
            System.out.println(keywords);
            Mensaje mensaje = new Mensaje();
            mensaje.setMensaje("BS");
            mensaje.setArgumentos(keywords);
            try{
                JadeGateway.execute(mensaje); 
            }catch(Exception e){
                e.printStackTrace();
            }

            ArrayList<String[]> al =(ArrayList<String[]>) mensaje.getRespuesta();
            respuesta.setAttribute("listado", al);
            respuesta.setAttribute("ky", keywords);
            response.sendRedirect("resultados.jsp?page=1");
  
    }
    
    @Override
    public void init()throws ServletException{
        pp.setProperty(Profile.MAIN_HOST, "localhost");
        pp.setProperty(Profile.MAIN_PORT, "1099");
       //pp.setProperty(Profile.MAIN,"main");
        JadeGateway.init("sma.AgenteInterfaz",pp);
    }

}
