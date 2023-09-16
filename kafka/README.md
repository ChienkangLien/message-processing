# rabbitMQ

事前準備
基於Erlang語言編寫、需安裝Erlang，環境變數(新增ERLANG_HOME：安裝的根目錄 / 增加Path：ERLANG_HOME\bin)
安裝rabbitMQ，可透過cmd或是工作管理員來開啟/關閉服務

服務管理UI需開啟插件(執行rabbit-plugins.bat enable rabbit_management)
瀏覽器訪問後端服務默認port 15672(http://localhost:15672)、默認帳號密碼皆是guest

啟用tracing插件 rabbitmq-plugins.bat enable rabbitmq_tracing
可以於UI中admin/tracing配置追蹤消息