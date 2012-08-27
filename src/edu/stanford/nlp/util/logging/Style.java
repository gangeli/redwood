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

/**
 * ANSI supported styles (rather, a subset of)
 * These values are mirrored in Redwood.Util
 * @author Gabor Angeli (angeli at cs.stanford)
 */
public enum Style {
  NONE(""), BOLD("\033[1m"), DIM("\033[2m"), ITALIC("\033[3m"), UNDERLINE("\033[4m"), BLINK("\033[5m"), CROSS_OUT("\033[9m");
  public final String ansiCode;
  private Style(String ansiCode){
    this.ansiCode = ansiCode;
  }
}
