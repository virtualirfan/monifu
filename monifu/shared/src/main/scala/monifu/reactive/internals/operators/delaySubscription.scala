/*
 * Copyright (c) 2014-2015 by its authors. Some rights reserved.
 * See the project homepage at: http://www.monifu.org
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package monifu.reactive.internals.operators

import monifu.reactive.Observable
import scala.concurrent.duration.FiniteDuration
import scala.concurrent.{Promise, Future}
import scala.util.{Failure, Success}


private[reactive] object delaySubscription {
  /**
   * Implementation for [[Observable.delaySubscription]].
   */
  def onFuture[T](source: Observable[T], future: Future[_]): Observable[T] =
      Observable.create[T] { subscriber =>
        import subscriber.{scheduler => s}

        future.onComplete {
          case Success(_) =>
            source.onSubscribe(subscriber)
          case Failure(ex) =>
            subscriber.onError(ex)
        }
      }

  /**
   * Implementation for [[Observable.delaySubscription]].
   */
  def onTimespan[T](source: Observable[T], timespan: FiniteDuration): Observable[T] =
    Observable.create { subscriber =>
      import subscriber.{scheduler => s}
      val underlying = source.delaySubscription {
        val p = Promise[Unit]()
        s.scheduleOnce(timespan)(p.success(()))
        p.future
      }

      underlying.onSubscribe(subscriber)
    }
}
