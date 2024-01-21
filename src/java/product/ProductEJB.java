/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatelessEjbClass.java to edit this template
 */
package product;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import java.util.List;


/**
 *
 * @author anil
 */
//Product EJB class
@Stateless
public class ProductEJB {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    //Attributes             
    @PersistenceContext(unitName = "ebusinessPU")
    private EntityManager em;

    //list product          
    public List<Product> findProducts() {
        Query query = em.createNamedQuery("findAllProducts");
        return query.getResultList();
    }
    
    //list games
    public List<Game> findGames(){
        Query query = em.createNamedQuery("findAllGames");
        return query.getResultList();
    }
    
    //list movies
    public List<Movie> findMovies(){
        Query query = em.createNamedQuery("findAllMovies");
        return query.getResultList();
    }

    //create game
    public Game createGame(Game game) {
        em.persist(game);
        return game;
    }

    //create movie
    public Movie createMovie(Movie movie) {
        em.persist(movie);
        return movie;
    }
    
    //search games and return games
    public List<Game>searchGame(Game game){
        Query  query = em.createNamedQuery("Game.findByTitle").setParameter("title", game.getTitle());
        return query.getResultList();
    }
    
    //search movies and return movies
    public List<Movie>searchMovie(Movie movie){
        Query  query = em.createNamedQuery("Movie.findByTitle").setParameter("title", movie.getTitle());
        return query.getResultList();
    }
}
