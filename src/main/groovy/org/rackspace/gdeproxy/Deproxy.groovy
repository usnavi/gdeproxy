package org.rackspace.gdeproxy

import java.util.UUID
import java.util.concurrent.locks.ReentrantLock 
import java.net.URI

/**
 * The main class.
 */
class Deproxy {
  //class Deproxy:
  //    """The main class."""
  //

  String REQUEST_ID_HEADER_NAME = 'Deproxy-Request-ID'
  def _messageChainsLock = new ReentrantLock()
  def _messageChains = [:]
  def _endpointLock = new ReentrantLock()
  def _endpoints = []
  def _defaultHandler = null


  Deproxy(defaultHandler = null) {

    //    def __init__(self, default_handler=None):
    //        """
    //Params:
    //default_handler - An optional handler function to use for requests, if
    //not specified elsewhere
    //"""
    //        self._message_chains_lock = threading.Lock()
    //        self._message_chains = dict()
    //        self._endpoint_lock = threading.Lock()
    //        self._endpoints = []
    //        self.default_handler = default_handler
    _defaultHandler = defaultHandler
    //
  }
    
  public MessageChain makeRequest(String url, String method = "GET", headers = null,
    String requestBody = "", defaultHandler = null,
    Map<DeproxyEndpoint, Object> handlers = null,
    boolean addDefaultHeaders=true) {
    //    def make_request(self, url, method='GET', headers=None, request_body='',
    //                     default_handler=None, handlers=None,
    //                     add_default_headers=True):
    //        """
    //Make an HTTP request to the given url and return a MessageChain.
    //
    //Parameters:
    //
    //url - The URL to send the client request to
    //method - The HTTP method to use, default is 'GET'
    //headers - A collection of request headers to send, defaults to None
    //request_body - The body of the request, as a string, defaults to empty
    //string
    //default_handler - An optional handler function to use for requests
    //related to this client request
    //handlers - A mapping object that maps endpoint references or names of
    //endpoints to handlers. If an endpoint or its name is a key within
    //``handlers``, all requests to that endpoint will be handled by the
    //associated handler
    //add_default_headers - If true, the 'Host', 'Accept', 'Accept-Encoding',
    //and 'User-Agent' headers will be added to the list of headers sent,
    //if not already specified in the ``headers`` parameter above.
    //Otherwise, those headers are not added. Defaults to True.
    //"""
    //        logger.debug('')
    //
    //        if headers is None:
    //            headers = HeaderCollection()
    //        else:
    //            headers = HeaderCollection(headers)
    if (headers == null) {
      headers = new HeaderCollection()
    } else if (headers instanceof Map) {
      data = new HeaderCollection()
      for (String key : headers.getKeys()) {
        data.add(key, headers[key])
      }
      headers = data
    } else if (headers instanceof HeaderCollection) {
      data = new HeaderCollection()
      for (Header header : headers){
        data.add(header.Name, header.Value)
      }
      headers = data
    }
    //
    //        request_id = str(uuid.uuid4())
    def requestId =  UUID.randomUUID().toString()
    //        if request_id_header_name not in headers:
    if (!headers.Contains(REQUEST_ID_HEADER_NAME)){
      //            headers.add(request_id_header_name, request_id)
      headers.add(REQUEST_ID_HEADER_NAME, requestId)
    }
    //
    //        message_chain = MessageChain(default_handler=default_handler,
    //                                     handlers=handlers)
    def messageChain = new MessageChain(defaultHandler, handlers)
    //        self.add_message_chain(request_id, message_chain)
    addMessageChain(requestId, messageChain)
    //
    //        urlparts = list(urlparse.urlsplit(url, 'http'))
    //        scheme = urlparts[0]
    //        host = urlparts[1]
    //        urlparts[0] = ''
    //        urlparts[1] = ''
    //        path = urlparse.urlunsplit(urlparts)
    def uri = new URI(url)
    def host = uri.host
    def port = uri.port
    def scheme = uri.scheme
    def path = uri.path
    //
    //        logger.debug('request_body: "{0}"'.format(request_body))
    //        if len(request_body) > 0:
    if (requestBody == null || requestBody.length() < 1) {
      //            headers.add('Content-Length', len(request_body))
      headers.add("Content-Length", requestBody.length().toString())
    }
    //
    //        if add_default_headers:
    if (addDefaultHeaders){
      //            if 'Host' not in headers:
      //                headers.add('Host', host)
      if (!headers.Contains("Host")){
        headers.add("Host", host)
      }
      //            if 'Accept' not in headers:
      //                headers.add('Accept', '*/*')
      if (!headers.Contains("Accept")){
        headers.add("Accept", "*/*")
      }
      //            if 'Accept-Encoding' not in headers:
      //                headers.add('Accept-Encoding',
      //                            'identity, deflate, compress, gzip')
      if (!headers.Contains("Accept-Encoding")){
        headers.add("Accept-Encoding", "identity")
      }
      //            if 'User-Agent' not in headers:
      //                headers.add('User-Agent', version_string)
      if (!headers.Contains("User-Agent")){
        headers.add("User-Agent", "TODO: versionString")
      }
      //
    }
    //        request = Request(method, path, headers, request_body)
    def request = new Request(method, path, headers, requestBody)
    //
    //        response = self.send_request(scheme, host, request)
    def response = sendRequest(request, scheme, host, port)
    //
    //        self.remove_message_chain(request_id)
    removeMessageChain(requestId)
    //
    //        message_chain.sent_request = request
    messageChain.sentRequest = request
    //        message_chain.received_response = response
    messageChain.receivedResponse = response
    //
    //        return message_chain
    //
    return messageChain
  }

