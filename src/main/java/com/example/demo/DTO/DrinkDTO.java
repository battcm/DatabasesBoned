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
    @JsonProperty("name")
    @NonNull
    @Id
    @Column(name ="name")
    private String name;

    @JsonProperty("brand")
    @NonNull
    @Column(name ="brand")
    private String brand;
    @JsonProperty("price")
    @NonNull
    @Column(name ="price")
    private float price;
}


