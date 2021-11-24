package com.eventoapp.eventoapp.repository;

import org.springframework.data.repository.CrudRepository;

import com.eventoapp.eventoapp.models.Evento;

public interface EventosRepository extends CrudRepository<Evento, Long>{
	
	Evento findByCodigo(long codigo);

}
