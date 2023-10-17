package org.java.app.controller;


import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.text.DateFormatter;

import org.java.app.db.pojo.Category;
import org.java.app.db.pojo.Discount;
import org.java.app.db.pojo.Pizza;
import org.java.app.db.serv.CategoryService;
import org.java.app.db.serv.DiscountService;
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
public class PizzaController {
	
	@Autowired
	private PizzaService pizzaService;
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private DiscountService discountService;

	
	@GetMapping("/")
	public String getIndex(
	        @RequestParam(name = "nome", required = false) String nome,
	        @RequestParam(name = "voto", required = false) Integer voto,
	        @RequestParam(name = "allergeni", required = false) String allergeniStr,
	        Model model) {

	    List<Pizza> pizzas = null;
	    
	    boolean allergeni = false;
	    
	    if (allergeniStr != null && !allergeniStr.isEmpty()) {
	        allergeni = allergeniStr.equals("1");
	    }

	    if (nome == null && voto == null && !allergeni) {
	        pizzas = pizzaService.findAll();
	    } else {
	        if (voto == null) {
	            voto = 0;
	        }
	        pizzas = pizzaService.findByNomeContaining(nome, voto, allergeni);
	    }

	    model.addAttribute("pizzas", pizzas);
	    model.addAttribute("nome", nome);
	    model.addAttribute("voto", voto);
	   
	    

	    return "home";
	}
	
	@GetMapping("/pizza/{id}")
	public String getPizzaId(@PathVariable int id, Model model) {
		
		Pizza pizza = pizzaService.findById(id);
		List<Pizza> pizzas = pizzaService.findByIdNotLike(id);
		
		
		model.addAttribute("pizzas", pizzas);
		model.addAttribute("pizza", pizza);
	
		return "pizza-show";
		
	}
	
	@GetMapping("/create")
	public String create(Model model) {
		
		
		List<Category> categories = categoryService.findAll();
	    List<Discount> discounts = discountService.findAll();

	    model.addAttribute("pizza", new Pizza());
	    model.addAttribute("categories", categories);
	    model.addAttribute("discounts", discounts);
		
		return "create";
	}
	
	
	
	@PostMapping("/create")
	public String store(@Valid @ModelAttribute("pizza") Pizza pizza, BindingResult bindingResult, Model model, @RequestParam(value = "categorieIds", required = false) List<Integer> categorieIds) {
	    if (bindingResult.hasErrors()) {
	        return "create";
	    }

	   
	    if (categorieIds != null && !categorieIds.isEmpty()) {
	        List<Category> selectedCategories = new ArrayList<>();
	        Category category = null;
	        for (Integer id : categorieIds) {
	            category = categoryService.findById(id);
	            if (category != null) {
	                selectedCategories.add(category);
	                category.setPizze(Arrays.asList(pizza));
	            }
	            
	        }
	        
	        pizza.setCategorie(selectedCategories);
	       
	    }

	    
	    pizzaService.save(pizza);
	    
	    
	    
	     
	    return "redirect:/";
	}
	
	@GetMapping("/edit/{id}")
	public String edit(
			@PathVariable("id") Integer id,
			Model model
			) {
		
		model.addAttribute("pizza", pizzaService.findById(id));
		System.out.println(pizzaService.findById(id));
		
		return "edit";
	}
	
	@PostMapping("/edit/{id}")
	public String update(
			@Valid @ModelAttribute("pizza") Pizza formPizza,
			BindingResult bindingResult,
			Model model
			) {
		
		if(bindingResult.hasErrors()) {
			return "/edit";
		}
		
		
		pizzaService.save(formPizza);
		
		return "redirect:/";
		
	}
	
	@PostMapping("/delete/{id}")
	public String delete(@PathVariable("id") Integer id) {
		
		pizzaService.deleteById(id);
		
		return "redirect:/";
	}
	
	
	
	
	 
	
	
	 
	
	

}
