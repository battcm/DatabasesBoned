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
@Table(name="WorksFor")
public class WorksForDTOCOM {
    @JsonProperty("username")
    @NonNull
    @Id
    @Column(name ="username")
    private String username;

	@JsonProperty("resId")
    @NonNull
    @Id
    @Column(name ="resId")
    private int resId;

    @JsonProperty("permission")
    @NonNull
    @Column(name ="permission")
    private String permission;
}


