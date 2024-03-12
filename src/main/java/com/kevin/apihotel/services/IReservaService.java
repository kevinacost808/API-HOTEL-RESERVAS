package com.kevin.apihotel.services;

import com.kevin.apihotel.model.dto.ReservaDto;
import com.kevin.apihotel.model.entity.Reserva;

import java.util.List;

public interface IReservaService{
    List<Reserva> getAllReserva();
    Reserva getById(Long id);
    Reserva saveReserva(ReservaDto reservaDto);
    void deleteReserva(Reserva reserva);
    boolean existIdReserva(Long id);
}
