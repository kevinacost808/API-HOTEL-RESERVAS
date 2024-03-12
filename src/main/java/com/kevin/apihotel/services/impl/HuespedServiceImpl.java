package com.kevin.apihotel.services.impl;

import com.kevin.apihotel.model.dao.HuespedDao;
import com.kevin.apihotel.model.dto.HuespedDto;
import com.kevin.apihotel.model.entity.Huesped;
import com.kevin.apihotel.services.IHuespedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class HuespedServiceImpl implements IHuespedService {

    @Autowired
    private HuespedDao huespedDao;

    @Transactional(readOnly = true)
    @Override
    public List<Huesped> getAllHuesped() {
        return (List<Huesped>) huespedDao.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Huesped getByIdHuesped(Long id) {
        return huespedDao.findById(id).orElse(null);
    }

    @Transactional()
    @Override
    public Huesped saveHuesped(HuespedDto huespedDto) {
        Huesped huesped = Huesped.builder()
                .idHuesped(huespedDto.getIdHuesped())
                .nombre(huespedDto.getNombre())
                .apellido(huespedDto.getApellido())
                .fechaNacimiento(huespedDto.getFechaNacimiento())
                .nacionalidad(huespedDto.getNacionalidad())
                .telefono(huespedDto.getTelefono())
                .reservas((List)huespedDto.getReservas())
                .build();
        return huespedDao.save(huesped);
    }

    @Transactional()
    @Override
    public void deleteHuesped(Huesped huesped) {
        huespedDao.delete(huesped);
    }

    @Override
    public boolean existIdHuesped(Long id) {
        return huespedDao.existsById(id);
    }
}
