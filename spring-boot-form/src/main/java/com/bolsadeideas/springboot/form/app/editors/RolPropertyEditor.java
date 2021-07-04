package com.bolsadeideas.springboot.form.app.editors;

import java.beans.PropertyEditorSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bolsadeideas.springboot.form.app.services.RolService;

@Component
public class RolPropertyEditor extends PropertyEditorSupport {

	@Autowired
	private RolService rolService;

	@Override
	public void setAsText(String idString) throws IllegalArgumentException {
		try {
			Integer id = Integer.parseInt(idString);
			this.setValue(rolService.obtenerPorId(id));				
		} catch (Exception e) {
			this.setValue(null);
		}
	}

}
