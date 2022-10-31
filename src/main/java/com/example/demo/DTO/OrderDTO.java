package com.example.demo.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Date;
import java.sql.Time;

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
public class OrderDTO {
    @JsonProperty("resid")
    @NonNull
    @Id
    private int resId;
    @JsonProperty("foodid")
    @NonNull
    private int foodId;
    @JsonProperty("drinkid")
    @NonNull
    private int drinkId;
    @JsonProperty("quan")
    private int quan;
    @JsonProperty("stor")
    private String stor;
    @JsonProperty("day")
    private Date day;

}
