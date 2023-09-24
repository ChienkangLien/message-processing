# kafka

事前準備
使用 2.8 以上的版本無須另外安裝 zookeeper(內建有，在此使用 2.8.1)
解壓縮 kafka 的目錄名稱不能有空格存在

先啟動 zookeeper(在根目錄)
.\bin\windows\zookeeper-server-start.bat .\config\zookeeper.properties
默認 port 號：2181

啟動 kafka(在根目錄)
.\bin\windows\kafka-server-start.bat .\config\server.properties
默認 port 號：9092

查看 topic(在.\bin\windows)
kafka-topics.bat --zookeeper 127.0.0.1:2181 --list

創建 topic(在.\bin\windows)
kafka-topics.bat --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic mytopic1
--zookeeper 指定kafka所連接的zookeeper地址
--topic 主題名稱
--partitions 分區個數
--replication-factor 副本因子

刪除 topic(在.\bin\windows)
kafka-topics.bat --delete --zookeeper localhost:2181 --topic mytopic1
執行後會註記為刪除(mytopic1 - marked for deletion)，並在後續清理週期實際清除

關閉 kafka(在根目錄)
.\bin\windows\kafka-server-stop.bat

關閉 zookeeper(在根目錄)
.\bin\windows\zookeeper-server-stop.bat

在 Windows 的命令行里啟動 Kafka 之後，當關閉命令行窗口時，就會強制關閉 kafka。這種關閉方式為暴力關閉，很可能會導致 Kafka 無法完成對日志文件的解鎖。屆時，再次啟動 kafka 的時候，就會提示日志文件被鎖，無法成功啟動。
解決方案：將 kafka 的日志文件全部刪除，再次啟動即可。

生產者功能測試
kafka-console-producer.bat --broker-list localhost:9092 --topic mytopic

消費者功能測試
kafka-console-consumer.bat --bootstrap-server localhost:9092 --topic mytopic --from-beginning
--bootstrap-server 指定連接kafka集群的地址