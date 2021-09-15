package br.com.emanuelgabriel.projeto01.domain.repository.customers;

import java.io.IOException;

import org.springframework.boot.jackson.JsonComponent;
import org.springframework.data.domain.PageImpl;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

/**
 * 
 * @author emanuel.sousa
 *
 */

@JsonComponent
public class PageJson {

	public static class PageSerializer extends JsonSerializer<PageImpl<?>> {

		@Override
		public void serialize(PageImpl<?> page, JsonGenerator jsonGenerator, SerializerProvider serializers) throws IOException {

			jsonGenerator.writeStartObject();			
			
			// customização do Page
			jsonGenerator.writeObjectField("itens", page.getContent()); // conteúdo
			jsonGenerator.writeNumberField("totalPaginas", page.getTotalPages()); // total de páginas
			jsonGenerator.writeNumberField("quantidadePorPagina", page.getSize()); // retorna o tamanho de itens do Page
			jsonGenerator.writeObjectField("total", page.getTotalElements()); // total de elementos
			
			
			jsonGenerator.writeEndObject();
			
		}

	}

}
