package toleco;

/**
 * Toleco Driver constructs a new TolecoApp either in GUI mode or console mode.
 * If a -c flag is provided on the command line the game is run in console mode,
 * if not it is run in GUI mode
 * @author Evan Ralston
 */
public class TolecoDriver
{
    /**
     * Holds the currently running game session
     */
    private static TolecoApp game;


    /**
     * The entry point for Toleco.
     * Either Console (debug) mode or GUI mode will be launched depending
     * on the contents of args.
     *
     * @pre if a map is specifiec, it is a valid Toleco map.
     *
     * @param args the command line arguments used to launch Toleco
     */
    public static void main(String[] args)
    {
        //IF args has a doesn't have a -c flag
        if(args != null && args.length == 2 && args[0].equals("-c"))
        {
            //INITIALIZE game using console mode constructor.
            game = new TolecoApp(args[1]);
        }
        //ELSE IF args is not null and args.length is 0
        else if (args != null && args.length == 0)
        {
            //INITIALIZE game with no params.
            game = new TolecoApp();
        }
        //ELSE
        else
        {
            //Print usage
            System.out.println("USAGE: java TolecoDriver [-c <MapFile>]");
        }
        //END IF
    }
}
