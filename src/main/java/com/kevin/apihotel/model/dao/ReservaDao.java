package com.kevin.apihotel.model.dao;

import com.kevin.apihotel.model.entity.Reserva;
import org.springframework.data.repository.CrudRepository;

public interface ReservaDao extends CrudRepository<Reserva, Long> {
}
