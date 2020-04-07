
# netty-app
初始化

nohup java -jar web.jar >biz.log &
nohup java -jar web.jar >$(date '+%Y%m%d').log &
nohup java -jar web.jar >$(date '+%Y%m%d_%H%M%S').log &
nohup java -jar web.jar ap-southeast-1 >$(date '+%Y%m%d').txt &

ps -ef | grep java | grep web.jar | cut -c 9-15 | xargs kill
ps -ef | grep java | grep web.jar | cut -c 9-15 | xargs kill -9
ps -ef | grep java | grep web.jar | awk '{print $2}'| xargs kill -9

