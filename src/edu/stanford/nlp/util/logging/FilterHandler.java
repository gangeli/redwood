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

import java.util.List;

import edu.stanford.nlp.util.logging.Redwood.Record;

/**
 * Basic support for filtering records via LogFilter objects.  Can be used in both conjunctive and disjunctive mode.
 *
 * @author David McClosky
 */
public class FilterHandler extends BooleanLogRecordHandler {
  private List<LogFilter> filters;
  private boolean disjunctiveMode;
  
  public FilterHandler(List<LogFilter> filters, boolean disjunctiveMode) {
    this.filters = filters;
    this.disjunctiveMode = disjunctiveMode;
  }
    
  @Override
  public boolean propagateRecord(Record record) {
    for (LogFilter filter : filters) {
      boolean match = filter.matches(record);
      if (match && disjunctiveMode) {
        return true;
      }
      if (!match && !disjunctiveMode) {
        return false;
      }
    }
    
    if (disjunctiveMode) {
      return false;
    } else {
      return true;
    }
  }
}
