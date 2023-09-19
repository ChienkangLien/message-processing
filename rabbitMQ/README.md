# rabbitMQ

事前準備
基於 Erlang 語言編寫、需安裝 Erlang，環境變數(新增 ERLANG_HOME：安裝的根目錄 / 增加 Path：ERLANG_HOME\bin)
安裝 rabbitMQ(在此使用 3.12.4)，可透過 cmd 或是工作管理員來開啟/關閉服務

服務管理 UI 需開啟插件(執行 rabbit-plugins.bat enable rabbit_management)
瀏覽器訪問後端服務默認 port 15672(`http://localhost:15672`)、默認帳號密碼皆是 guest

啟用 tracing 插件 rabbitmq-plugins.bat enable rabbitmq_tracing
可以於 UI 中 admin/tracing 配置追蹤消息
