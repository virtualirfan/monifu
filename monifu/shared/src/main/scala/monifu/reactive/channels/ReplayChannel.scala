/*
 * Copyright (c) 2015 Alexandru Nedelcu
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
 
package monifu.reactive.channels

import monifu.concurrent.Scheduler
import monifu.reactive.BufferPolicy
import monifu.reactive.BufferPolicy.Unbounded
import monifu.reactive.subjects.ReplaySubject

/**
 * Represents a [[monifu.reactive.Channel Channel]] that uses an underlying
 * [[monifu.reactive.subjects.ReplaySubject ReplaySubject]].
 */
final class ReplayChannel[T] private (policy: BufferPolicy.Synchronous[T], s: Scheduler)
  extends SubjectChannel(ReplaySubject[T](), policy)(s)

object ReplayChannel {
  /**
   * Builds a [[monifu.reactive.Channel Channel]] that uses an underlying
   * [[monifu.reactive.subjects.ReplaySubject ReplaySubject]].
   */
  def apply[T](bufferPolicy: BufferPolicy.Synchronous[T] = Unbounded)
      (implicit s: Scheduler): ReplayChannel[T] = {

    new ReplayChannel[T](bufferPolicy, s)
  }
}
