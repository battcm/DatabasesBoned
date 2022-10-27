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
@Table(name="Sells")
public class SellsDTO {
    @JsonProperty("resId")
    @NonNull
    @Id
    @Column(name ="resId")
    private int resId;

	@JsonProperty("foodId")
    @NonNull
    @Id
    @Column(name ="foodId")
    private int foodId;

    @JsonProperty("ingreId")
    @NonNull
    @Id
    @Column(name ="ingreId")
    private int ingreId;

    @JsonProperty("mealType")
    @NonNull
    @Id
    @Column(name ="mealType")
    private String mealType;
}


