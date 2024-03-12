package com.kevin.apihotel.controller;

import com.kevin.apihotel.model.dto.HuespedDto;
import com.kevin.apihotel.model.dto.ReservaDto;
import com.kevin.apihotel.model.entity.Huesped;
import com.kevin.apihotel.model.payload.MensajeResponse;
import com.kevin.apihotel.services.IHuespedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class HuespedController {
    @Autowired
    private IHuespedService huespedService;

    @GetMapping("/huespedes")
    public ResponseEntity<?> getAllHuespedes(){
        try {
            List<Huesped> listHuespedes = huespedService.getAllHuesped();
            if(listHuespedes == null){
                return new ResponseEntity<>(
                        MensajeResponse.builder()
                                .mensaje("Lista nula")
                                .object(null)
                                .build()
                        , HttpStatus.NOT_FOUND
                );
            }
            return new ResponseEntity<>(
                    MensajeResponse.builder()
                            .mensaje("Huespedes:")
                            .object(listHuespedes)
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

    @PostMapping("/huesped")
    public ResponseEntity<?> saveHuesped(@RequestBody HuespedDto huespedDto){
        try {
            Huesped huespedSave = huespedService.saveHuesped(huespedDto);
            huespedDto = HuespedDto.builder()
                    .idHuesped(huespedSave.getIdHuesped())
                    .nombre(huespedSave.getNombre())
                    .apellido(huespedSave.getApellido())
                    .fechaNacimiento(huespedSave.getFechaNacimiento())
                    .nacionalidad(huespedSave.getNacionalidad())
                    .telefono(huespedSave.getTelefono())
                    .reservas((List) huespedSave.getReservas())
                    .build();
            return new ResponseEntity<>(
                    MensajeResponse.builder()
                            .mensaje("huesped guardado")
                            .object(huespedDto)
                            .build()
                    , HttpStatus.CREATED
            );
        }catch (DataAccessException dataAccessException){
            return new ResponseEntity<>(
                    MensajeResponse.builder()
                            .mensaje(dataAccessException.getMessage())
                            .object(null)
                            .build()
                    , HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }
}
