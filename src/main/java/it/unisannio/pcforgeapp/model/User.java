package it.unisannio.pcforgeapp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;

    private String password;

    @Column(unique = true)
    private String email;

    private String nome;
    private String cognome;

    @Column(name = "data_registrazione")
    private LocalDateTime dataRegistrazione;

    @PrePersist
    protected void onCreation() {
        dataRegistrazione = LocalDateTime.now();
    }
}