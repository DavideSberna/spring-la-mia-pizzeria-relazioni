package org.java.app.db.repo;

import java.util.List;

import org.java.app.db.pojo.Discount;
import org.java.app.db.pojo.Pizza;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiscountRepo extends JpaRepository<Discount, Integer> {
	
	public List<Discount> findByTitolo(String titolo);
	
	public List<Discount> deleteById(int id);

}
