package com.bolsadeideas.springboot.horariointerceptor.app.services;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class HorarioAperturaService implements IHorarioAperturaService {

	@Value("${config.horario.apertura}")
	private String apertura;

	@Value("${config.horario.cierre}")
	private String cierre;

	@Override
	public boolean esHorarioDeAtencion() {

		int hora = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);

		var horaApertura = Integer.parseInt(apertura);
		var horaCierre = Integer.parseInt(cierre);

		return (hora >= horaApertura && hora <= horaCierre);

	}

}