  Object createSslConnection(address, timeout=30, sourceAddress=null) {
    //    def create_ssl_connection(self, address,
    //                              timeout=socket._GLOBAL_DEFAULT_TIMEOUT,
    //                              source_address=None):
    //        """
    //Copied from the socket module and modified for ssl support.
    //
    //Connect to *address* and return the socket object.
    //
    //Convenience function. Connect to *address* (a 2-tuple ``(host,
    //port)``) and return the socket object. Passing the optional
    //*timeout* parameter will set the timeout on the socket instance
    //before attempting to connect. If no *timeout* is supplied, the
    //global default timeout setting returned by :func:`getdefaulttimeout`
    //is used. If *source_address* is set it must be a tuple of (host, port)
    //for the socket to bind as a source address before making the
    //connection. A host of '' or port 0 tells the OS to use the default.
    //"""
    //
    //        host, port = address
    //        err = None
    //        for res in socket.getaddrinfo(host, port, 0, socket.SOCK_STREAM):
    //            af, socktype, proto, canonname, sa = res
    //            sock = None
    //            try:
    //                sock = socket.socket(af, socktype, proto)
    //
    //                sock = ssl.wrap_socket(sock)
    //
    //                if timeout is not socket._GLOBAL_DEFAULT_TIMEOUT:
    //                    sock.settimeout(timeout)
    //                if source_address:
    //                    sock.bind(source_address)
    //                sock.connect(sa)
    //                return sock
    //
    //            except socket.error as _:
    //                err = _
    //                if sock is not None:
    //                    sock.close()
    //
    //        if err is not None:
    //            raise err
    //        else:
    //            raise error("getaddrinfo returns an empty list")
    //
  }
  
  
  //    def send_request(self, scheme, host, request):
  def sendRequest(Request request, scheme, host, port=null) {
    //        """Send the given request to the host and return the Response."""
    //        logger.debug('sending request (scheme="%s", host="%s")' %
    //                     (scheme, host))
    if (port == null || port == "") {
      if (scheme == "https") {
        port = 443
      }
      else {
        port = 80
      }
    }
    //        hostname = hostparts[0]
    //        hostip = socket.gethostbyname(hostname)
    def hostIP = InetAddress.getByName(host)
    //
    //        request_line = '%s %s HTTP/1.1\r\n' % (request.method, request.path)
    def requestLine = String.format("%s %s HTTP/1.1\r\n", request.method, request.path)
    //        lines = [request_line]
    def lines = [requestLine]
    //
    //        for name, value in request.headers.iteritems():
    //            lines.append('%s: %s\r\n' % (name, value))
    request.headers.each{
      lines += String.format("%s: %s\r\n", it.Name, it.Value)
    }
    
    //        lines.append('\r\n')
    lines += "\r\n"
    //        if request.body is not None and len(request.body) > 0:
    if (request.body != null & request.body != "") {
      //            lines.append(request.body)
      lines += request.body
    }
    //
    //        #for line in lines:
    //        # logger.debug(' ' + line)
    //
    //        logger.debug('Creating connection (hostname="%s", port="%s")' %
    //                     (hostname, str(port)))
    //
    //        address = (hostname, port)
    Socket s;
    //        if scheme == 'https':
    if (scheme == "https") {
      //            s = self.create_ssl_connection(address)
      s = createSslConnection(host, port)
      //        else:
    } else {
      //            s = socket.create_connection(address)
      s = createConnection(host, port)
    }
    //
    //        s.send(''.join(lines))
    //
    //        rfile = s.makefile('rb', -1)
    //
    //        logger.debug('Reading response line')
    //        response_line = rfile.readline(65537)
    //        if (len(response_line) > 65536):
    //            raise ValueError
    //        response_line = response_line.rstrip('\r\n')
    //        logger.debug('Response line is ok: %s' % response_line)
    //
    //        words = response_line.split()
    //
    //        proto = words[0]
    //        code = words[1]
    //        message = ' '.join(words[2:])
    //
    //        logger.debug('Reading headers')
    //        response_headers = HeaderCollection.from_stream(rfile)
    //        logger.debug('Headers ok')
    //        for k,v in response_headers.iteritems():
    //            logger.debug(' %s: %s', k, v)
    //
    //        logger.debug('Reading body')
    //        body = read_body_from_stream(rfile, response_headers)
    //
    //        logger.debug('Creating Response object')
    //        response = Response(code, message, response_headers, body)
    //
    //        logger.debug('Returning Response object')
    //        return response
    //
  }
  
