/* Redwood is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * Redwood is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

package edu.stanford.nlp.util.logging;

import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;


/**
 * Reroutes java.util.logging messages to the Redwood logging system.
 * 
 * @author David McClosky
 */
public class JavaUtilLoggingAdaptor {
  private static boolean addedRedwoodHandler = false;

  public static void adapt() {
    // get the top Logger:
    Logger topLogger = Logger.getLogger("");
    Handler oldConsoleHandler = null;

    // see if there is already a console handler
    // hopefully reasonable assumption: there's only one ConsoleHandler
    // TODO confirm that this will always give us all handlers (i.e. do we need to loop over all Loggers in java.util.LogManager and do this for each one?)
    for (Handler handler : topLogger.getHandlers()) {
      if (handler instanceof ConsoleHandler && !(handler instanceof RedwoodHandler)) {
        // found the console handler
        oldConsoleHandler = handler;
        break;
      }
    }

    if (oldConsoleHandler != null) {
      // it's safe to call this after it's been removed    
      topLogger.removeHandler(oldConsoleHandler);
    }

    if (!addedRedwoodHandler) {
      Handler redwoodHandler = new JavaUtilLoggingAdaptor.RedwoodHandler();
      topLogger.addHandler(redwoodHandler);
      addedRedwoodHandler = true;
    }

    // exclude ourselves from stacktraces
    Redwood.addLoggingClass(JavaUtilLoggingAdaptor.class.getCanonicalName());
    Redwood.addLoggingClass("java.util.logging");
  }

  /**
   * This is the bridge class which actually adapts java.util.logging calls to Redwood calls.
   */
  public static class RedwoodHandler extends ConsoleHandler {

    /**
     * This is a no-op since Redwood doesn't have this.
     */
    @Override
    public void close() throws SecurityException {
    }

    /**
     * This is a no-op since Redwood doesn't have this.
     */
    @Override
    public void flush() {      
    }

    /**
     * Convert a java.util.logging call to its equivalent Redwood logging call.
     * Currently, the WARNING log level becomes Redwood WARNING flag, the SEVERE log level becomes Redwood.ERR, and anything at FINE or lower becomes Redwood.DBG
     * CONFIG and INFO don't map to a Redwood tag.
     */
    @Override
    public void publish(LogRecord record) {
      String message = record.getMessage();
      Level level = record.getLevel();
      Object tag = null;
      if (level == Level.WARNING) {
        tag = Redwood.WARN;
      } else if (level == Level.SEVERE) {
        tag = Redwood.ERR;
      } else if (level.intValue() <= Level.FINE.intValue()) {
        tag = Redwood.DBG;
      }

      if (tag == null) {
        Redwood.log(message);
      } else {
        Redwood.log(tag, message);
      }
    }

  }

  /**
   * Simple test case.
   */
  public static void main(String[] args) {
    Redwood.log(Redwood.DBG, "at the top");
    Redwood.startTrack("Adaptation test");

    Logger topLogger = Logger.getLogger("global");
    topLogger.warning("I'm warning you!");
    topLogger.severe("Now I'm using my severe voice.");
    topLogger.info("FYI");

    Redwood.log(Redwood.DBG, "adapting");
    JavaUtilLoggingAdaptor.adapt();
    topLogger.warning("I'm warning you in Redwood!");
    JavaUtilLoggingAdaptor.adapt(); // should be safe to call this twice
    topLogger.severe("Now I'm using my severe voice in Redwood!");
    topLogger.info("FYI: Redwood rocks");

    // make sure original java.util.logging levels are respected
    topLogger.setLevel(Level.OFF);
    topLogger.severe("We shouldn't see this message.");

    Redwood.log(Redwood.DBG, "at the bottom");
    Redwood.endTrack("Adaptation test");
  }
}
