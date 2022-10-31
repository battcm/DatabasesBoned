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
@ToString()
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties()
@JsonInclude(JsonInclude.Include.NON_NULL)
@EqualsAndHashCode
@Builder
@Table()
public class ResDTO {
    @JsonProperty("name")
    @NonNull
    @Id
    private String name;

    @JsonProperty("addr")
    @NonNull
    private String addr;}

