package com.jmr.stream.demostream.model.entity;

import com.jmr.stream.demostream.model.validator.Dni;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class UserEntity {

    /**
     * Entity dni
     * specific validator implementation
     */
    @Id
    @Dni
    private String dni;
    /**
     * Entity name
     */
    @NotBlank
    private String name;
}
