import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class CallingScript {

    public static void main(String[] args) {
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("groovy");
        System.out.println("Calling script from java");
        try {
            engine.eval("println 'Hello from java'");
            engine.put("name", "Venkat");
            engine.eval("println \"hello ${name} from groovy\"; name+='!'");
            String name = (String) engine.get("name");
            System.out.println("Back in java " + name);
        } catch (ScriptException e) {
            System.out.println(e);
        }
    }
}
