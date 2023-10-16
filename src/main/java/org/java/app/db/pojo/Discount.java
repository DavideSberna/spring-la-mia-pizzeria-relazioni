package org.java.app.db.pojo;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
 

@Entity
public class Discount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    
    @Column(nullable = false)
    private LocalDate dataInizio;
    
    @Column(nullable = false)
    private LocalDate dataFine;
    
    @Column(nullable = false)
    private String titolo;
    
    @ManyToOne
    @JoinColumn(name="pizza_id", nullable=false)
    private Pizza pizza;
    
     
	public Discount() {}
    public Discount(Pizza pizza, LocalDate dataInizio, LocalDate dataFine, String titolo ) {
    	setDataInizio(dataInizio);
    	setDataFine(dataFine);
    	setTitolo(titolo);
    	setPizza(pizza);
    }
    
    
    
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public LocalDate getDataInizio() {
		return dataInizio;
	}
	public void setDataInizio(LocalDate dataInizio) {
		this.dataInizio = dataInizio;
	}
	public LocalDate getDataFine() {
		return dataFine;
	}
	public void setDataFine(LocalDate dataFine) {
		this.dataFine = dataFine;
	}
	
	public String getHtmlStartDate() {

		return getDataInizio() == null
				? null
				: getDataInizio().format(DateTimeFormatter.ofPattern("YYYY-MM-dd"));
	}
	
	public void setHtmlStartDate(String date) {

		setDataInizio(LocalDate.parse(date));
	}
	
	
	public String getHtmlEndDate() {

		return getDataFine() == null
				? null
				: getDataFine().format(DateTimeFormatter.ofPattern("YYYY-MM-dd"));
	}
	
	public void setHtmlEndDate(String date) {

		setDataFine(LocalDate.parse(date));
	}
	
	
	public String getTitolo() {
		return titolo;
	}
	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}
	public Pizza getPizza() {
		return pizza;
	}
	public void setPizza(Pizza pizza) {
		this.pizza = pizza;
	}

    
}
