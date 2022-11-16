import breeze.linalg._
import breeze.numerics._
import breeze.optimize._
import org.apache.spark.ml.regression.LinearRegression
import org.apache.spark.ml.regression.LinearRegressionModel
import org.apache.spark.ml.Pipeline
import org.apache.spark.ml.feature.VectorAssembler

import org.apache.spark.sql.SparkSession

object SimpleApp {
    def main(args: Array[String]) {
      val logFile = "./all.log" // Should be some file on your system
      val spark = SparkSession.builder.appName("Simple Application").master("local").getOrCreate()
      //val conf = new SparkConf().setAppName("Simple Application").setMaster("local[2]")
      //val spark = SparkSession.builder().config(conf).appName("SimpleApp").master("local").getOrCreate()
      //val sc: SparkContext = spark.sparkContext
      import spark.implicits._
      val N = 100000
      val M = 3
      val n_iter = 100
      val step = 0.001
      val X = DenseMatrix.rand(N,M)
      val y = X * DenseVector(1.5, 0.3, -0.7)
      val data = DenseMatrix.horzcat(X, y.asDenseMatrix.t)
      val df = data(*, ::).iterator.map(x => (x(0),x(1),x(2),x(3))).toSeq.toDF("x1","x2","x3","y")
      val pipeline = new Pipeline().setStages(
        Array(
          new VectorAssembler()
            .setInputCols(Array("x1","x2","x3"))
            .setOutputCol("features"),
          new LinearRegression().setLabelCol("y")
        )
      )
      val model = pipeline.fit(df)
      val w = model.stages.last.asInstanceOf[LinearRegressionModel].coefficients
      println("w="+w)
    }
}

