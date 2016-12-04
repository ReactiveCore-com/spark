/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 */
package org.apache.spark

object AdvertisedHostTest {
  def main(args: Array[String]) {
    val master = args.length match {
      case x: Int if x > 0 => args(0)
      case _ => "spark://ec2-52-53-209-85.us-west-1.compute.amazonaws.com:7077" // "local[4]"
    }

//    val master = "local[4]" // "spark://esNode-1.com:9292", 192.168.1.31
    val appName = "appNameParam"
    val esNodes = List("spark://esNode-1.com:9292", "esNode-1")
    val requiredJars = List.empty

    val sparkConf = new SparkConf()
      .setMaster(master)
      .setAppName(appName)
      .set("es.nodes", esNodes.mkString(","))
      .set("spark.driver.bindAddress", "0.0.0.0")
      .set("spark.driver.host", "74.90.189.49")
      .set("spark.driver.port", "9999")
      .set("spark.driver.blockManager.port", "10000")

    //    val sc = new SparkContext(master, "BasicMap", System.getenv("SPARK_HOME"))
    val sc = new SparkContext(sparkConf)
    val input = sc.parallelize(List(1, 2, 3, 4))
    val result = input.map(x => x * x)

    // scalastyle:off println
    println(result.collect().mkString(","))

    sparkConf.getAll foreach { x =>
      println(s"getAll: ($x._1, $x._2)")
    }

    sc.getConf.getAll foreach { x =>
      println(s"getConf.getAll: ($x._1, $x._2)")
    }

    sparkConf.getExecutorEnv foreach { x =>
      println(s"getExecutorEnv: ($x._1, $x._2)")
    }

    // scalastyle:on println
  }
}
