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
@Table(name="Orders")
public class OrderIdDTO {
    @JsonProperty("restId")
    @NonNull
    @Id
    private int restId;
    @JsonProperty("foodId")
    @NonNull
    private int foodId;
    @JsonProperty("drinkId")
    @NonNull
    private int drinkId;
}