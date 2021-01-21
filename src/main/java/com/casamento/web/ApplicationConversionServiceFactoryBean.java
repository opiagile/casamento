package com.casamento.web;

import com.casamento.Rsvp;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.support.FormattingConversionServiceFactoryBean;
import org.springframework.roo.addon.web.mvc.controller.converter.RooConversionService;

@Configurable
@RooConversionService
public class ApplicationConversionServiceFactoryBean extends FormattingConversionServiceFactoryBean {

	@Override
	protected void installFormatters(FormatterRegistry registry) {
		super.installFormatters(registry);
	}

	public Converter<Rsvp, String> getRsvpToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<com.casamento.Rsvp, java.lang.String>() {
            public String convert(Rsvp rsvp) {
                return new StringBuilder().append(rsvp.getCodigo()).append(' ').append(rsvp.getEmail()).append(' ').append(rsvp.getAtendido()).append(' ').append(rsvp.getObservacao()).toString();
            }
        };
    }

	public Converter<Long, Rsvp> getIdToRsvpConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, com.casamento.Rsvp>() {
            public com.casamento.Rsvp convert(java.lang.Long id) {
                return Rsvp.findRsvp(id);
            }
        };
    }

	public Converter<String, Rsvp> getStringToRsvpConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, com.casamento.Rsvp>() {
            public com.casamento.Rsvp convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), Rsvp.class);
            }
        };
    }

	public void installLabelConverters(FormatterRegistry registry) {
        registry.addConverter(getRsvpToStringConverter());
        registry.addConverter(getIdToRsvpConverter());
        registry.addConverter(getStringToRsvpConverter());
    }

	public void afterPropertiesSet() {
        super.afterPropertiesSet();
        installLabelConverters(getObject());
    }
}
