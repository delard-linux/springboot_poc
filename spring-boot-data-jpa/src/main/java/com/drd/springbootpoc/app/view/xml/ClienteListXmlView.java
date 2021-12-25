package com.drd.springbootpoc.app.view.xml;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.xml.MarshallingView;

import com.drd.springbootpoc.app.controllers.ConstantesController;
import com.drd.springbootpoc.app.model.domain.ClienteDTO;

@Component("listar.xml")
public class ClienteListXmlView extends MarshallingView {
	
	
	@Autowired
	public ClienteListXmlView(Jaxb2Marshaller marshaller) {
		super(marshaller);
	}

	@Override
	protected boolean generatesDownloadContent() {
		return true;
	}
	
	@Override
	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		model.remove(ConstantesController.ATT_TITULO);
		model.remove(ConstantesController.ATT_PAGINA);
		model.remove(ConstantesController.ATT_CLIENTE_SEARCH_CRIT);
		
		@SuppressWarnings("unchecked")
		var clientes = (List<ClienteDTO>)model.get(ConstantesController.ATT_CLIENTE_LIST);
		
		model.remove(ConstantesController.ATT_CLIENTE_LIST);

		model.put("clienteList", new ClienteList(clientes));
		
		super.renderMergedOutputModel(model, request, response);

	}

}