package com.jmr.stream.demostream.model.entity;

import com.jmr.stream.demostream.model.validator.Dni;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class UserEntity {

    /**
     * Entity dni
     * specific validator implementation
     * dni is primary key = index will be created = search by this field = better performance
     */
    @Id
    @Dni
    @Schema(description = "DNI", example = "09530164E", required = true)
    private String dni;
    /**
     * Entity name
     */
    @NotBlank
    @Schema(description = "Name of the User.", example = "Paco Palotes", required = true)
    @Size(max = 100)
    private String name;
}
