# 部署

## 1、基本命令

```bash
# 关机
poweroff
# 重启
shutdown -r now
```



## 2、网络配置

```bash
# ip
ifconfig -a
ip addr

# vim
yum install -y vim

# 网卡
nmcli con add con-name enp0s8 type ethernet ifname enp0s8
service network restart

cd /etc/sysconfig/network-scripts/
vim ifcfg-enp0s3

# 防火墙 
systemctl status firewalld
systemctl stop firewalld
systemctl disable firewalld

# 入站规则
yum list installed | grep iptables-services
yum install -y iptables-services

/sbin/iptables -I INPUT -p tcp --dport 22 -j ACCEPT
/sbin/iptables -I INPUT -p tcp --dport 8080 -j ACCEPT

service iptables save
service iptables restart

iptables -nL

# ssh
rpm -qa | grep ssh
yum install openssh-server
service sshd start

netstat -ntpl | grep 22

vim /etc/ssh/sshd_config
Port 22
service sshd restart

# 端口转发：virtualbox中网络配置
```



## 3、Jdk

```shell
# 解压
cd /usr/local
tar -zxvf jdk-8u121-linux-x64.tar.gz

# 软链接
ln -s /usr/local/jdk1.8.0_121/ /usr/local/jdk

# 环境变量
vim /etc/profile

export JAVA_HOME=/usr/local/jdk
export JRE_HOME=$JAVA_HOME/jre
export CLASSPATH=.:$CLASSPATH:$JAVA_HOME/lib:$JRE_HOME/lib
export PATH=$PATH:$JAVA_HOME/bin:$JRE_HOME/bin

source /etc/profile

# 验证
java -version
```



## 4、Maven

```shell
# 解压
cd/usr/local
tar -zxvf apache-maven-3.6.1-bin.tar.gz

# 软链接
ln -s /usr/local/apache-maven-3.6.1/ /usr/local/maven

# 环境变量
vim /etc/profile

export MAVEN_HOME=/usr/local/maven
export PATH=$PATH:$MAVEN_HOME/bin

source /etc/profile

# 验证
mvn -v

# 查找maven配置文件
find / -type f -name settings.xml

# 更换镜像
<mirror>
    <id>aliyun maven</id>
    <name>aliyun</name>
    <url>http://maven.aliyun.com/nexus/content/groups/public/</url>
    <mirrorOf>central</mirrorOf>
</mirror>
```



## 5、Git

```shell
yum -y install git
```



## 6、Docker

```shell
# 安装必要系统工具
yum install -y yum-utils device-mapper-persistent-data lvm2

# yum镜像
yum-config-manager --add-repo http://mirrors.aliyun.com/docker-ce/linux/centos/docker-ce.repo

# 安装 Docker-CE（Community Edition，社区版）
yum makecache fast
yum -y install docker-ce
docker -v

# 启动
service docker start
service docker status
ps -ef | grep docker

# Docker镜像
docker images

# 自启动
systemctl is-enabled docker.service
systemctl enable docker.service
systemctl start docker.service
```



## 7、Jenkins

- 版本：2.346.1

```bash
# 启动
cd/usr/local/jenkins
nohup java -jar /usr/local/jenkins/jenkins.war >/usr/local/jenkins/jenkins.out &

# 访问
http://ip:8080

# 管理员密码
cat /root/.jenkins/secrets/initialAdminPassword

# 配置国内的镜像
find / -name default.json
cd /root/.jenkins/updates
cp default.json default.json.bak
yum install -y wget
wget https://mirrors.tuna.tsinghua.edu.cn/jenkins/updates/dynamic-2.346/update-center.json --no-check-certificate
mv update-center.json default.json
# sed -i 's/aaa/bbb/g' default.json && sed -i 's/ccc/ddd/g' default.json
# 这是两个使用sed命令的操作，分别对default.json文件进行了替换操作。
# 第一个命令中，使用了-i选项表示直接修改文件内容，而不是输出到标准输出。s/old/new/表示将文件中所有的old替换为new。具体来说，这个命令将default.json文件中的 http://updates.jenkins-ci.org/download 替换为 https://mirrors.tuna.tsinghua.edu.cn/jenkins。
# 第二个命令也是类似，将default.json文件中的 http://www.google.com 替换为 https://www.baidu.com
sed -i 's#www.google.com#www.baidu.com#g' /root/.jenkins/updates/default.json && sed -i 's#updates.jenkins.io/download/plugins#mirrors.tuna.tsinghua.edu.cn/jenkins/plugins#g' /root/.jenkins/updates/default.json

# 替换
https://updates.jenkins.io/update-center.json
https://mirrors.tuna.tsinghua.edu.cn/jenkins/updates/dynamic-2.346/update-center.json

# 重启（命令行）
# 第二列是进程ID（PID），第三列是进程的父进程ID（PPID）
ps -ef | grep jenkins
kill -9 PID
nohup java -jar /usr/local/jenkins/jenkins.war >/usr/local/jenkins/jenkins.out &

# 重启（非命令行）
http://ip:8080/restart
```

- 自启动

```shell
vim /usr/local/jenkins/jenkins.sh
```

- jenkins.sh

```shell
#!/bin/bash

# shell脚本必须指定，因为脚本不会自动加载环境变量，不写的话导致出现此错误
JAVA_HOME=/usr/local/jdk
PATH=$PATH:$JAVA_HOME/bin

# jar包的决定路径
app='/usr/local/jenkins/jenkins.war'

# 获取执行脚本的时候带的参数
cmd=$1

# 抓取对应的java进程
pid=`ps -ef|grep java|grep $app|awk '{print $2}'`

startup(){
  aa=`nohup java -jar $args $app >> jenkins.out &`
  echo $aa
}

if [ ! $cmd ]; then
  echo "Please specify args 'start|restart|stop'"
  exit
fi

if [ $cmd == 'start' ]; then
  if [ ! $pid ]; then
    startup
  else
    echo "$app is running! pid=$pid"
  fi
fi

if [ $cmd == 'restart' ]; then
  if [ $pid ]
    then
      echo "$pid will be killed after 3 seconds!"
      sleep 3
      kill -9 $pid
  fi
  startup
fi

if [ $cmd == 'stop' ]; then
  if [ $pid ]; then
    echo "$pid will be killed after 3 seconds!"
    sleep 3
    kill -9 $pid
  fi
  echo "$app is stopped"
fi
```

- 设置执行权限

```shell
chmod +x /usr/local/jenkins/jenkins.sh
```

- 自定义服务

```shell
vim /etc/systemd/system/jenkins.service
```

- jenkins.service

```shell
[Unit]
Description=jenkins-service
After=network.target

[Service]
Type=forking
KillMode=process
ExecStart=/bin/sh /usr/local/jenkins/jenkins.sh start
ExecReload=/bin/sh /usr/local/jenkins/jenkins.sh restart
ExecStop=/bin/sh /usr/local/jenkins/jenkins.sh stop

[Install]
WantedBy=multi-user.target
```

- 设置执行权限

```shell
chmod +x /etc/systemd/system/jenkins.service
```

- 添加自启动服务

```shell
# 开机启动
systemctl enable hello-service.service
# 取消开机启动
systemctl disable hello-service.service
# 启动服务
systemctl start hello-service.service 
# 关闭服务
systemctl stop hello-service.service
# 重启服务
systemctl restart hello-service.service
# 查看服务
systemctl status hello-service.service
```



## 8、访问测试

```
http://192.168.170.200:8111/user
```

