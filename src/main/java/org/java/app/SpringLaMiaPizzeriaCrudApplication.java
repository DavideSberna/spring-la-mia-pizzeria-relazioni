package org.java.app;

import java.time.LocalDate;

import org.java.app.db.pojo.Discount;
import org.java.app.db.pojo.Pizza;
import org.java.app.db.serv.DiscountService;
import org.java.app.db.serv.PizzaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringLaMiaPizzeriaCrudApplication implements CommandLineRunner {
	
	
	@Autowired
	private PizzaService pizzaService;
	
	@Autowired
	private DiscountService discountService;

	public static void main(String[] args) {
		SpringApplication.run(SpringLaMiaPizzeriaCrudApplication.class, args);
	}
	
	@Override
	public void run(String... args) throws Exception {
		
		Pizza pizza1 = new Pizza("Margherita", "Pizza leggera, semplice e facile da digerire", "https://media-assets.vanityfair.it/photos/61e444841e21bc3bd54b5357/1:1/w_2832,h_2832,c_limit/pizza%20tendenze.jpg", 20.00, 7, true);
		Pizza pizza2 = new Pizza("Mangia e taci", "Pizza leggera, semplice e facile da digerire", "https://assets.website-files.com/615643f927c118dd46b32c80/61dff3812029d02ee8eb5ced_Best%20Pizza%20in%20Clifton%20Hhill.jpg", 30.20, 8, false);
		Pizza pizza3 = new Pizza("Bufalina", "Pizza leggera, semplice e facile da digerire", "https://www.guardini.com/images/guardinispa/ricette/full/pizza_basilico.jpg", 22.90, 9, true);
		Pizza pizza4 = new Pizza("Bella Brescia", "Pizza leggera, semplice e facile da digerire", "https://i2.wp.com/www.piccolericette.net/piccolericette/wp-content/uploads/2016/12/3018_Pizza.jpg?resize=895%2C616&ssl=1", 32.60, 6, false);
		
		pizzaService.save(pizza1);
		pizzaService.save(pizza2);
		pizzaService.save(pizza3);
		pizzaService.save(pizza4);
		
		Discount disc1 = new Discount(pizza1, LocalDate.of(2020, 5, 5), LocalDate.of(2020, 5, 5), "sconto1");
		Discount disc2 = new Discount(pizza2, LocalDate.of(2020, 5, 5), LocalDate.of(2020, 5, 5), "sconto2");
		Discount disc3 = new Discount(pizza3, LocalDate.of(2020, 5, 5), LocalDate.of(2020, 5, 5), "sconto3");
		
		
		 
		
		discountService.save(disc1);
		discountService.save(disc2);
		discountService.save(disc3);
	 
		
	}

}
