import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLOutput;
import java.util.*;

public class MovieCollection
{
    private ArrayList<Movie> movies;
    private Scanner scanner;

    public MovieCollection(String fileName)
    {
        importMovieList(fileName);
        scanner = new Scanner(System.in);
    }

    public ArrayList<Movie> getMovies()
    {
        return movies;
    }

    public void menu()
    {
        String menuOption = "";

        System.out.println("Welcome to the movie collection!");
        System.out.println("Total: " + movies.size() + " movies");

        while (!menuOption.equals("q"))
        {
            System.out.println("------------ Main Menu ----------");
            System.out.println("- search (t)itles");
            System.out.println("- search (k)eywords");
            System.out.println("- search (c)ast");
            System.out.println("- see all movies of a (g)enre");
            System.out.println("- list top 50 (r)ated movies");
            System.out.println("- list top 50 (h)igest revenue movies");
            System.out.println("- (q)uit");
            System.out.print("Enter choice: ");
            menuOption = scanner.nextLine();

            if (!menuOption.equals("q"))
            {
                processOption(menuOption);
            }
        }
    }

    private void processOption(String option)
    {
        if (option.equals("t"))
        {
            searchTitles();
        }
        else if (option.equals("c"))
        {
            searchCast();
        }
        else if (option.equals("k"))
        {
            searchKeywords();
        }
        else if (option.equals("g"))
        {
            listGenres();
        }
        else if (option.equals("r"))
        {
            listHighestRated();
        }
        else if (option.equals("h"))
        {
            listHighestRevenue();
        }
        else
        {
            System.out.println("Invalid choice!");
        }
    }

    private void searchTitles()
    {
        System.out.print("Enter a title search term: ");
        String searchTerm = scanner.nextLine();

        // prevent case sensitivity
        searchTerm = searchTerm.toLowerCase();

        // arraylist to hold search results
        ArrayList<Movie> results = new ArrayList<Movie>();

        // search through ALL movies in collection
        for (int i = 0; i < movies.size(); i++)
        {
            String movieTitle = movies.get(i).getTitle();
            movieTitle = movieTitle.toLowerCase();

            if (movieTitle.indexOf(searchTerm) != -1)
            {
                //add the Movie objest to the results list
                results.add(movies.get(i));
            }
        }

        // sort the results by title
        sortResults(results);

        // now, display them all to the user
        for (int i = 0; i < results.size(); i++)
        {
            String title = results.get(i).getTitle();

            // this will print index 0 as choice 1 in the results list; better for user!
            int choiceNum = i + 1;

            System.out.println("" + choiceNum + ". " + title);
        }

        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        Movie selectedMovie = results.get(choice - 1);

        displayMovieInfo(selectedMovie);

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }

    private void sortResults(ArrayList<Movie> listToSort)
    {
        for (int j = 1; j < listToSort.size(); j++)
        {
            Movie temp = listToSort.get(j);
            String tempTitle = temp.getTitle();

            int possibleIndex = j;
            while (possibleIndex > 0 && tempTitle.compareTo(listToSort.get(possibleIndex - 1).getTitle()) < 0)
            {
                listToSort.set(possibleIndex, listToSort.get(possibleIndex - 1));
                possibleIndex--;
            }
            listToSort.set(possibleIndex, temp);
        }
    }

    private void displayMovieInfo(Movie movie)
    {
        System.out.println();
        System.out.println("Title: " + movie.getTitle());
        System.out.println("Tagline: " + movie.getTagline());
        System.out.println("Runtime: " + movie.getRuntime() + " minutes");
        System.out.println("Year: " + movie.getYear());
        System.out.println("Directed by: " + movie.getDirector());
        System.out.println("Cast: " + movie.getCast());
        System.out.println("Overview: " + movie.getOverview());
        System.out.println("User rating: " + movie.getUserRating());
        System.out.println("Box office revenue: " + movie.getRevenue());
    }

    private void searchCast()
    {
        ArrayList<String>actors = new ArrayList<>();

        for(Movie movie:movies){
            String[] castArray = movie.getCast().split("\\|");
            for(String castMember:castArray){
                String updatedCastMember = castMember.toLowerCase();
                if(!actors.contains(updatedCastMember)){
                    actors.add(updatedCastMember);
                }
            }
        }
        Collections.sort(actors);
        System.out.println("Cast members:");
        for(int i = 0; i < actors.size(); i++){
                System.out.println((i+1) + "." + actors.get(i));
        }
        System.out.println("Choose cast member");
        int castMemberNumber = scanner.nextInt();
        scanner.nextLine();
        String chosenCastMember = actors.get(castMemberNumber-1);
        System.out.println("You chose" + chosenCastMember);
        ArrayList<Movie>moviesWithCastMember = new ArrayList<>();
        for(Movie movie : movies){
            String[]castArray = movie.getCast().split("\\|");
            for(String castMember:castArray){
                if(castMember.trim().equalsIgnoreCase(chosenCastMember)){
                    moviesWithCastMember.add(movie);
                }
            }
        }
        System.out.println("Movies with " + chosenCastMember);
        for (int i = 0; i< moviesWithCastMember.size();i++){
            System.out.println((i+1)+"."+moviesWithCastMember.get(i).getTitle());
        }
        System.out.println("Enter movie number to learn more");
        int movieNumber = scanner.nextInt();
        scanner.nextLine();
        Movie chosenMovie = moviesWithCastMember.get(movieNumber-1);
        displayMovieInfo(chosenMovie);
    }
    private void listGenres() {
        ArrayList<String> genreList = new ArrayList<>();
        for (Movie movie : movies) {
            String[] genreArray = movie.getGenres().split("\\|");
            for (String genres : genreArray) {
                String updatedGenre = genres.toLowerCase();
                if (!genreList.contains(updatedGenre)) {
                    genreList.add(updatedGenre);
                }
            }
        }
        Collections.sort(genreList);
        System.out.println("Genres list:");
        for (int i = 0; i < genreList.size(); i++) {
            System.out.println((i + 1) + "." + genreList.get(i));
        }
        System.out.println("Which genre would you like to learn about?");
        int choice = scanner.nextInt();
        scanner.nextLine();
        String chosenGenre = genreList.get(choice - 1);
        System.out.println("You chose " + chosenGenre);
        ArrayList<Movie> moviesWithGenre = new ArrayList<>();
        for (Movie movie : movies) {
            String[] genreArray = movie.getGenres().split("\\|");
            for (String genre : genreArray) {
                if (genre.trim().equalsIgnoreCase(chosenGenre)) {
                    moviesWithGenre.add(movie);
                }
            }
        }
        System.out.println("Movies with :" + chosenGenre);
        for(int i = 0;i<moviesWithGenre.size();i++){
            System.out.println((i+1)+"."+moviesWithGenre.get(i).getTitle());
        }
        System.out.println("Enter a movie number to learn more");
        int movieNumber = scanner.nextInt();
        scanner.nextLine();
        Movie chosenMovie = moviesWithGenre.get(movieNumber-1);
        displayMovieInfo(chosenMovie);
    }