  //    def add_endpoint(self, port, name=None, hostname=None,
  //                     default_handler=None):
  def addEndpoint(int port, name=null, hostname=null, defaultHandler=null) {
    //        """Add a DeproxyEndpoint object to this Deproxy object's list of
    //endpoints, giving it the specified server address, and then return the
    //endpoint.
    //
    //Params:
    //port - The port on which the new endpoint will listen
    //name - An optional descriptive name for the new endpoint. If None, a
    //suitable default will be generated
    //hostname - The ``hostname`` portion of the address tuple passed to
    //``socket.bind``. If not specified, it defaults to 'localhost'
    //default_handler - An optional handler function to use for requests that
    //the new endpoint will handle, if not specified elsewhere
    //"""
    //
    //        logger.debug('')
    //        endpoint = None
    def endpoint = null
    //        with self._endpoint_lock:
    synchronized(_endpointLock) {
      //            if name is None:
      if (name == null) {
        //                name = 'Endpoint-%i' % len(self._endpoints)
        name = String.format("Endpoint-%d", _endpoints.size())
      }
      //            endpoint = DeproxyEndpoint(self, port=port, name=name,
      //                                       hostname=hostname,
      //                                       default_handler=default_handler)
      endpoint = new DeproxyEndpoint(this, port, name, hostname, defaultHandler)
      //            self._endpoints.append(endpoint)
      _endpoints.add(endpoint)
      //            return endpoint
      return endpoint
      //
    }
  }
  
  //    def _remove_endpoint(self, endpoint):
  def _remove_endpoint(endpoint) {
    //        """Remove a DeproxyEndpoint from the list of endpoints. Returns True if
    //the endpoint was removed, or False if the endpoint was not in the list.
    //This method should normally not be called by user code. Instead, call
    //the endpoint's shutdown method."""
    //        logger.debug('')

    //        with self._endpoint_lock:
    synchronized (_endpointLock) {
      //            count = len(self._endpoints)
      count = _endpoints.size()
      //            self._endpoints = [e for e in self._endpoints if e != endpoint]
      _endpoints = _endpoints.findAll { e -> e != endpoint }
      //            return (count != len(self._endpoints))
      return (count != _endpoints.size())
      //
    }
  }
  
  //    def shutdown_all_endpoints(self):
  def shutdown() {
    //        """Shutdown and remove all endpoints in use."""
    //        logger.debug('')
    def endpoints = []
    synchronized (_endpointLock) {
      for (e in _endpoints) {
        e.shutdown()
      }
      _endpoints = []
    }
  }
  
  //    def add_message_chain(self, request_id, message_chain):
  def addMessageChain(requestId, messageChain) {
    //        """Add a MessageChain to the internal list for the given request ID."""
    //        logger.debug('request_id = %s' % request_id)
    //        with self._message_chains_lock:
    synchronized (_messageChainsLock) {
      //            self._message_chains[request_id] = message_chain
      _messageChains[requestId] = messageChain
      //
    }
  }
  
  //    def remove_message_chain(self, request_id):
  def removeMessageChain(requestId) {
    //        """Remove a particular MessageChain from the internal list."""
    //        logger.debug('request_id = %s' % request_id)
    //        with self._message_chains_lock:
    synchronized (_messageChainsLock) {
      //            del self._message_chains[request_id]
      _messageChains.remove(requestId)
      //
    }
  }
  
  //    def get_message_chain(self, request_id):
  def getMessageChain(requestId) {
    //        """Return the MessageChain for the given request ID."""
    //        logger.debug('request_id = %s' % request_id)
    //        with self._message_chains_lock:
    synchronized (_messageChainsLock) {
      //            if request_id in self._message_chains:
      if (_messageChains.containsKey(requestId)) {
        //                return self._message_chains[request_id]
        return _messageChains[requestId]
        //            else:
      } else {
        //                return None
        return null
        //
      }
    }
  }
  
  //    def add_orphaned_handling(self, handling):
  def addOrphanedHandling(handling) {
    //        """Add the handling to all available MessageChains."""
    //        logger.debug('Adding orphaned handling')
    //        with self._message_chains_lock:
    synchronized (_handlingsLock) {
      //            for mc in self._message_chains.itervalues():
      for (mc in _messageChains.values()) {
        //                mc.add_orphaned_handling(handling)
        mc.addOrphanedHandling(handling)
      }
    }
  }



  //
  //def read_body_from_stream(stream, headers):
  //    if ('Transfer-Encoding' in headers and
  //            headers['Transfer-Encoding'] != 'identity'):
  //        # 2
  //        logger.debug('NotImplementedError - Transfer-Encoding != identity')
  //        raise NotImplementedError
  //    elif 'Content-Length' in headers:
  //        # 3
  //        length = int(headers['Content-Length'])
  //        body = stream.read(length)
  //    elif False:
  //        # multipart/byteranges ?
  //        logger.debug('NotImplementedError - multipart/byteranges')
  //        raise NotImplementedError
  //    else:
  //        # there is no body
  //        body = None
  //    return body
}