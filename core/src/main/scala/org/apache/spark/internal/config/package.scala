/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.spark.internal

import java.util.concurrent.TimeUnit

import org.apache.spark.launcher.SparkLauncher
import org.apache.spark.network.util.ByteUnit
import org.apache.spark.util.Utils
package object config {

  private[spark] val DRIVER_CLASS_PATH =
    ConfigBuilder(SparkLauncher.DRIVER_EXTRA_CLASSPATH).stringConf.createOptional

  private[spark] val DRIVER_JAVA_OPTIONS =
    ConfigBuilder(SparkLauncher.DRIVER_EXTRA_JAVA_OPTIONS).stringConf.createOptional

  private[spark] val DRIVER_LIBRARY_PATH =
    ConfigBuilder(SparkLauncher.DRIVER_EXTRA_LIBRARY_PATH).stringConf.createOptional

  private[spark] val DRIVER_USER_CLASS_PATH_FIRST =
    ConfigBuilder("spark.driver.userClassPathFirst").booleanConf.createWithDefault(false)

  private[spark] val DRIVER_MEMORY = ConfigBuilder("spark.driver.memory")
    .bytesConf(ByteUnit.MiB)
    .createWithDefaultString("1g")

  private[spark] val EXECUTOR_CLASS_PATH =
    ConfigBuilder(SparkLauncher.EXECUTOR_EXTRA_CLASSPATH).stringConf.createOptional

  private[spark] val EXECUTOR_JAVA_OPTIONS =
    ConfigBuilder(SparkLauncher.EXECUTOR_EXTRA_JAVA_OPTIONS).stringConf.createOptional

  private[spark] val EXECUTOR_LIBRARY_PATH =
    ConfigBuilder(SparkLauncher.EXECUTOR_EXTRA_LIBRARY_PATH).stringConf.createOptional

  private[spark] val EXECUTOR_USER_CLASS_PATH_FIRST =
    ConfigBuilder("spark.executor.userClassPathFirst").booleanConf.createWithDefault(false)

  private[spark] val EXECUTOR_MEMORY = ConfigBuilder("spark.executor.memory")
    .bytesConf(ByteUnit.MiB)
    .createWithDefaultString("1g")

  private[spark] val IS_PYTHON_APP = ConfigBuilder("spark.yarn.isPython").internal()
    .booleanConf.createWithDefault(false)

  private[spark] val CPUS_PER_TASK = ConfigBuilder("spark.task.cpus").intConf.createWithDefault(1)

  private[spark] val DYN_ALLOCATION_MIN_EXECUTORS =
    ConfigBuilder("spark.dynamicAllocation.minExecutors").intConf.createWithDefault(0)

  private[spark] val DYN_ALLOCATION_INITIAL_EXECUTORS =
    ConfigBuilder("spark.dynamicAllocation.initialExecutors")
      .fallbackConf(DYN_ALLOCATION_MIN_EXECUTORS)

  private[spark] val DYN_ALLOCATION_MAX_EXECUTORS =
    ConfigBuilder("spark.dynamicAllocation.maxExecutors").intConf.createWithDefault(Int.MaxValue)

  private[spark] val SHUFFLE_SERVICE_ENABLED =
    ConfigBuilder("spark.shuffle.service.enabled").booleanConf.createWithDefault(false)

  private[spark] val KEYTAB = ConfigBuilder("spark.yarn.keytab")
    .doc("Location of user's keytab.")
    .stringConf.createOptional

  private[spark] val PRINCIPAL = ConfigBuilder("spark.yarn.principal")
    .doc("Name of the Kerberos principal.")
    .stringConf.createOptional

  private[spark] val TOKEN_RENEWAL_INTERVAL = ConfigBuilder("spark.yarn.token.renewal.interval")
    .internal()
    .timeConf(TimeUnit.MILLISECONDS)
    .createOptional

  private[spark] val EXECUTOR_INSTANCES = ConfigBuilder("spark.executor.instances")
    .intConf
    .createOptional

  private[spark] val PY_FILES = ConfigBuilder("spark.submit.pyFiles")
    .internal()
    .stringConf
    .toSequence
    .createWithDefault(Nil)

  // Note: This is a SQL config but needs to be in core because the REPL depends on it
  private[spark] val CATALOG_IMPLEMENTATION = ConfigBuilder("spark.sql.catalogImplementation")
    .internal()
    .stringConf
    .checkValues(Set("hive", "in-memory"))
    .createWithDefault("in-memory")

  // To limit memory usage, we only track information for a fixed number of tasks
  private[spark] val UI_RETAINED_TASKS = ConfigBuilder("spark.ui.retainedTasks")
    .intConf
    .createWithDefault(100000)

  // To limit how many applications are shown in the History Server summary ui
  private[spark] val HISTORY_UI_MAX_APPS =
    ConfigBuilder("spark.history.ui.maxApplications").intConf.createWithDefault(Integer.MAX_VALUE)

  private[spark] val LISTENER_BUS_EVENT_QUEUE_SIZE =
    ConfigBuilder("spark.scheduler.listenerbus.eventqueue.size")
      .intConf
      .createWithDefault(10000)

  /*
   * RC changes
   */
  private[spark] val DRIVER_HOST_ADDRESS = ConfigBuilder("spark.driver.host")
    .doc("Address of driver endpoints.")
    .stringConf
    .createWithDefault(Utils.localHostName())

  private[spark] val DRIVER_BIND_ADDRESS = ConfigBuilder("spark.driver.bindAddress")
    .doc("Address where to bind network listen sockets on the driver.")
    .fallbackConf(DRIVER_HOST_ADDRESS)

  private[spark] val BLOCK_MANAGER_PORT = ConfigBuilder("spark.blockManager.port")
    .doc("Port to use for the block manager when a more specific setting is not provided.")
    .intConf
    .createWithDefault(0)

  private[spark] val DRIVER_BLOCK_MANAGER_PORT = ConfigBuilder("spark.driver.blockManager.port")
    .doc("Port to use for the block managed on the driver.")
    .fallbackConf(BLOCK_MANAGER_PORT)

}
