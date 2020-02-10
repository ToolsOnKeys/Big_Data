# RDD依赖关系

## 1、查看血缘关系

* 通过toDebugString方法，可以查看RDD血缘关系
* 同时可以查看RDD执行的并度（即分区数）
  * HadoopRDD
  * MapPartitionsRDD
  * ShuffledRDD

## 2、查看依赖关系

* 通过dependencies方法，可以查看RDD的依赖关系

