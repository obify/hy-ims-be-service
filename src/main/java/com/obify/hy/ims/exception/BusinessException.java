package com.obify.hy.ims.exception;

import com.obify.hy.ims.dto.ErrorDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class BusinessException extends RuntimeException {

    private List<ErrorDTO> errors;

    public BusinessException(List<ErrorDTO> errors){
        this.errors = errors;
    }

}
