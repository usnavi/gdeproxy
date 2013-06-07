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

  public String Name;
  public String Value;

  private Header(String name, String value) {
    Name = name;
    Value = value;
  }
}

