package com.example.demo.DTO;

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
@Table(name="Drink")
public class DrinkDTO {
	@JsonProperty("id")
    @NonNull
    @Id
    @Column(name ="id")
    private int id;
	
    @JsonProperty("name")
    @NonNull
    @Column(name ="name")
    private String name;

    @JsonProperty("brand")
    @NonNull
    @Column(name ="brand")
    private String brand;
    
    @JsonProperty("price")
    @NonNull
    @Column(name ="price")
    private String price;
}


