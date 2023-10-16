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
	private DiscountService discountService;
	
	@Autowired
	private CategoryService categoryService;
	
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
		model.addAttribute("pizza", new Pizza());
		
		return "create";
	}
	
	@PostMapping("/create")
	public String store(@Valid @ModelAttribute("pizza") Pizza formPizza, BindingResult bindingResult, Model model){
		
		if(bindingResult.hasErrors()) {
			return "create";
		}
		
		pizzaService.save(formPizza);
		
		
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
	
	
	
	
	
	//OFFERTE
	
	@GetMapping("/pizza/new-offer/{of_id}")
	public String createNewOffer(@PathVariable("of_id") int id, Model model) {
	    
		Pizza pizza = pizzaService.findById(id);
		
	    model.addAttribute("pizza", pizza);
	    model.addAttribute("discount", new Discount());
	    
	    return "nuova-offerta";
	}
	
	@PostMapping("/pizza/new-offer/{of_id}")
	public String saveOffer(
			@Valid @ModelAttribute Discount discount,
			BindingResult bindingResult,
			@PathVariable("of_id") int id,
			Model model
			) {
		
	    Pizza pizza = pizzaService.findById(id);
	    discount.setPizza(pizza);
	    discountService.save(discount);
	    
	    return "redirect:/pizza/" + discount.getPizza().getId();
	}
	
	
	@GetMapping("/pizza/edit/new-offer/{of_id}")
	public String editNewOffer(
			@PathVariable("of_id") int id,
			Model model
			) {
		
		
		Discount discount = discountService.findById(id);
		Pizza pizza = discount.getPizza();
		
		model.addAttribute("pizza", pizza);
		model.addAttribute("discount", discount);
		
		
		return "modifica-offerta";
	}
	
	@PostMapping("/pizza/edit/new-offer/{of_id}")
	public String updateOffer(
	        @Valid @ModelAttribute("discount") Discount formDiscount,
	        BindingResult bindingResult,
	        @PathVariable("of_id") int id,
	        Model model) {

	    if (bindingResult.hasErrors()) {
	        return "modifica-offerta";
	    }

	    Discount discount = discountService.findById(id);
	    Pizza pizza = discount.getPizza();
	    
	    formDiscount.setPizza(pizza);
	    discountService.save(formDiscount);

	    
	    return "redirect:/pizza/" + discount.getPizza().getId();
	}
	
	
	
	
	
	//CATEGORIE
	
	
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
	    
	    return "redirect:/";
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
	
	@PostMapping("/pizza/new-category/{ct_id}")
	public String saveNewCategory(
			@Valid @ModelAttribute Category category,
			BindingResult bindingResult,
			@PathVariable("ct_id") List<Integer> ids,
			Model model
			) {
		
		
		List<Pizza> selectedPizzas = new ArrayList<>();
		for(Integer id : ids) {
			
			Pizza pizza = pizzaService.findById(id);
			
			if (pizza != null) {
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
		List<Pizza> pizza = category.getPizze();
		
		model.addAttribute("pizza", pizza);
		model.addAttribute("category", category);
		
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
			List<Pizza> pizza = lastCategory.getPizze();
			
			category.setPizze(pizza);
			categoryService.save(category);
		
		
		 
		
		return "redirect:/";
	}
	
	

}
