package org.java.app.db.serv;

import java.util.List;

import org.java.app.db.pojo.Discount;
import org.java.app.db.repo.DiscountRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DiscountService {
	
	@Autowired
	private DiscountRepo discountRepo;
	
	
	public void save(Discount discount) {
		discountRepo.save(discount);
	}
	
	public List<Discount> findAll(){
		return discountRepo.findAll();
	}
	
	public Discount findById(int id) {
		return discountRepo.findById(id).get();
	}
	
	public List<Discount> deleteById(int id) {
		return discountRepo.deleteById(id);
	}

}
