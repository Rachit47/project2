package com.springbootproject.librarium365.controller;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SessionTracker implements HttpSessionListener {
	private static final Logger log = LoggerFactory.getLogger(SessionTracker.class);
    private static final Map<String, HttpSessionEvent> sessions = Collections.synchronizedMap(new HashMap<>());

    @Override
    public void sessionCreated(HttpSessionEvent event) {
		log.info("session created :{}",event.getSession().getId());
        sessions.put(event.getSession().getId(), event);
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent event) {
    	log.info("session destroyed :{}",event.getSession().getId());
        sessions.remove(event.getSession().getId());
    }

    public static Map<String, HttpSessionEvent> getSessions() {
        return sessions;
    }
}
