#include <jni.h>
#include <string.h>
#include <sys/ioctl.h>
#include <stdlib.h>
#include <stdio.h>
#include <unistd.h>
#include <fcntl.h>
#include <android/log.h>
#include <linux/fb.h>
#include <sys/select.h>
#include <termios.h>


#include <fcntl.h>
#include <string.h>
#include <sys/stat.h>
#include <sys/types.h>

#include "uart_para.h"

#include "private.h"

#define UART_BUF_LEN 3000

int uart_state;
jstring tag_485 = "jni uart";
jstring dev = "/dev/s3c_serial2";
//jstring gpio ="/sys/class/mult_gas/mult_gas/set_cmd";
fd_set uart1_send,uart1_rcv;				// 串口1的收发设备端口
int read_index = 0;
int read_len = 0;
char str[UART_BUF_LEN];

#define LOGI(...) ((void)__android_log_print(ANDROID_LOG_INFO, "JNI-485", __VA_ARGS__))
#define LOGW(...) ((void)__android_log_print(ANDROID_LOG_WARN, "JNI-485", __VA_ARGS__))
#define LOGE(...) ((void)__android_log_print(ANDROID_LOG_ERROR, "JNI-485", __VA_ARGS__))

//#define debug(x, ...)  __android_log_print(ANDROID_LOG_INFO,tag,x, ...)

/**
 *JNI 设置串口参数
 * */
jint
Java_com_bohua_HDJni_Jni485_jniSet485Para( JNIEnv* env,jobject thiz ,int baud)
{
	int fd_com2;
	int ret;
	LOGI("jniSet485Para");
//	fd_com2 = open(dev,O_RDWR | O_NOCTTY | O_NONBLOCK);
	fd_com2 = open(dev,O_RDWR);
	if(fd_com2 < 0)
	{
		LOGE("open /dev/ttyS1 error");
		return -1;
	}
	init485(fd_com2, baud, &uart1_send, &uart1_rcv);

	close(fd_com2);
	return 0;
}

//jstring
//Java_com_bohua_HDJni_Jni485_xxxx( JNIEnv* env,jobject thiz)
//{
////	LOGI("Java_com_bohua_HDJni_Jni485_xxxx");
//	jstring ret;
//	int i;
////	sleep(1);
////	LOGI("2222 read_index = %d",read_index);
//	usleep(1);
//	if(read_index == 1)
//	{
//		ret = (*env)->NewStringUTF(env, str);
//		for(i=0;i<read_len;i++)
//		{
//			LOGE("	str = %x",str[i]);
//		}
//		memset(str, 0, sizeof(str));
//		read_index = 0;
////		(*env)->DeleteLocalRef (env, str);
//		return ret;
//	}
//	else
//	{
//		return NULL;
//	}
//}


jintArray
Java_com_bohua_HDJni_Jni485_xxxx( JNIEnv* env,jobject thiz)
{
//	LOGI("Java_com_bohua_HDJni_Jni485_xxxx");
	jint tmp[UART_BUF_LEN];
	int i;
	jintArray result = (*env)->NewIntArray(env, read_len);
	if (result == NULL)
	{
		return NULL; /* out of memory error thrown */
	}
	for(i = 0;i<read_len;i++)
	{
		tmp[i]=str[i];
//		LOGI("tmp %d = %x",i,tmp[i]);
	}
	(*env)->SetIntArrayRegion(env, result, 0, read_len, tmp);
	memset(str, 0, sizeof(str));
	read_index = 0;
	read_len = 0;


	return result;
}

jint
Java_com_bohua_HDJni_Jni485_readflag(JNIEnv* env,jobject thiz)
{
	return read_index;
}
jint
Java_com_bohua_HDJni_Jni485_readlength(JNIEnv* env,jobject thiz)
{
	return read_len;
}

/**
 *JNI 读串口
 * */
