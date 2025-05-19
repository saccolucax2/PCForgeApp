package it.unisannio.pcforgeapp;

import it.unisannio.pcforgeapp.model.Component;
import it.unisannio.pcforgeapp.repository.ComponentRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class PcForgeApplication {

    public static void main(String[] args) {
        SpringApplication.run(PcForgeApplication.class, args);
    }

    @Bean
    public CommandLineRunner dataLoader(ComponentRepository repo) {
        return args -> {
            // Evita di reinserire i dati se la tabella non è vuota
            if (repo.count() == 0) {
                // CPU
                repo.save(new Component("Intel i5-12400F", "CPU", 180.0, 80));
                repo.save(new Component("AMD Ryzen 5 5600X", "CPU", 200.0, 85));

                // GPU
                repo.save(new Component("NVIDIA RTX 3060", "GPU", 350.0, 90));
                repo.save(new Component("AMD RX 6600 XT", "GPU", 330.0, 88));

                // RAM
                repo.save(new Component("Corsair Vengeance 16GB DDR4", "RAM", 75.0, 70));
                repo.save(new Component("G.Skill Ripjaws 16GB DDR4", "RAM", 70.0, 68));

                // Motherboard
                repo.save(new Component("MSI B560M PRO-VDH", "Motherboard", 100.0, 60));
                repo.save(new Component("ASUS TUF Gaming X570", "Motherboard", 180.0, 80));

                // PSU
                repo.save(new Component("Corsair CV550 550W", "PSU", 60.0, 50));
                repo.save(new Component("EVGA 600 BR 600W", "PSU", 70.0, 55));

                // Case
                repo.save(new Component("Cooler Master MasterBox Q300L", "Case", 50.0, 40));
                repo.save(new Component("NZXT H510", "Case", 80.0, 50));
            }
        };
    }
}