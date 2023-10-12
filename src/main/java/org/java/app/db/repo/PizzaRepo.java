package org.java.app.db.repo;

import java.util.List;

import org.java.app.db.pojo.Pizza;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PizzaRepo extends JpaRepository<Pizza, Integer> {
	public List<Pizza> findByNome(String nome);
	
	@Query("SELECT p FROM Pizza p WHERE (:nome IS NULL OR p.nome LIKE %:nome%) AND (:voto IS NULL OR p.voto >= :voto) AND (:allergeni IS NULL OR p.allergeni = :allergeni)")
    public List<Pizza> searchPizze(@Param("nome") String nome, @Param("voto") int voto, @Param("allergeni") boolean allergeni);
	
	public List<Pizza> findByIdNotLike(int id);
	
	public List<Pizza> deleteById(int id);

}
