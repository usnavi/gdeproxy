package org.rackspace.gdeproxy

/**
 *  An object containing the initial request sent via the make_request method,
 *  and all request/response pairs (Handling objects) processed by
 *  DeproxyEndpoint objects.
 *
 *  Params:
 *  default_handler - An optional handler function to use for requests
 *  related to this MessageChain, if not specified elsewhere
 *  handlers - A mapping object that maps endpoint references or names of
 *  endpoints to handlers
 */
class MessageChain {

    Request sentRequest
    Response receivedResponse
    Handler defaultHandler
    List<Handling> handlings = new ArrayList()
    List<Handling> orphanedHandlings = new ArrayList()

    def addHandling(handling) {
        handlings.add(handling)
    }

    def addOrphanedHandling(handling) {
        orphanedHandlings.add(handling)
    }

    String toString() {
        sprintf('MessageChain(handler_function=%r, sent_request=%r, handlings=%r, received_response=%r, orphaned_handlings=%r)',
                handlerFunction, sentRequest, handlings, receivedResponse, orphanedHandlings)
    }
}