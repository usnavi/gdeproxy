package org.rackspace.gdeproxy

/**
 * An object representing a request received by an endpoint and the
 * response it returns.
 */
class Handling {

    def endpoint
    Request request
    Response response

  public Handling(DeproxyEndpoint endpoint, Request request, Response response) {
    this.endpoint = endpoint;
    this.request = request;
    this.response = response;
  }

    String toString() {
        sprintf('Handling(endpoint=%r, request=%r, response=%r)', endpoint, request, response)
    }

}
