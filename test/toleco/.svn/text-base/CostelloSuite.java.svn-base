package toleco;

import junit.framework.Test;
import junit.extensions.abbot.*;

/**
 *
 * @author jdalbey
 */
public class CostelloSuite extends ScriptFixture
{

    public CostelloSuite(String testName) {
        super(testName);
    }

     public static Test suite()
     {
         return new ScriptTestSuite(CostelloSuite.class, "test/toleco/guiscripts")
         {
             public boolean accept(java.io.File file)
             {
                 String name = file.getName();
                 if (name.startsWith("guiscript") && name.endsWith(".xml"))
                 {
                     System.out.println("Running " + file);
                     return true;
                 }
                 else
                 {
                     return false;
                 }
             }
         };
     }

}
