package com.kevin.apihotel.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservaDto {
    private Long idReserva;
    private Date fechaEntrada;
    private Date fechaSalida;
    private double precio;
    private String formaPago;
}