jstring
Java_com_bohua_HDJni_Jni485_jniRead485( JNIEnv* env,jobject thiz)
{
	int fd_com2;
	int len = 0;
//	char str[3000];
	int select_t = 0;
	int rtv;
	int qstatus;
	int len_xx;
	int i;


	fd_set fd_set_rc;

	struct timeval tv;

	fd_com2 = open(dev,O_RDWR);
	if(fd_com2 < 0)
	{
		LOGE("open /dev/ttyS1 error");
		return NULL;
	}

	memset(str, 0, sizeof(str));

	while (1) //循环读取数据
	{
		int nread;
		char buff[512];
		while((nread = read(fd_com2, buff, 1))>0)
		{
			printf("/nLen %d/n",nread);
			LOGI("select = %d" ,nread);
		}
	}

//	FD_ZERO(&fd_set_rc);
//	FD_SET(fd_com2,&fd_set_rc);
//	read_index = 0;
//	while(FD_ISSET(fd_com2,&fd_set_rc))
//	{
//		read_len = read(fd_com2,str,UART_BUF_LEN);
//		if(read_len > 0)
//		{
//			LOGI("read len = %d" ,read_len);
//			read_index = 1;
//		}
//		usleep(1);
//
////		LOGI("read111!!");
////		LOGI("select = %d" ,select(fd_com2+1,&fd_set_rc,NULL,NULL,NULL));
////		if(select(fd_com2+1,&fd_set_rc,NULL,NULL,NULL) < 0)
////		{
////			LOGE("select error!\n");
////		}
////		else
////		{
////			LOGI("444 read_index = %d" ,read_index);
////			while(1)
////			{
//////				LOGI("333 read_index = %d" ,read_index);
//////				if(read_index == 0)
////				{
////					read_len = read(fd_com2,str,UART_BUF_LEN);
////					if(read_len > 0)
////					{
////						LOGI("read len = %d" ,read_len);
////						read_index = 1;
//////						for(i=0;i<read_len;i++)
//////						{
//////							LOGI("str %d = %x" ,i,str[i]);
//////						}
////					}
////				}
////				usleep(1);
////			}
////		}
//	}

	LOGI("read over!!");
	close(fd_com2);
	return (*env)->NewStringUTF(env, str);

}
/**
 *JNI 写串口
 **/
jint
Java_com_bohua_HDJni_Jni485_jniWrite485( JNIEnv* env,jobject thiz ,jintArray send,int len)
{
	int fd_com2;
//	int fd_gpio;
	int ret;
	int bytes;
	char cap[UART_BUF_LEN];
//	char gpio_cmd[1];
	int status;
	int i;
	int test;

	int length = (*env)->GetArrayLength(env, send);
//	LOGE("length %d", length);

    jint* elems = (*env)-> GetIntArrayElements(env, send, 0);
//	for(i=0; i<len; i++)
//		LOGE("ELEMENT %d IS %x", i, elems[i]);

//	LOGI("jniWriteUart");

	fd_com2 = open(dev,O_RDWR);
	if(fd_com2 < 0)
	{
		LOGE("write open /dev/ttyS1 error");
//		(*env)->ReleaseIntArrayElements(env, elems, send, 0);
		return -1;
	}

//	fd_gpio = open(gpio,O_RDWR);
//	if(gpio < 0)
//	{
//		LOGE("write open /dev/ttyS1 error");
//		close(fd_com2);
////		(*env)->ReleaseIntArrayElements(env, elems, send, 0);
//		return -1;
//	}

//	LOGI("write input len = %d ",len);

//	const char *str = (*env)->GetStringUTFChars(env, send, 0);
//	strcpy(cap, send);

//	LOGI("write send = %s ",cap);
//	LOGI("write send len = %d ",len);
//	gpio_cmd[0] = '1';
////	ret = write(fd_gpio, gpio_cmd, 1);
//	if(ret < 0)
//	{
//		LOGE("error to write gpio_module");
////		(*env)->ReleaseIntArrayElements(env, elems, send, 0);
//		close(fd_com2);
//		close(fd_gpio);
//		return -1;
//	}


//	LOGE("before write!!");

//	for(i=0;i<len;i++)
//	{
//		LOGI("%x" ,cap[i]);
//	}
	memset(cap, 0, sizeof(cap));
	for(i=0;i<len;i++)
	{
		cap[i] = (char)elems[i];
//		LOGI("%x" ,cap[i]);
	}


	ret = write(fd_com2, cap, len);
	if(ret < 0)
	{
		LOGE("error to write /dev/ttyS1");
//		gpio_cmd[0] = '0';
//		ret = write(fd_gpio, gpio_cmd, 1);
//		(*env)->ReleaseIntArrayElements(env, elems, send, 0);
		usleep(1000);
//		close(fd_gpio);
		close(fd_com2);
		return -1;
	}

	do
	{
		ioctl(fd_com2, TIOCSERGETLSR, &status);
//		LOGE("status = 0x%x ",status);
	}
	while(status == 0x00);

	LOGE("after write!!");

//	gpio_cmd[0] = '0';
//	ret = write(fd_gpio, gpio_cmd, 1);

	(*env)->ReleaseIntArrayElements(env, send, elems, 0);
//	close(fd_gpio);
	close(fd_com2);
	return 0;
}



