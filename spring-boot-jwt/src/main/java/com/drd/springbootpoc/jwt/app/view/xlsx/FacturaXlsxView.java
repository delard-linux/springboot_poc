package com.drd.springbootpoc.jwt.app.view.xlsx;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.view.document.AbstractXlsxView;

import com.drd.springbootpoc.jwt.app.model.domain.FacturaDTO;
import com.drd.springbootpoc.jwt.app.model.domain.ItemFacturaDTO;

@Component("/factura/ver.xlsx")
public class FacturaXlsxView extends AbstractXlsxView {

	@Autowired
    private MessageSource messageSource;
	
	@Autowired
    private LocaleResolver localeResolver;
	
	@Override
	protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		var locale = localeResolver.resolveLocale(request);
		
		var factura = (FacturaDTO) model.get("facturadto");
		response.setHeader("Content-Disposition", "attachment; filename=factura_" 
								+ factura.getDescripcion() 
								+ ".xlsx");
		var sheet = workbook.createSheet("Factura");
		
		var row = sheet.createRow(0);
		var cell = row.createCell(0);
		cell.setCellValue(messageSource.getMessage("text.factura.ver.datos.cliente", null, locale));
		
		row = sheet.createRow(1);
		cell = row.createCell(0);
		cell.setCellValue(factura.getCliente().getNombre() + " " + factura.getCliente().getApellido());
		
		row = sheet.createRow(2);
		cell= row.createCell(0);
		cell.setCellValue(factura.getCliente().getEmail());
		
		sheet.createRow(4).createCell(0).setCellValue(messageSource.getMessage("text.factura.ver.datos.factura", null, locale));
		sheet.createRow(5).createCell(0).setCellValue(messageSource.getMessage("text.cliente.factura.folio", null, locale) + ": " +  factura.getId());
		sheet.createRow(6).createCell(0).setCellValue(messageSource.getMessage("text.cliente.factura.descripcion", null, locale) + ": " + factura.getDescripcion());
		sheet.createRow(7).createCell(0).setCellValue(messageSource.getMessage("text.cliente.factura.fecha", null, locale) + ": " + factura.getCreateAt());
		
		var theaderStyle = workbook.createCellStyle();
		theaderStyle.setBorderBottom(BorderStyle.MEDIUM);
		theaderStyle.setBorderTop(BorderStyle.MEDIUM);
		theaderStyle.setBorderRight(BorderStyle.MEDIUM);
		theaderStyle.setBorderLeft(BorderStyle.MEDIUM);
		theaderStyle.setFillForegroundColor(IndexedColors.GOLD.index);
		theaderStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		
		var tbodyStyle = workbook.createCellStyle();
		tbodyStyle.setBorderBottom(BorderStyle.THIN);
		tbodyStyle.setBorderTop(BorderStyle.THIN);
		tbodyStyle.setBorderRight(BorderStyle.THIN);
		tbodyStyle.setBorderLeft(BorderStyle.THIN);
		
		var header = sheet.createRow(9);
		header.createCell(0).setCellValue(messageSource.getMessage("text.factura.form.item.nombre", null, locale));
		header.createCell(1).setCellValue(messageSource.getMessage("text.factura.form.item.precio", null, locale));
		header.createCell(2).setCellValue(messageSource.getMessage("text.factura.form.item.cantidad", null, locale));
		header.createCell(3).setCellValue(messageSource.getMessage("text.factura.form.item.total", null, locale));
		
		header.getCell(0).setCellStyle(theaderStyle);
		header.getCell(1).setCellStyle(theaderStyle);
		header.getCell(2).setCellStyle(theaderStyle);
		header.getCell(3).setCellStyle(theaderStyle);
		
		var rownum = 10;
		
		for(ItemFacturaDTO item: factura.getItems()) {
			var fila = sheet.createRow(rownum ++);
			cell = fila.createCell(0);
			cell.setCellValue(item.getProducto().getNombre());
			cell.setCellStyle(tbodyStyle);
			
			cell = fila.createCell(1);
			cell.setCellValue(item.getProducto().getPrecio());
			cell.setCellStyle(tbodyStyle);
			
			cell = fila.createCell(2);
			cell.setCellValue(item.getCantidad());
			cell.setCellStyle(tbodyStyle);
			
			cell = fila.createCell(3);
			cell.setCellValue(item.calcularImporte());
			cell.setCellStyle(tbodyStyle);
		}
		
		var filatotal = sheet.createRow(rownum);
		cell = filatotal.createCell(2);
		cell.setCellValue(messageSource.getMessage("text.factura.form.total", null, locale) + ": ");
		cell.setCellStyle(tbodyStyle);
		
		cell= filatotal.createCell(3);
		cell.setCellValue(factura.getTotal());
		cell.setCellStyle(tbodyStyle);
	
	}

}