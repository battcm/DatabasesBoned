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
@Table(name="Ingrediant")
public class IngredDTOCOM {
    @JsonProperty("id")
    @NonNull
    @Id
    @Column(name ="IngredientID")
    private int id;

    @JsonProperty("name")
    @NonNull
    @Column(name ="IngreName")
    private String name;

    @JsonProperty("type")
    @NonNull
    @Column(name ="IngreType")
    private String type;}

