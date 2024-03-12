package com.kevin.apihotel.model.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MensajeResponse implements Serializable {
    private String mensaje;
    private Object object;
}
