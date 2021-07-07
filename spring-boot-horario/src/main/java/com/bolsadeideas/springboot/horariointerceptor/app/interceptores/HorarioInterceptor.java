package com.bolsadeideas.springboot.horariointerceptor.app.interceptores;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.bolsadeideas.springboot.horariointerceptor.app.services.IHorarioAperturaService;

@Component("controlHorarioInterceptor")
public class HorarioInterceptor implements HandlerInterceptor{

	@Value("${config.horario.apertura}")
	private String apertura;

	@Value("${config.horario.cierre}")
	private String cierre;
	
	private static final String STR_MENSAJE = "mensaje";
	private static final String STR_HORARIO_MSG = "horarioMensaje";

	@Autowired
	private IHorarioAperturaService horarioApertura;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		
		boolean esHorarioAtencion = horarioApertura.esHorarioDeAtencion();
		
		
		if (handler instanceof HandlerMethod && esHorarioAtencion) {

				var mensaje = new StringBuilder("Bienvenidos al horario de atencion al clientes");
				mensaje.append(", atendemos desde las ");
				mensaje.append(apertura);
				mensaje.append(" hrs. hasta las ");
				mensaje.append(cierre);
				mensaje.append(" hrs. Gracias por su visita");
				request.setAttribute(STR_MENSAJE, mensaje.toString());
				return true;
		} 
		
		response.sendRedirect(request.getContextPath().concat("/cerrado"));
		return false;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		
		if(handler instanceof HandlerMethod 
				&& modelAndView!=null
				&& request.getAttribute(STR_MENSAJE)!=null) {
			
			var mensaje = (String)request.getAttribute(STR_MENSAJE);
			modelAndView.addObject(STR_HORARIO_MSG, mensaje);
			
		}
			
	}

}
