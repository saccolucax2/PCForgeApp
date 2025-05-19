package it.unisannio.pcforgeapp.controller;

import it.unisannio.pcforgeapp.model.Build;
import it.unisannio.pcforgeapp.service.BuildGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/builds")
public class BuildGeneratorController {

    @Autowired
    private BuildGeneratorService generatorService;

    /**
     * Genera una build a partire da un budget totale.
     * Esempio di chiamata: GET /api/builds/generate?budget=1500
     */
    @GetMapping("/generate")
    public ResponseEntity<Build> generateBuildByBudget(@RequestParam double budget) {
        Build build = generatorService.generateBuildByBudget(budget);
        return ResponseEntity.ok(build);
    }

    /**
     * Genera una build includendo un componente obbligatorio.
     * Esempio: GET /api/builds/generate-with-component?componentId=5&budget=2000
     */
    @GetMapping("/generate-with-component")
    public ResponseEntity<Build> generateBuildByComponent(
            @RequestParam Long componentId,
            @RequestParam double budget
    ) {
        Build build = generatorService.generateBuildByComponent(componentId, budget);
        return ResponseEntity.ok(build);
    }
}