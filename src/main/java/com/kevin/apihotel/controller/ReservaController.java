package com.kevin.apihotel.controller;

import com.kevin.apihotel.model.entity.Reserva;
import com.kevin.apihotel.model.payload.MensajeResponse;
import com.kevin.apihotel.services.IReservaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class ReservaController {
    @Autowired
    private IReservaService reservaService;

    @GetMapping("/reservas")
    public ResponseEntity<?> getAllReservas(){
        try {
            List<Reserva> listReservas = reservaService.getAllReserva();
            if(listReservas==null){
                return new ResponseEntity<>(
                        MensajeResponse.builder()
                                .mensaje("No existe registro de reservas")
                                .object(null)
                                .build()
                        , HttpStatus.NOT_FOUND
                );
            }
            return new ResponseEntity<>(
                    MensajeResponse.builder()
                            .mensaje("Reservas: ")
                            .object(listReservas)
                            .build()
                    , HttpStatus.OK
            );
        }catch (DataAccessException dataAccessException){
            return new ResponseEntity<>(
                    MensajeResponse.builder()
                            .mensaje(dataAccessException.getMessage())
                            .object(null)
                            .build()
                    , HttpStatus.METHOD_NOT_ALLOWED
            );
        }
    }
}