//jint
//Java_com_bohua_HDJni_Jni485_jniWrite485( JNIEnv* env,jobject thiz ,jstring send,int len)
//{
//	int fd_com2;
//	int fd_gpio;
//	int ret;
//	int bytes;
//	char cap[UART_BUF_LEN];
//	char gpio_cmd[1];
//	int status;
//	int i;
//	int test;
//
//	LOGI("jniWriteUart");
//
//	fd_com2 = open(dev,O_RDWR);
//	if(fd_com2 < 0)
//	{
//		LOGE("write open /dev/ttyS1 error");
//		return -1;
//	}
//
//	fd_gpio = open(gpio,O_RDWR);
//	if(gpio < 0)
//	{
//		LOGE("write open /dev/ttyS1 error");
//		close(fd_com2);
//		return -1;
//	}
//
//	LOGI("write input len = %d ",len);
//
//	const char *str = (*env)->GetStringUTFChars(env, send, 0);
//	strcpy(cap, str);
//
////	LOGI("write send = %s ",cap);
//	LOGI("write send len = %d ",len);
//	gpio_cmd[0] = '1';
//	ret = write(fd_gpio, gpio_cmd, 1);
//	if(ret < 0)
//	{
//		LOGE("error to write /dev/ttyS1");
//		close(fd_com2);
//		close(fd_gpio);
//		return -1;
//	}
//
//
//	LOGE("before write!!");
//
//	for(i=0;i<len;i++)
//	{
//		LOGI("%x" ,cap[i]);
//	}
//
//	ret = write(fd_com2, cap, len);
//	if(ret < 0)
//	{
//		LOGE("error to write /dev/ttyS1");
//		gpio_cmd[0] = '0';
//		ret = write(fd_gpio, gpio_cmd, 1);
//		usleep(1000);
//		close(fd_gpio);
//		close(fd_com2);
//		return -1;
//	}
//
//	do
//	{
//		ioctl(fd_com2, TIOCSERGETLSR, &status);
////		LOGE("status = 0x%x ",status);
//	}
//	while(status == 0x00);
//
//	LOGE("after write!!");
//
//	gpio_cmd[0] = '0';
//	ret = write(fd_gpio, gpio_cmd, 1);
//
//	close(fd_gpio);
//	close(fd_com2);
//	return 0;
//}

/**
 *初始化串口
 * */
void init485(int i_fd, speed_t i_baud, fd_set* i_sendSet, fd_set* i_rcvSet)
{
	int i;

	struct termios newtio;

	LOGI("initTtys");

	FD_ZERO(i_rcvSet);									// 清空串口接收端口集
	FD_ZERO(i_sendSet);									// 清空串口发送端口集
	FD_SET(i_fd,i_rcvSet);								// 设置串口接收端口集
	FD_SET(i_fd,i_sendSet);								// 设置串口发送端口集



	bzero(&newtio, sizeof(newtio));
	tcgetattr(i_fd, &newtio);							// 得到当前串口的参数
	cfsetispeed(&newtio, i_baud);						// 设置输入波特率设
	cfsetospeed(&newtio, i_baud);						// 设置输出波特率设
	newtio.c_cflag |= (CLOCAL | CREAD);					// 使能接收并使能本地状态
	newtio.c_cflag &= ~PARENB;							// 无校验 8位数据位1位停止位
	newtio.c_cflag &= ~CSTOPB;
	newtio.c_cflag &= ~CSIZE;
	newtio.c_iflag &= ~(ICRNL | IXON);
	newtio.c_cflag |= CS8;
	newtio.c_lflag &= ~(ICANON | ECHO | ECHOE | ISIG);	// 原始数据输入
	newtio.c_oflag &= ~(OPOST);

	newtio.c_cc[VTIME]    = 0;   						// 设置等待时间和最小接收字符数
	newtio.c_cc[VMIN]     = 0;

	tcflush(i_fd, TCIFLUSH);							// 处理未接收的字符
	tcsetattr(i_fd,TCSANOW,&newtio); 					// 激活新配置

}







