package com.example.demo.DTOHAV;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@ToString(doNotUseGetters = true)
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@EqualsAndHashCode
@Builder
@Table(name="CanStoreWith")
public class CanStoreWithDTOCOM {
	@JsonProperty("ingreAId")
    @NonNull
    @Id
    @Column(name ="ingreAId")
    private int ingreAId;

    @JsonProperty("ingreBId")
    @NonNull
    @Id
    @Column(name ="ingreBId")
    private int ingreBId;
}


