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

import java.util.ArrayList;
import java.util.List;

import edu.stanford.nlp.util.logging.Redwood.Record;

public class RerouteChannel extends LogRecordHandler {
  
  private Object oldChannelName;
  private Object newChannelName;

  public RerouteChannel(Object oldChannelName, Object newChannelName) {
    this.oldChannelName = oldChannelName;
    this.newChannelName = newChannelName;
  }

  public List<Record> handle(Record record) {
    List<Record> results = new ArrayList<Record>();
    
    Object[] channels = record.channels();
    for (int i = 0; i < channels.length; i++) {
      Object channel = channels[i];
      if (oldChannelName.equals(channel)) {
        // make a new version of the Record with a different channel name
        channels[i] = newChannelName;
        Record reroutedRecord = new Record(record.content, channels, record.depth, record.callingClass, record.callingMethod, record.timesstamp);
        results.add(reroutedRecord);
        return results;
      }
    }
    
    // didn't find any matching records, so just return the original one
    results.add(record);
    return results;
  }
}
