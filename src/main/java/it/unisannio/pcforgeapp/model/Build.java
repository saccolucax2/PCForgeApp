package it.unisannio.pcforgeapp.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Entity
@Table(name = "builds")
@Getter
@Setter
@NoArgsConstructor
public class Build {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name; // opzione: un nome scelto dall’utente
    private double totalPrice;

    @ManyToMany
    @JoinTable(
            name = "build_components",
            joinColumns = @JoinColumn(name = "build_id"),
            inverseJoinColumns = @JoinColumn(name = "component_id")
    )
    private List<Component> components;
}