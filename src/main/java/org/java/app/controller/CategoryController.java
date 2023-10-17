package org.java.app.controller;

import java.util.ArrayList;
import java.util.List;

import org.java.app.db.pojo.Category;
import org.java.app.db.pojo.Pizza;
import org.java.app.db.serv.CategoryService;
import org.java.app.db.serv.PizzaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.validation.Valid;

@Controller
public class CategoryController {

	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private PizzaService pizzaService;
	
	@GetMapping("/pizza/category")
	public String getCategory(
			Model model
			) {
		
		List<Category> categories = categoryService.findAll();
		model.addAttribute("categories", categories);
		
		return "category";
	}
	
	@GetMapping("/pizza/{c_id}/new-category")
	public String createPizzaCategory(
			@PathVariable("c_id") int id,
			Model model
			) {
		Pizza pizza = pizzaService.findById(id);
		
		
		List<Pizza> pizzas = null;
		model.addAttribute("pizzas", pizzas);
		model.addAttribute("pizza", pizza);
		model.addAttribute("category", new Category());
		
		
		return "nuova-categoria";
	}
	
	@PostMapping("/pizza/{c_id}/new-category")
	public String saveCategory(
	        @Valid @ModelAttribute Category category,
	        BindingResult bindingResult,
	        @PathVariable("c_id") int id,
	        Model model
	) {
	    if (bindingResult.hasErrors()) {
	        
	        return "errore";
	    }
	    
	    List<Pizza> selectedPizzas = new ArrayList<>();
	    Pizza pizza = pizzaService.findById(id);
	    selectedPizzas.add(pizza);
	    category.setPizze(selectedPizzas);
	    categoryService.save(category);
	    
	    return "redirect:/pizza/" + pizza.getId();
	}
	
	
	
	
	@GetMapping("/pizza/new-category")
	public String createNewCategory(
			Model model
			) {
		
		Pizza pizza = new Pizza();
		List<Pizza> pizzas = pizzaService.findAll();
		 
		
		model.addAttribute("pizzas", pizzas);
		model.addAttribute("pizza", pizza);
		model.addAttribute("category", new Category());
		
		
		return "nuova-categoria";
	}
	
	@PostMapping("/pizza/new-category")
	public String saveNewCategory(
			@Valid @ModelAttribute Category category,
			BindingResult bindingResult,
			@RequestParam("pizzaIds") List<Integer> pizzaIds,
			Model model
			) {
		
		
		List<Pizza> selectedPizzas = new ArrayList<>();
		
		for(Integer id : pizzaIds) {
			
			Pizza pizza = pizzaService.findById(id);
			
			if(pizza != null) {
				selectedPizzas.add(pizza);
			}
		}
		
		category.setPizze(selectedPizzas);
	    categoryService.save(category);
		
		return "redirect:/";
	}
	
	
	
	@GetMapping("/pizza/edit/new-category/{ct_id}")
	public String editNewCategory(
			@PathVariable("ct_id") int id,
			Model model
			) {
		
		Category category = categoryService.findById(id);
		List<Pizza> pizzas = pizzaService.findAll();
		
		model.addAttribute("category", category);
		model.addAttribute("pizzas", pizzas);
		
		return "modifica-categoria";
	}
	
	
	
	@PostMapping("/pizza/edit/new-category/{ct_id}")
	public String updateCategory(
			@Valid @ModelAttribute Category category,
			BindingResult bindingResult,
			@PathVariable("ct_id") int id,
			Model model
			) {
		
			if (bindingResult.hasErrors()) {
		        return "modifica-categoria";
		    }
			
			Category lastCategory = categoryService.findById(id);
			lastCategory.setNome(category.getNome());
			
			categoryService.save(lastCategory);
		
		
		 
		
		return "redirect:/";
	}
	
	@PostMapping("/pizza/delete/category/{ct_id}")
	public String deleteCategory(@PathVariable("ct_id") Integer id) {
		
		categoryService.deleteById(id);
		
		return "redirect:/";
	}
	
}
