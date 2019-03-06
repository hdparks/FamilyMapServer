package services;

import requests.EventIDRequest;
import responses.EventIDResponse;

/**
 * Returns a single Event object with the specified ID
 *
 */
public class EventIDService implements Service<EventIDRequest, EventIDResponse> {
    /**
     * Handles a requests made to /event/[eventID]
     * @param req the EventIDRequest object
     * @return a EventIDResponse object
     */
    @Override
    public EventIDResponse serveResponse(EventIDRequest req) {
        return null;
    }
}
