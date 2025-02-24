import java.util.ArrayList;

public class Main
{
    public static void main(String arg[])
    {
        MovieCollection myCollection = new MovieCollection("src/movies_data.csv");
        myCollection.menu();
    }
}