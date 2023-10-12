package org.java.app.db.serv;

import java.util.List;

import org.java.app.db.pojo.Pizza;
import org.java.app.db.repo.PizzaRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PizzaService {
	
	@Autowired
	private PizzaRepo pizzaRepo;
	
	public void save(Pizza pizza) {
		pizzaRepo.save(pizza);
	}
	
	public List<Pizza> findAll(){
		return pizzaRepo.findAll();
	}
	
	public Pizza findById(int id) {
		return pizzaRepo.findById(id).get();
	}
	
	
	public List<Pizza> findByTitle(String nome){
		return pizzaRepo.findByNome(nome);
	}
	
	public List<Pizza> findByNomeContaining(String nome, int voto, boolean allergeni){
		return pizzaRepo.searchPizze(nome, voto, allergeni);
	}
	
	public List<Pizza> findByIdNotLike(int id){
		return pizzaRepo.findByIdNotLike(id);
	}
	
	public List<Pizza> deleteById(int id){
		return pizzaRepo.deleteById(id);
	}

}
