/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package product;

import jakarta.ejb.EJB;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.bean.ManagedBean;
import jakarta.faces.bean.RequestScoped;
import jakarta.faces.context.FacesContext;
import java.util.ArrayList;
import java.util.List;
import order.Orders;

/**
 *
 * @author rajes
 */
@ManagedBean
@RequestScoped

//Product controller class which is responsible for interacting with EJB class and JSF
public class ProductController {

    /**
     * @param game the game to set
     */
    public void setGame(Game game) {
        this.game = game;
    }

    /**
     * @param movie the movie to set
     */
    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    /**
     * @param product the product to set
     */
    public void setProduct(Product product) {
        this.product = product;
    }

    /**
     * @param productList the productList to set
     */
    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

    /**
     * Creates a new instance of ProductController
     */
    //Attributes             
    @EJB
    private ProductEJB productEJB;//create instance of productaaEJB

    private Game game = new Game();//create instance of Game
    private Movie movie = new Movie();//Create instance of Movie
    private Product product = new Product();//Create instance of Product
    private List<Product> productList = new ArrayList<Product>();//create product list
    private List<Game> gameList = new ArrayList<>();//create game list
    private List<Movie> movieList = new ArrayList<>();//create movie list
    private List<Game> gameSearchList = new ArrayList<>();
    private List<Movie> movieSearchList = new ArrayList<>();

    public ProductController() {
    }

    //create game
    public String doCreateGame() {
        FacesContext ctx = FacesContext.getCurrentInstance();
        try {
            game = productEJB.createGame(game);
            ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Successfully created", " the game: " + game.getTitle()));
        } catch (Exception e) {
            ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Game hasn't been created", e.getMessage()));
        }
        return "listGames.faces";
    }

    //create movie
    public String doCreateMovie() {
        FacesContext ctx = FacesContext.getCurrentInstance();
        try {
            movie = productEJB.createMovie(movie);
            ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Successfully created", " the movie: " + movie.getTitle()));
        } catch (Exception e) {
            ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Movie hasn't been created", e.getMessage()));
        }
        return "listMovies.faces";
    }

    //search movie
    public String searchMovie() {
        movieSearchList = productEJB.searchMovie(movie);
        return "searchMovieResult.faces";
    }

    //search game
    public String searchGame() {
        gameSearchList = productEJB.searchGame(game);
        return "searchGameResult.faces";
    }

    //search item
    public String searchItem(Orders order) {
        String itemType = order.getItem().getDecriminatorValue();//get descrinator value
        if (order.getItem() instanceof Game game) { //check if item is game or not
            gameSearchList = productEJB.searchGame(game);
            return "searchGameResult.faces";
        }
        if (order.getItem() instanceof Movie movie) { //check if item is movie or not
            movieSearchList = productEJB.searchMovie(movie);
            return "searchMovieResult.faces";
        }
        return null;
    }

    //get game
    public String getGame(Game game) {
        gameSearchList = productEJB.searchGame(game);
        return "searchGameResult.faces";
    }

    //get movie
    public String getMovie(Movie movie) {
        movieSearchList = productEJB.searchMovie(movie);
        return "searchMovieResult.faces";
    }
    
    /**
     * @return the game
     */
    public Game getGame() {
        return game;
    }

    /**
     * @return the movie
     */
    public Movie getMovie() {
        return movie;
    }

    /**
     * @return the product
     */
    public Product getProduct() {
        return product;
    }

    /**
     * @return the productList
     */
    public List<Product> getProductList() {
        productList = productEJB.findProducts();
        return productList;
    }

    /**
     * @return the gameList
     */
    public List<Game> getGameList() {
        gameList = productEJB.findGames();
        return gameList;
    }

    /**
     * @param gameList the gameList to set
     */
    public void setGameList(List<Game> gameList) {
        this.gameList = gameList;
    }

    /**
     * @return the movieList
     */
    public List<Movie> getMovieList() {
        movieList = productEJB.findMovies();
        return movieList;
    }

    /**
     * @param movieList the movieList to set
     */
    public void setMovieList(List<Movie> movieList) {
        this.movieList = movieList;
    }

    /**
     * @return the gameSearchList
     */
    public List<Game> getGameSearchList() {
        return gameSearchList;
    }

    /**
     * @param gameSearchList the gameSearchList to set
     */
    public void setGameSearchList(List<Game> gameSearchList) {
        this.gameSearchList = gameSearchList;
    }

    /**
     * @return the movieSearchList
     */
    public List<Movie> getMovieSearchList() {
        return movieSearchList;
    }

    /**
     * @param movieSearchList the movieSearchList to set
     */
    public void setMovieSearchList(List<Movie> movieSearchList) {
        this.movieSearchList = movieSearchList;
    }

}
