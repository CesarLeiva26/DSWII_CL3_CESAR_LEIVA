package edu.pe.cibertec.DSWII_CL3_CESAR_LEIVA.Model.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder

public class ImagenesResponse {
    private String message;
    public ImagenesResponse(String message) {
        this.message = message;
    }
}
