package it.unisannio.pcforgeapp.service;

import it.unisannio.pcforgeapp.model.Build;
import it.unisannio.pcforgeapp.model.Component;
import it.unisannio.pcforgeapp.repository.BuildRepository;
import it.unisannio.pcforgeapp.repository.ComponentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;

@Service
public class BuildGeneratorService {

    @Autowired
    private ComponentRepository componentRepository;

    @Autowired
    private BuildRepository buildRepository;

    // Categorie fisse (puoi modificarle o aggiungerne di nuove)
    private static final List<String> CATEGORIES = List.of(
            "CPU", "GPU", "RAM", "Motherboard", "PSU", "Case"
    );

    /**
     * Genera una build ottimizzata data una lista di categorie e un budget totale.
     * Basic strategy: per ogni categoria, prendo il componente più performante
     * che costi entro un sotto-budget calcolato in modo uguale per ogni categoria.
     */
    @Transactional
    public Build generateBuildByBudget(double budget) {
        Build build = new Build();
        build.setName("Build per budget €" + budget);

        // Suddividiamo il budget per categoria in modo uniforme
        int nCategories = CATEGORIES.size();
        double budgetPerCategoria = budget / nCategories;

        List<Component> selected = new ArrayList<>();
        double totalPrice = 0.0;

        for (String category : CATEGORIES) {
            // Prendi tutti i componenti di quella categoria, ordinati per prezzo asc.
            List<Component> candidates = componentRepository.findByCategoryOrderByPriceAsc(category);

            // Trova il componente più performante che costa <= budgetPerCategoria
            Component best = getComponent(candidates, budgetPerCategoria);

            if (best != null) {
                selected.add(best);
                totalPrice += best.getPrice();
            }
        }

        build.setComponents(selected);
        build.setTotalPrice(totalPrice);

        return buildRepository.save(build);
    }

    private static Component getComponent(List<Component> candidates, double budgetPerCategoria) {
        Component best = null;
        for (Component c : candidates) {
            if (c.getPrice() <= budgetPerCategoria) {
                if (best == null || c.getPerformanceScore() > best.getPerformanceScore()) {
                    best = c;
                }
            }
        }
        // Se non troviamo nulla sotto budgetPerCategoria, prendiamo il primo (più economico)
        if (best == null && !candidates.isEmpty()) {
            best = candidates.getFirst();
        }
        return best;
    }

    /**
     * Genera una build includendo un componente obbligatorio.
     * Se, ad esempio, l'utente sceglie una GPU, togliamo il suo prezzo dal budget
     * e distribuiamo il resto fra le altre categorie.
     */
    @Transactional
    public Build generateBuildByComponent(Long componentId, double budget) {
        Optional<Component> mandatoryOpt = componentRepository.findById(componentId);
        if (mandatoryOpt.isEmpty()) {
            throw new IllegalArgumentException("Componente non trovato");
        }
        Component mandatory = mandatoryOpt.get();
        Build build = new Build();
        build.setName("Build con componente " + mandatory.getName());

        double remainingBudget = budget - mandatory.getPrice();
        if (remainingBudget < 0) {
            throw new IllegalArgumentException("Budget insufficiente per questo componente");
        }

        List<String> otherCategories = new ArrayList<>(CATEGORIES);
        otherCategories.remove(mandatory.getCategory());

        int nOther = otherCategories.size();
        double budgetPerCategoria = remainingBudget / nOther;

        List<Component> selected = new ArrayList<>();
        selected.add(mandatory);
        double totalPrice = mandatory.getPrice();

        for (String category : otherCategories) {
            List<Component> candidates = componentRepository.findByCategoryOrderByPriceAsc(category);
            Component best = null;
            for (Component c : candidates) {
                if (c.getPrice() <= budgetPerCategoria) {
                    if (best == null || c.getPerformanceScore() > best.getPerformanceScore()) {
                        best = c;
                    }
                }
            }
            if (best == null && !candidates.isEmpty()) {
                best = candidates.getFirst();
            }
            if (best != null) {
                selected.add(best);
                totalPrice += best.getPrice();
            }
        }

        build.setComponents(selected);
        build.setTotalPrice(totalPrice);

        return buildRepository.save(build);
    }
}
