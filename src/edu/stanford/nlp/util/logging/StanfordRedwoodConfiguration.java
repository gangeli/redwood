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

import java.util.Properties;

/**
 * @author Gabor Angeli (angeli at cs.stanford)
 */
public class StanfordRedwoodConfiguration extends RedwoodConfiguration {
  /**
   * Private constructor to prevent use of "new StanfordRedwoodConfiguration()"
   */
  private StanfordRedwoodConfiguration() {
    super();
  }

  /**
   * Configures the Redwood logger using a reasonable set of defaults,
   * which can be overruled by the supplied Properties file
   * @param props The properties file to overrule or augment the default
   *              configuration
   */
  public static void apply(Properties props){
    //--Tweak Properties
    //(capture system streams)
    if(props.getProperty("log.captureStderr") == null){
      props.setProperty("log.captureStderr", "true");
    }
    //(log to stderr)
    if(props.getProperty("log.toStderr") == null){
      props.setProperty("log.toStderr", "true");
    }
    //(apply properties)
    RedwoodConfiguration.apply(props);

    //--Strange Tweaks
    //(adapt legacy logging systems)
    JavaUtilLoggingAdaptor.adapt();
    //(skip stack trace elements from this class)
    Redwood.addLoggingClass("edu.stanford.nlp.kbp.slotfilling.common.Log");

    // TODO: Redwood.setIgnorableClassPrefix("edu.stanford.nlp")
  }

  /**
   * Set up the Redwood logger with Stanford's default configuration
   */
  public static void setup(){
    apply(new Properties());
  }

	public static void minimalSetup(){
		Properties props = new Properties();
		props.setProperty("log.toStderr", "true");
		RedwoodConfiguration.apply(props);
	}
}
