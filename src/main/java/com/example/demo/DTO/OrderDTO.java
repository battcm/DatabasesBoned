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
@Table(name="Schedule")
public class OrderDTO {
    @JsonProperty("resid")
    @NonNull
    @Id
    @Column(name ="id")
    private int resId;
    @JsonProperty("foodid")
    @NonNull
    @Column(name ="day")
    private int foodId;
    @JsonProperty("drinkid")
    @NonNull
    @Column(name ="open")
    private int drinkId;
    @JsonProperty("quan")
    @NonNull
    @Column(name ="close")
    private int quan;
    @JsonProperty("stor")
    @NonNull
    @Column(name ="close")
    private String stor;
    @JsonProperty("day")
    @NonNull
    @Column(name ="close")
    private Date day;

}
