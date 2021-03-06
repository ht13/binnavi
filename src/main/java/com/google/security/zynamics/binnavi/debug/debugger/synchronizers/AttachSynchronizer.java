/*
Copyright 2011-2016 Google Inc. All Rights Reserved.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/
package com.google.security.zynamics.binnavi.debug.debugger.synchronizers;

import com.google.security.zynamics.binnavi.debug.connection.packets.replies.AttachReply;
import com.google.security.zynamics.binnavi.debug.debugger.interfaces.IDebugEventListener;
import com.google.security.zynamics.binnavi.debug.debugger.interfaces.IDebugger;
import com.google.security.zynamics.zylib.general.ListenerProvider;

/**
 * Synchronizes incoming replies to Attach requests by interpreting the effects of the reply and
 * applying these effects to the state of the debugger that sent the Attach request.
 */
public final class AttachSynchronizer extends ReplySynchronizer<AttachReply> {
  /**
   * Creates a new Attach synchronizer.
   *
   * @param debugger The debug client synchronize.
   * @param listeners Listeners that are notified about relevant events.
   */
  public AttachSynchronizer(
      final IDebugger debugger, final ListenerProvider<IDebugEventListener> listeners) {
    super(debugger, listeners);
  }

  /**
   * Handles incoming Attach replies that report errors during that occurred during the Attach
   * process.
   *
   * @param reply The incoming Attach reply to handle.
   */
  @Override
  protected void handleError(final AttachReply reply) {
    getDebugger().setTerminated();
  }

  /**
   * Handles incoming Attach replies that report of successful Attach operations.
   */
  @Override
  protected void handleSuccess(final AttachReply reply) {
    getDebugger().getProcessManager().setAttached(true);
  }

  @Override
  protected void notifyListener(final IDebugEventListener listener, final AttachReply reply) {
    listener.receivedReply(reply);
  }
}
