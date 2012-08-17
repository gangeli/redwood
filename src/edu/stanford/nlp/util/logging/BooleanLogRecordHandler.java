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

/**
 * A log message handler designed for filtering.  This is a convenience class to handle the common case of filtering messages.
 * 
 * @author David McClosky
 */
public abstract class BooleanLogRecordHandler extends LogRecordHandler {

  /**
   * For BooleanLogRecordHandler, you should leave this alone and implement propagateRecord instead.
   */
  public List<Record> handle(Record record) {
    boolean keep = propagateRecord(record);
    if (keep) {
      ArrayList<Record> records = new ArrayList<Record>();
      records.add(record);
      return records;
    } else {
      return LogRecordHandler.EMPTY;
    }
  }

  /**
   * Given a record, return true if it should be propagated to later handlers.
   */
  public abstract boolean propagateRecord(Record record);
}