package it.unisannio.pcforgeapp.model;

import jakarta.persistence.*;  // se usi Jakarta; altrimenti javax.persistence
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

    @Entity
    @Table(name = "components")
    @Getter
    @Setter
    @NoArgsConstructor
    public class Component {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        private String name;           // es. "Intel i5-12400F"
        private String category;       // es. "CPU", "GPU", "RAM", "Motherboard", "PSU", "Case"
        private double price;          // prezzo in euro
        private int performanceScore;  // un punteggio numerico indicativo delle prestazioni

        // costruttore di convenienza
        public Component(String name, String category, double price, int performanceScore) {
            this.name = name;
            this.category = category;
            this.price = price;
            this.performanceScore = performanceScore;
        }
    }