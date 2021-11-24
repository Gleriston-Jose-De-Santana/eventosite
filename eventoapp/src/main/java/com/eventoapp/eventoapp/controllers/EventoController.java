package com.eventoapp.eventoapp.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.eventoapp.eventoapp.models.Convidado;
import com.eventoapp.eventoapp.models.Evento;
import com.eventoapp.eventoapp.repository.ConvidadoRepository;
import com.eventoapp.eventoapp.repository.EventosRepository;

@Controller
public class EventoController {
	
	@Autowired
	private EventosRepository eventoRepository;
	
	@Autowired
	private ConvidadoRepository convidadoRepository;
	
	@RequestMapping(value="/cadastrarEvento", method=RequestMethod.GET)
	public String form() {
		return "evento/formEvento";
	}
	
	@RequestMapping(value="/cadastrarEvento", method=RequestMethod.POST)
	public String form(@Valid Evento evento, BindingResult result, RedirectAttributes attributes) {
		if(result.hasErrors()){
			attributes.addFlashAttribute("Mensagem", "Verifique os campos!");
			return "redirect:/cadastrarEvento";
		}
		eventoRepository.save(evento);
		attributes.addFlashAttribute("Mensagem", "Evento cadastrado com sucesso!");
		return "redirect:/cadastrarEvento";
	}
	
	@RequestMapping(value="/eventos")
	public ModelAndView listarEventos() {
		ModelAndView mv = new ModelAndView("index");
		Iterable<Evento> eventos = eventoRepository.findAll();
		mv.addObject("eventos", eventos);
		return mv;
	}
	
	@RequestMapping(value="/{codigo}", method=RequestMethod.GET)
	public ModelAndView detalhesEvento(@PathVariable("codigo") long codigo) {
	    Evento evento = eventoRepository.findByCodigo(codigo);
	    ModelAndView mv = new ModelAndView("evento/detalhesEvento");
	    mv.addObject("evento", evento);
	    
	    Iterable<Convidado> convidados = convidadoRepository.findByEvento(evento);
	    mv.addObject("convidados", convidados);
		return mv;
	}

	@RequestMapping(value="/{codigo}", method=RequestMethod.POST)
	public String detalhesEventoPost(@PathVariable("codigo") long codigo, @Valid Convidado convidado, BindingResult result, RedirectAttributes attributes) {
		if(result.hasErrors()) {
			attributes.addFlashAttribute("Mensagem", "Verifique os campos!");
			return "redirect:/{codigo}";
		}
		Evento evento = eventoRepository.findByCodigo(codigo);
		convidado.setEvento(evento);
		convidadoRepository.save(convidado);
		attributes.addFlashAttribute("Mensagem", "Convidado cadastrado com sucesso!");
		return "redirect:/{codigo}";
	}
	
	@RequestMapping("/deletar")
	public String deletarEvento(long codigo) {
		Evento evento = eventoRepository.findByCodigo(codigo);
		eventoRepository.delete(evento);
		return "redirect:/eventos";
	}
	
	@RequestMapping("/deletarConvidado")
	public String deletarConvidado(String rg) {
		Convidado convidado = convidadoRepository.findByRg(rg);
		convidadoRepository.delete(convidado);
		
		Evento evento = convidado.getEvento();
		Long codigoLong = evento.getCodigo();
		String codigo = "" + codigoLong;
		return "redirect:/" + codigo;
	}
}
