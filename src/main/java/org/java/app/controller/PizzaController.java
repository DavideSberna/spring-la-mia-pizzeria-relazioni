package org.java.app.controller;


import java.util.List;

import org.java.app.db.pojo.Discount;
import org.java.app.db.pojo.Pizza;
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
	
	
	
	@GetMapping("/pizza/{id}/new-offer")
	public String createNewOffer(@PathVariable("id") Integer pizzaId, Model model) {
	    Pizza pizza = pizzaService.findById(pizzaId);
	    model.addAttribute("pizza", pizza);
	    model.addAttribute("discount", new Discount());
	    return "nuova-offerta";
	}
	
	@PostMapping("/save-offer")
	public String saveOffer(@RequestParam("pizzaId") Integer pizzaId, @ModelAttribute("discount") Discount discount) {
	    Pizza pizza = pizzaService.findById(pizzaId);
	    discount.setPizza(pizza);
	    discountService.save(discount);
	    return "redirect:/pizza/" + pizzaId;
	}
	
	
	

}
