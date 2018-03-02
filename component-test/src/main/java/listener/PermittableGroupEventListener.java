/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package listener;

import io.mifos.core.lang.config.TenantHeaderFilter;
import io.mifos.core.test.listener.EventRecorder;
import io.mifos.identity.api.v1.events.EventConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

/**
 * @author Myrle Krantz
 */
@SuppressWarnings("unused")
@Component
public class PermittableGroupEventListener {

  private final EventRecorder eventRecorder;

  @Autowired
  public PermittableGroupEventListener(@SuppressWarnings("SpringJavaAutowiringInspection") final EventRecorder eventRecorder)
  {
    this.eventRecorder = eventRecorder;
  }

  @JmsListener(
          subscription = EventConstants.DESTINATION,
          destination = EventConstants.DESTINATION,
          selector = EventConstants.SELECTOR_POST_PERMITTABLE_GROUP
  )
  public void onCreatePermittableGroup(
          @Header(TenantHeaderFilter.TENANT_HEADER)final String tenant,
          final String payload) throws Exception {
    eventRecorder.event(tenant, EventConstants.OPERATION_POST_PERMITTABLE_GROUP, payload, String.class);
  }
}
