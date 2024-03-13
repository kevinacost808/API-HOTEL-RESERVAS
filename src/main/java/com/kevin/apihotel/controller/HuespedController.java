package com.kevin.apihotel.controller;

import com.kevin.apihotel.model.dto.HuespedDto;
import com.kevin.apihotel.model.dto.ReservaDto;
import com.kevin.apihotel.model.entity.Huesped;
import com.kevin.apihotel.model.entity.Reserva;
import com.kevin.apihotel.model.payload.MensajeResponse;
import com.kevin.apihotel.services.IHuespedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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

    @GetMapping("/huesped/{id}")
    public ResponseEntity<?> getHuespedById(@PathVariable Long id){
        try{
            if(huespedService.existIdHuesped(id)){
                Huesped huesped = huespedService.getByIdHuesped(id);

                List<ReservaDto> reservaDtos = new ArrayList<>();
                for(Reserva reserva: huesped.getReservas()){
                    ReservaDto reservaDto = ReservaDto.builder()
                            .idReserva(reserva.getIdReserva())
                            .precio(reserva.getPrecio())
                            .formaPago(reserva.getFormaPago())
                            .fechaSalida(reserva.getFechaSalida())
                            .fechaEntrada(reserva.getFechaEntrada())
                            .build();
                    reservaDtos.add(reservaDto);
                }

                HuespedDto huespedDto = HuespedDto.builder()
                        .idHuesped(huesped.getIdHuesped())
                        .nombre(huesped.getNombre())
                        .apellido(huesped.getApellido())
                        .nacionalidad(huesped.getNacionalidad())
                        .telefono(huesped.getTelefono())
                        .fechaNacimiento(huesped.getFechaNacimiento())
                        .reservas(reservaDtos)
                        .build();
                
                return new ResponseEntity<>(
                        MensajeResponse.builder()
                                .mensaje("Huesped "+id)
                                .object(huespedDto)
                                .build()
                        , HttpStatus.OK
                );
            }else{
                return new ResponseEntity<>(
                        MensajeResponse.builder()
                                .mensaje("No existe el huesped con id "+id)
                                .object(null)
                                .build()
                        , HttpStatus.NOT_FOUND
                );
            }
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

    @PostMapping("/huesped")
    public ResponseEntity<?> saveHuesped(@RequestBody HuespedDto huespedDto){
        try {

            Huesped huespedSave = huespedService.saveHuesped(huespedDto);

            // Convertir las reservas de la entidad a DTOs
            List<ReservaDto> reservaDtos = new ArrayList<>();
            for (Reserva reserva : huespedSave.getReservas()) {
                ReservaDto reservaDto = ReservaDto.builder()
                        .idReserva(reserva.getIdReserva())
                        .fechaEntrada(reserva.getFechaEntrada())
                        .fechaSalida(reserva.getFechaSalida())
                        .precio(reserva.getPrecio())
                        .formaPago(reserva.getFormaPago())
                        .build();
                reservaDtos.add(reservaDto);
            }

            huespedDto = HuespedDto.builder()
                    .idHuesped(huespedSave.getIdHuesped())
                    .nombre(huespedSave.getNombre())
                    .apellido(huespedSave.getApellido())
                    .fechaNacimiento(huespedSave.getFechaNacimiento())
                    .nacionalidad(huespedSave.getNacionalidad())
                    .telefono(huespedSave.getTelefono())
                    .reservas(reservaDtos)
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

    @PutMapping("/huesped/{id}")
    public ResponseEntity<?> updateHuesped(@PathVariable Long id, @RequestBody HuespedDto huespedDto){
        try{
            if(huespedService.existIdHuesped(id)){
                if(!huespedDto.getIdHuesped().equals(id)){
                    return new ResponseEntity<>(
                            MensajeResponse.builder()
                                    .mensaje("No coinciden los registros")
                                    .object(null)
                                    .build()
                            , HttpStatus.METHOD_NOT_ALLOWED
                    );
                }

                Huesped huespedActualizar = huespedService.saveHuesped(huespedDto);

                // Convertir las reservas de la entidad a DTOs
                List<ReservaDto> reservaDtos = new ArrayList<>();
                for (Reserva reserva : huespedActualizar.getReservas()) {
                    ReservaDto reservaDto = ReservaDto.builder()
                            .idReserva(reserva.getIdReserva())
                            .fechaEntrada(reserva.getFechaEntrada())
                            .fechaSalida(reserva.getFechaSalida())
                            .precio(reserva.getPrecio())
                            .formaPago(reserva.getFormaPago())
                            .build();
                    reservaDtos.add(reservaDto);
                }

                huespedDto = HuespedDto.builder()
                        .idHuesped(huespedActualizar.getIdHuesped())
                        .nombre(huespedActualizar.getNombre())
                        .apellido(huespedActualizar.getApellido())
                        .fechaNacimiento(huespedActualizar.getFechaNacimiento())
                        .nacionalidad(huespedActualizar.getNacionalidad())
                        .telefono(huespedActualizar.getTelefono())
                        .reservas(reservaDtos)
                        .build();

                return new ResponseEntity<>(
                        MensajeResponse.builder()
                                .mensaje("Actualizado exitosamente")
                                .object(huespedDto)
                                .build()
                        , HttpStatus.CREATED
                );
            }else{
                return new ResponseEntity<>(
                        MensajeResponse.builder()
                                .mensaje("No existe registro a actualizar")
                                .object(null)
                                .build()
                        , HttpStatus.INTERNAL_SERVER_ERROR
                );
            }
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

    @DeleteMapping("/huesped/{id}")
    public ResponseEntity<?> deleteHuesped(@PathVariable Long id){
        try{
            Huesped huespedDelete = huespedService.getByIdHuesped(id);
            huespedService.deleteHuesped(huespedDelete);
            return new ResponseEntity<>(
                    MensajeResponse.builder()
                        .mensaje("Huesped eliminado correctamente")
                        .object(null)
                        .build()
                    , HttpStatus.OK);
        }catch (DataAccessException dataAccessException){
            return new ResponseEntity<>(dataAccessException.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
