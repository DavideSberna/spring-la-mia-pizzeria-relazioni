package org.java.app.db.pojo;
import java.util.List;

import org.hibernate.validator.constraints.Length;

 
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
 

@Entity
public class Pizza {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(unique = true)
    @Length(min = 1, max = 30, message = "Il nome deve essere tra 1 e 30 caratteri.")
    private String nome;
	
	@Length(min = 1, max = 200, message = "La descrizione non può essere vuota o superare i 200 caratteri.")
    private String descrizione;
	
	@NotBlank(message = "Il campo immagine non può essere vuoto.")
    private String image;
	
	@DecimalMin(value = "0.01", message = "Il prezzo deve essere maggiore di 0.")
    private double prezzo;
	
	@Min(value = 0, message = "Il voto non può essere inferiore a 0.")
    @Max(value = 10, message = "Il voto non può essere superiore a 10.")
    private int voto;
	
	private boolean allergeni;
	
	@OneToMany(mappedBy = "pizza")
	private List<Discount> specialOffers;
	

	@ManyToMany(mappedBy = "pizze")
	private List<Category> categorie; 

	
	
	 
	public Pizza() {}
	public Pizza(String nome, String descrizione, String image, double prezzo, int voto, boolean allergeni) {
		setNome(nome);
		setDescrizione(descrizione);
		setImage(image);
		setPrezzo(prezzo);
		setVoto(voto);
		setAllergeni(allergeni);
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		
		this.nome = nome;
	}
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		
		this.descrizione = descrizione;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		
		this.image = image;
	}
	public double getPrezzo() {
		return prezzo;
	}
	public void setPrezzo(double prezzo) {
		
		this.prezzo = prezzo;
	}
	
	public int getVoto() {
		return voto;
	}
	public void setVoto(int voto) {
		
		this.voto = voto;
	}
	public boolean isAllergeni() {
		return allergeni;
	}
	public void setAllergeni(boolean allergeni) {
		this.allergeni = allergeni;
	}
	
	public List<Discount> getSpecialOffers() {
		return specialOffers;
	}
	public void setSpecialOffers(List<Discount> specialOffers) {
		this.specialOffers = specialOffers;
	}
	
	public List<Category> getCategorie() {
		return categorie;
	}
	public void setCategorie(List<Category> categorie) {
		this.categorie = categorie;
	}
	
	
	@Override
	public String toString() {
		
		return "[" + getId() + "] Pizza:\n" +
				"nome: " + getNome() + "\n"+ 
				"descrizione: " + getDescrizione() + "\n"+
				"prezzo: " + getPrezzo() + "\n";
		
	}
	 

}
