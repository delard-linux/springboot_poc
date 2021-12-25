package com.drd.springbootpoc.app.view.json;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.drd.springbootpoc.app.controllers.ConstantesController;
import com.drd.springbootpoc.app.model.domain.ClienteDTO;

@Component("listar.json")
public class ClienteListJsonView extends MappingJackson2JsonView{

	@Override
	protected Object filterModel(Map<String, Object> model) {

		model.remove(ConstantesController.ATT_TITULO);
		model.remove(ConstantesController.ATT_PAGINA);
		model.remove(ConstantesController.ATT_CLIENTE_SEARCH_CRIT);
		
		@SuppressWarnings("unchecked")
		var clientes = (List<ClienteDTO>)model.get(ConstantesController.ATT_CLIENTE_LIST);
		
		model.remove(ConstantesController.ATT_CLIENTE_LIST);

		model.put("clienteList", clientes);
		
		return super.filterModel(model);
	}
	
}
