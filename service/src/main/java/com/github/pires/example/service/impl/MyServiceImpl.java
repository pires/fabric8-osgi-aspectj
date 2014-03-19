package com.github.pires.example.service.impl;

import com.github.pires.example.service.MyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Implementation of {@link MyService}.
 */
public class MyServiceImpl implements MyService {

  private static final Logger log = LoggerFactory.getLogger(MyService.class);

  public void doSomething() {
    log.info("doing something..");
  }

}
