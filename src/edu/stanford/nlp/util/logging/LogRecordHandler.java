/* 
 * Redwood -- a framework for logging in Java
 * Copyright (c) 2012 The Board of Trustees of
 * The Leland Stanford Junior University. All Rights Reserved.
 *
 * Redwood is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation.
 * 
 * Redwood is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

package edu.stanford.nlp.util.logging;

import edu.stanford.nlp.util.logging.Redwood.Record;

import java.util.Collections;
import java.util.List;

/**
 * A log message handler. This can take the role of a filter, which blocks future handlers from
 * receiving the message, or as an entity that produces a side effect based on the message
 * (e.g. printing it to console or a file).
 *
 * All log Records pass through an ordered list of Handler objects; all operations done on a log
 * Record are done by some handler or another.
 *
 * When writing filters, you should see {@link BooleanLogRecordHandler} instead which allows for a
 * simpler interface.
 *
 * @see BooleanLogRecordHandler
 */
public abstract class LogRecordHandler {
  /**
   * An empty list to serve as the FALSE token for filters
   */
  public static final List<Record> EMPTY = Collections.emptyList();

  /**
   * Handle a log Record, either as a filter or by producing a side effect.
   * @param record The log record to handle
   * @return a (possibly empty) list of records to be sent on in the pipeline
   */
  public abstract List<Record> handle(Record record);

  /**
   * Signal the start of a track, i.e. that we have descended a level deeper.
   * @param signal A record corresponding to the information in the track header.
   *               The depth in this object is the old log depth.
   * @return A list of records to pass down the pipeline, not including the startTrack() signal.
   *         The returned records are passed to handle(), not startTrack(),
   *         and are sent before the startTrack() signal.
   */
  public List<Record> signalStartTrack(Record signal) {
    return EMPTY;
  }

  /**
   * Signal the end of a track, i.e. that we have popped up to a higher level.
   * @param newDepth The new depth; that is, the current depth - 1.
    * @return A list of records to pass down the pipeline.
   *         The returned records are passed to handle(), not endTrack().
   *         and are sent before the startTrack() signal.
   */
  public List<Record> signalEndTrack(int newDepth, long timeEnded) {
    return EMPTY;
  }

  public List<Record> signalShutdown(){
    return EMPTY;
  }
}
