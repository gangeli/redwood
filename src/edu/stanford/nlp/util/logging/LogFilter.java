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

import edu.stanford.nlp.util.logging.Redwood.Record;

/**
 * Simple interface to determine if a Record matches a set of criteria.  Inner classes provide some common filtering operations.  Other simple and generate purpose LogFilters should
 * be added here as well.
 * 
 * @author David McClosky
 */
public interface LogFilter {
  public boolean matches(Record message);
  
  public static class HasChannel implements LogFilter {
    private Object matchingChannel;

    public HasChannel(Object message) {
      this.matchingChannel = message;
    }

    public boolean matches(Record record) {
      for (Object tag : record.channels()) {
        if (tag.equals(matchingChannel)) {
          return true;
        }
      }
      return false;
    }
  }
  
  /**
   * Propagate records containing certain substrings.  Note that this doesn't require Records to have String messages since it will call toString() on them anyway.
   */
  public static class ContainsMessage implements LogFilter {
    private String substring;

    public ContainsMessage(String message) {
      this.substring = message;
    }

    public boolean matches(Record record) {
      String content = record.content.toString();
      return content.contains(this.substring);
    }
  }

  /**
   * Propagate records when Records match a specific message exactly (equals() is used for comparisons)
   */
  public static class MatchesMessage implements LogFilter {
    private Object message;

    public MatchesMessage(Object message) {
      this.message = message;
    }

    public boolean matches(Record record) {
      return record.content.equals(message);
    }
  }
  
  /**
   * Propagate records which originate from a specific method.
   */
  public static class HasCallingMethod implements LogFilter {
    private String callingMethod;

    public HasCallingMethod(String source) {
      this.callingMethod = source;
    }

    public boolean matches(Record record) {
      return this.callingMethod.equals(record.callingMethod);
    }
  }

  /**
   * Propagate records which originate from a specific calling class.  When specifying the calling class,
   * it is sufficient to just specify a suffix of it.
   */
  public static class HasCallingClass implements LogFilter {
    private String callingClassSuffix;

    public HasCallingClass(String sourceSuffix) {
      this.callingClassSuffix = sourceSuffix;
    }

    public boolean matches(Record record) {
      return record.callingClass.endsWith(this.callingClassSuffix);
    }
  }
}