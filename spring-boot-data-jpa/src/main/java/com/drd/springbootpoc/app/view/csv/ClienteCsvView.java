package com.drd.springbootpoc.app.view.csv;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.AbstractView;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import com.drd.springbootpoc.app.controllers.ConstantesController;
import com.drd.springbootpoc.app.model.domain.ClienteDTO;

@Component("listar.csv")
public class ClienteCsvView extends AbstractView {
	
	public ClienteCsvView() {
		setContentType("text/csv");
	}

	@Override
	protected boolean generatesDownloadContent() {
		return true;
	}
	
	@Override
	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		response.setHeader("Content-Disposition", "attachment; filename=\"clientes.csv\"");
		response.setContentType(getContentType());
		
		@SuppressWarnings("unchecked")
		var clientes = (List<ClienteDTO>)model.get(ConstantesController.ATT_CLIENTE_LIST);
		
		var beanWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.EXCEL_NORTH_EUROPE_PREFERENCE); 
		
		
		
		var header = new String[]{"id", "nombre", "apellido", "email", "createAt"};
		beanWriter.writeHeader(header);
	
		for (var cl :  clientes) {
			beanWriter.write(cl, header);
		}
		beanWriter.close();
		
	}


	


}