        private void searchKeywords ()
        {
            System.out.println("Enter a keyword");
            String searchKeyword = scanner.nextLine();
            searchKeyword = searchKeyword.toLowerCase();
            ArrayList<Movie> results = new ArrayList<Movie>();
            for (int i = 0; i < movies.size(); i++) {
                String movieKeyword = movies.get(i).getKeywords();
                movieKeyword = movieKeyword.toLowerCase();

                if (movieKeyword.contains(searchKeyword)) {
                    results.add(movies.get(i));
                }
            }
            sortResults(results);

            // now, display them all to the user
            for (int i = 0; i < results.size(); i++) {
                String keywords = results.get(i).getKeywords();

                // this will print index 0 as choice 1 in the results list; better for user!
                int choiceNum = i + 1;

                System.out.println("" + choiceNum + ". " + keywords);
            }

            System.out.println("Which movie would you like to learn more about?");
            System.out.print("Enter number: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            Movie selectedMovie = results.get(choice - 1);

            displayMovieInfo(selectedMovie);

            System.out.println("\n ** Press Enter to Return to Main Menu **");
            scanner.nextLine();


        }




    private void listHighestRated()
    {
        ArrayList<Movie>highestRatedMovies = new ArrayList<>(movies);
        for(int i = 0;i < highestRatedMovies.size();i++) {
            for (int j = i + 1; j < highestRatedMovies.size(); j++) {
                if (highestRatedMovies.get(i).getUserRating() < highestRatedMovies.get(j).getUserRating()) {
                    Movie temp = highestRatedMovies.get(i);
                    highestRatedMovies.set(i, highestRatedMovies.get(j));
                    highestRatedMovies.set(j, temp);
                }
            }
        }
        for (int o = 0; o < 50; o++) {
            System.out.println((o + 1) + "." + highestRatedMovies.get(o).getTitle()
                    + " Rating:" + highestRatedMovies.get(o).getUserRating());
        }
        System.out.println("What movie would you like to learn about?");
        int choice = scanner.nextInt();
        scanner.nextLine();
        Movie selectedMovie = highestRatedMovies.get(choice - 1);
        displayMovieInfo(selectedMovie);
        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();

    }

    private void listHighestRevenue()
    {
        ArrayList<Movie>highestEarningMovies = new ArrayList<>(movies);
        for(int i = 0; i < highestEarningMovies.size();i++){
            for(int j = i + 1; j < highestEarningMovies.size();j++){
                if(highestEarningMovies.get(i).getRevenue()<highestEarningMovies.get(j).getRevenue()){
                    Movie temp = highestEarningMovies.get(i);
                    highestEarningMovies.set(i,highestEarningMovies.get(j));
                    highestEarningMovies.set(j,temp);
                }
            }
        }
        for (int o = 0; o < 50;o++){
            System.out.println((o+1)+"." + highestEarningMovies.get(o).getTitle() + " Revenue:" +
            highestEarningMovies.get(o).getRevenue());
        }
        System.out.println("What movie do you want to learn about?");
        int choice = scanner.nextInt();
        scanner.nextLine();
        Movie selectedMovie = highestEarningMovies.get(choice-1);
        displayMovieInfo(selectedMovie);
        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();

    }

    private void importMovieList(String fileName)
    {
        try
        {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = bufferedReader.readLine();

            movies = new ArrayList<Movie>();

            while ((line = bufferedReader.readLine()) != null)
            {
                String[] movieFromCSV = line.split(",");

                String title = movieFromCSV[0];
                String cast = movieFromCSV[1];
                String director = movieFromCSV[2];
                String tagline = movieFromCSV[3];
                String keywords = movieFromCSV[4];
                String overview = movieFromCSV[5];
                int runtime = Integer.parseInt(movieFromCSV[6]);
                String genres = movieFromCSV[7];
                double userRating = Double.parseDouble(movieFromCSV[8]);
                int year = Integer.parseInt(movieFromCSV[9]);
                int revenue = Integer.parseInt(movieFromCSV[10]);

                Movie nextMovie = new Movie(title, cast, director, tagline, keywords, overview, runtime, genres, userRating, year, revenue);
                movies.add(nextMovie);
            }
            bufferedReader.close();
        }
        catch(IOException exception)
        {
            // Print out the exception that occurred
            System.out.println("Unable to access " + exception.getMessage());
        }
    }
}