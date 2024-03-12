package com.kevin.apihotel.services;

import com.kevin.apihotel.model.dto.HuespedDto;
import com.kevin.apihotel.model.entity.Huesped;

import java.util.List;

public interface IHuespedService {
    List<Huesped> getAllHuesped();
    Huesped getByIdHuesped(Long id);
    Huesped saveHuesped(HuespedDto huespedDto);
    void deleteHuesped(Huesped huesped);
    boolean existIdHuesped(Long id);
}
