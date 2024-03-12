package com.kevin.apihotel.services.impl;

import com.kevin.apihotel.model.dao.ReservaDao;
import com.kevin.apihotel.model.dto.ReservaDto;
import com.kevin.apihotel.model.entity.Reserva;
import com.kevin.apihotel.services.IReservaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ReservaServiceImpl implements IReservaService {
    @Autowired
    private ReservaDao reservaDao;

    @Transactional(readOnly = true)
    @Override
    public List<Reserva> getAllReserva() {
        return (List<Reserva>) reservaDao.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Reserva getById(Long id) {
        return reservaDao.findById(id).orElse(null);
    }

    @Transactional
    @Override
    public Reserva saveReserva(ReservaDto reservaDto) {
        Reserva reserva = Reserva.builder()
                .idReserva(reservaDto.getIdReserva())
                .fechaEntrada(reservaDto.getFechaEntrada())
                .fechaSalida(reservaDto.getFechaSalida())
                .precio(reservaDto.getPrecio())
                .formaPago(reservaDto.getFormaPago())
                .build();
        return reservaDao.save(reserva);
    }

    @Transactional
    @Override
    public void deleteReserva(Reserva reserva) {
        reservaDao.delete(reserva);
    }

    @Override
    public boolean existIdReserva(Long id) {
        return reservaDao.existsById(id);
    }
}
