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

import com.google.security.zynamics.binnavi.debug.connection.packets.replies.DetachReply;
import com.google.security.zynamics.binnavi.debug.debugger.interfaces.IDebugEventListener;
import com.google.security.zynamics.binnavi.debug.debugger.interfaces.IDebugger;
import com.google.security.zynamics.zylib.general.ListenerProvider;

/**
 * Synchronizes incoming replies to Detach requests by interpreting the effects of the reply and
 * applying these effects to the state of the debugger that sent the request.
 */
public final class DetachSynchronizer extends ReplySynchronizer<DetachReply> {
  /**
   * Creates a new Detach synchronizer.
   *
   * @param debugger The debug client synchronize.
   * @param listeners Listeners that are notified about relevant events.
   */
  public DetachSynchronizer(final IDebugger debugger,
      final ListenerProvider<IDebugEventListener> listeners) {
    super(debugger, listeners);
  }

  @Override
  protected void handleSuccess(final DetachReply reply) {
    // After the debug client detached from the target process, the information
    // about the target process is reset.
    resetTargetProcess();
  }

  @Override
  protected void notifyListener(final IDebugEventListener listener, final DetachReply reply) {
    listener.receivedReply(reply);

    if (reply.success()) {
      listener.debuggerClosed(reply.getErrorCode());
    }
  }
}
