
package org.rackspace.gdeproxy
/**
 *
 * @author ricahrd-sartor
 *
 * A collection class for HTTP Headers. This class combines aspects of a list
 * and a map. Lookup is always case-insenitive. A key can be added multiple
 * times with different values, and all of those values will be kept in the
 * same order as entered.
 *
 */
class HeaderCollection {


  List<Header> _headers = new ArrayList<Header>();

  HeaderCollection() {
  }

  HeaderCollection(Map<? extends Object, ? extends Object> map){
    for (entry in map.entrySet) {
      this.add(entry.getKey().toString(), entry.getValue().toString())
    }
  }

  HeaderCollection(HeaderCollection headers){
    for (header in headers._headers) {
      this.add(new Header(header.name, header.value))
    }
  }

  boolean contains(String name) {
    for (Header header : _headers) {
      if (name.equalsIgnoreCase(header.name)) {
        return true;
      }
    }

    return false;
  }

  public Object each(Closure closure) {
    return _headers.each(closure)
  }

  public Object eachWithIndex(Closure closure) {
    return _headers.eachWithIndex(closure)
  }

  public int size() {
    return _headers.size();
  }


  public void add(String name, String value) {
    add(new Header(name, value));
  }

  public void add(Header header) {
    _headers.add(header)
  }

  def findAll(String name) {

    def values = []

    _headers.each {
      if (it.name.equalsIgnoreCase(name)){
        values += it.value
      }
    }

    return values
  }

  public void deleteAll(String name) {

    ArrayList<Header> toRemove = new ArrayList<Header>();

    for (Header header : _headers) {
      if (name.equalsIgnoreCase(header.name)) {
        toRemove.add(header);
      }
    }

    _headers.removeAll(toRemove);
  }

  public String[] getNames() {
    ArrayList<String> names = new ArrayList<String>();
    for (Header header : _headers) {
      names.add(header.name);
    }

    return names.toArray(new String[0]);
  }

  public String[] getValues() {
    ArrayList<String> values = new ArrayList<String>();
    for (Header header : _headers) {
      values.add(header.value);
    }

    return values.toArray(new String[0]);
  }

  public Header[] getItems() {
    return _headers.toArray(new Header[0]);
  }

  public String getFirstValue(String name, String defaultValue=null) {
    for (Header header : _headers) {
      if (name.equalsIgnoreCase(header.name)) {
        return header.value;
      }
    }

    return defaultValue;
  }

  public static HeaderCollection fromReader(reader) throws IOException {

    HeaderCollection headers = new HeaderCollection();
    String line = reader.readLine();
    while (line != null && !line.equals("") && !line.equals("\r\n")) {
      String[] parts = line.split(":", 2);
      String name = parts[0];
      String value = (parts.length > 1 ? parts[1] : "");
      name = name.trim();
      line = reader.readLine();
      while (line.startsWith(" ") || line.startsWith("\t")) {
        // Continuation lines - see RFC 2616, section 4.2
        value += " " + line;
        line = reader.readLine();
      }
      headers.add(name, value.trim());
    }
    return headers;

  }

    public String toString() {
        return _headers.toString()
    }
}
