package org.java.app.controller;

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

import jakarta.validation.Valid;

@Controller
public class DiscountController {

	@Autowired
	private DiscountService discountService;
	
	@Autowired
	private PizzaService pizzaService;
	
	
	@GetMapping("/pizza/{of_id}/new-offer")
	public String createNewOffer(@PathVariable("of_id") int id, Model model) {
	    
		Pizza pizza = pizzaService.findById(id);
		
	    model.addAttribute("pizza", pizza);
	    model.addAttribute("discount", new Discount());
	    
	    return "nuova-offerta";
	}
	
	@PostMapping("/pizza/{of_id}/new-offer")
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
	
	@PostMapping("/pizza/delete/offer/{of_id}")
	public String deleteOffer(@PathVariable("of_id") int id) {
		
		discountService.deleteById(id);
		
		
		return "redirect:/";
	}
}
