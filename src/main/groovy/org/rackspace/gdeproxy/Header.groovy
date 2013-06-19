/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.rackspace.gdeproxy

/**
 *
 * @author ricahrd-sartor
 *
 * A simple name-value pair.
 *
 */
class Header {

  public String name;
  public String value;

  private Header(String name, String value) {
    name = name;
    value = value;
  }
}

