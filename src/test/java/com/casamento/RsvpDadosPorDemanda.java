package com.casamento;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.roo.addon.dod.RooDataOnDemand;
import org.springframework.stereotype.Component;

@Configurable
@Component
@RooDataOnDemand(entity = Rsvp.class)
public class RsvpDadosPorDemanda {

	private Random rnd = new SecureRandom();

	private List<Rsvp> data;

	public Rsvp getNewTransientRsvp(int index) {
        Rsvp obj = new Rsvp();
        setAtendido(obj, index);
        setCodigo(obj, index);
        setDataAtendimento(obj, index);
        setEmail(obj, index);
        setObservacao(obj, index);
        return obj;
    }

	public void setAtendido(Rsvp obj, int index) {
        Integer atendido = new Integer(index);
        obj.setAtendido(atendido);
    }

	public void setCodigo(Rsvp obj, int index) {
        String codigo = "codigo_" + index;
        if (codigo.length() > 30) {
            codigo = codigo.substring(0, 30);
        }
        obj.setCodigo(codigo);
    }

	public void setDataAtendimento(Rsvp obj, int index) {
        Date dataAtendimento = new GregorianCalendar(Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH), Calendar.getInstance().get(Calendar.HOUR_OF_DAY), Calendar.getInstance().get(Calendar.MINUTE), Calendar.getInstance().get(Calendar.SECOND) + new Double(Math.random() * 1000).intValue()).getTime();
        obj.setDataAtendimento(dataAtendimento);
    }

	public void setEmail(Rsvp obj, int index) {
        String email = "foo" + index + "@bar.com";
        if (email.length() > 30) {
            email = email.substring(0, 30);
        }
        obj.setEmail(email);
    }

	public void setObservacao(Rsvp obj, int index) {
        String observacao = "observacao_" + index;
        if (observacao.length() > 100) {
            observacao = observacao.substring(0, 100);
        }
        obj.setObservacao(observacao);
    }

	public Rsvp getSpecificRsvp(int index) {
        init();
        if (index < 0) {
            index = 0;
        }
        if (index > (data.size() - 1)) {
            index = data.size() - 1;
        }
        Rsvp obj = data.get(index);
        Long id = obj.getId();
        return Rsvp.findRsvp(id);
    }

	public Rsvp getRandomRsvp() {
        init();
        Rsvp obj = data.get(rnd.nextInt(data.size()));
        Long id = obj.getId();
        return Rsvp.findRsvp(id);
    }

	public boolean modifyRsvp(Rsvp obj) {
        return false;
    }

	public void init() {
        int from = 0;
        int to = 10;
        data = Rsvp.findRsvpEntries(from, to);
        if (data == null) {
            throw new IllegalStateException("Find entries implementation for 'Rsvp' illegally returned null");
        }
        if (!data.isEmpty()) {
            return;
        }
        
        data = new ArrayList<Rsvp>();
        for (int i = 0; i < 10; i++) {
            Rsvp obj = getNewTransientRsvp(i);
            try {
                obj.persist();
            } catch (ConstraintViolationException e) {
                StringBuilder msg = new StringBuilder();
                for (Iterator<ConstraintViolation<?>> iter = e.getConstraintViolations().iterator(); iter.hasNext();) {
                    ConstraintViolation<?> cv = iter.next();
                    msg.append("[").append(cv.getConstraintDescriptor()).append(":").append(cv.getMessage()).append("=").append(cv.getInvalidValue()).append("]");
                }
                throw new RuntimeException(msg.toString(), e);
            }
            obj.flush();
            data.add(obj);
        }
    }
}
