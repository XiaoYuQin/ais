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

#include "uart_para.h"

#define UART_BUF_LEN 512

int uart_state;
jstring tag = "jni uart";
fd_set uart1_send,uart1_rcv;				// ����1���շ��豸�˿�
jstring uart_dev = "/dev/s3c_serial1";

//#define debug(x, ...)  __android_log_print(ANDROID_LOG_INFO,tag,x, ...)

/**
 *JNI ���ô��ڲ���
 * */
jint
Java_com_bohua_HDJni_JniUart_jniSetUartPara( JNIEnv* env,jobject thiz )
{
	int fd_com2;
	int ret;
	__android_log_print(ANDROID_LOG_INFO, tag,"jniSetUartPara");
	fd_com2 = open(uart_dev,O_RDWR);
	if(fd_com2 < 0)
	{
		__android_log_print(ANDROID_LOG_INFO, tag,"open /dev/ttyS2 error");
		return -1;
	}
	__android_log_print(ANDROID_LOG_INFO, tag,"fd_com2 = %d",fd_com2);
	initTtys(fd_com2, B9600, &uart1_send, &uart1_rcv);

	close(fd_com2);
	return 0;
}
/**
 *JNI ������
 * */
jstring
Java_com_bohua_HDJni_JniUart_jniReadUart( JNIEnv* env,jobject thiz )
{
	jstring ret = "uart read";
	int fd_com2;
	int len = 0;
	char str[UART_BUF_LEN];
//	__android_log_print(ANDROID_LOG_INFO, tag,"jniReadUart");
	fd_com2 = open(uart_dev,O_RDWR);
	if(fd_com2 < 0)
	{
		free(str);
		__android_log_print(ANDROID_LOG_INFO, tag,"open /dev/ttyS2 error");
		return ret;
	}

	memset(str, 0, sizeof(str));

	while((len = read(fd_com2,str,UART_BUF_LEN)) == 0){}

//	__android_log_print(ANDROID_LOG_INFO, tag,"len = %d" ,len);
//	__android_log_print(ANDROID_LOG_INFO, tag,"len = %s" ,str);

//	free(str);
//	__android_log_print(ANDROID_LOG_INFO, tag,"11123");
	close(fd_com2);
//	__android_log_print(ANDROID_LOG_INFO, tag,"fd_com2");
    if (IsUTF8(str,strlen(str)) == -1)
    {
        return NULL;
    }
	return (*env)->NewStringUTF(env, str);
}
/**
 *JNI д����
 **/
jint
Java_com_bohua_HDJni_JniUart_jniWriteUart( JNIEnv* env,jobject thiz ,jstring send)
{
	int fd_com2;
	int ret;
	char cap[UART_BUF_LEN];

	__android_log_print(ANDROID_LOG_INFO, tag,"jniWriteUart");
	fd_com2 = open(uart_dev,O_RDWR);
	if(fd_com2 < 0)
	{
		__android_log_print(ANDROID_LOG_INFO, tag,"write open /dev/ttyS2 error");
		return -1;
	}
//	__android_log_print(ANDROID_LOG_INFO, tag," send = %s ",send);
//	__android_log_print(ANDROID_LOG_INFO, tag," send len = %s ",sizeof(send));
	const char *str = (*env)->GetStringUTFChars(env, send, 0);
	strcpy(cap, str);

	__android_log_print(ANDROID_LOG_INFO, tag,"send = %s ",cap);
	__android_log_print(ANDROID_LOG_INFO, tag,"send len = %d ",sizeof(cap));

	ret = write(fd_com2, cap, sizeof(cap));
	if(ret < 0)
	{
		__android_log_print(ANDROID_LOG_INFO, tag,"error to write ttyS2");
	}

	close(fd_com2);
	return 0;
}

/**
 *��ʼ������
 * */
void initTtys(int i_fd, speed_t i_baud, fd_set* i_sendSet, fd_set* i_rcvSet)
{
	struct termios newtio;

	__android_log_print(ANDROID_LOG_INFO, tag,"initTtys");

	FD_ZERO(i_rcvSet);									// ��մ��ڽ��ն˿ڼ�
	FD_ZERO(i_sendSet);									// ��մ��ڷ��Ͷ˿ڼ�
	FD_SET(i_fd,i_rcvSet);								// ���ô��ڽ��ն˿ڼ�
	FD_SET(i_fd,i_sendSet);								// ���ô��ڷ��Ͷ˿ڼ�

	bzero(&newtio, sizeof(newtio));
	tcgetattr(i_fd, &newtio);							// �õ���ǰ���ڵĲ���
	cfsetispeed(&newtio, i_baud);						// �������벨������
	cfsetospeed(&newtio, i_baud);						// ���������������
	newtio.c_cflag |= (CLOCAL | CREAD);					// ʹ�ܽ��ղ�ʹ�ܱ���״̬
	newtio.c_cflag &= ~PARENB;							// ��У�� 8λ����λ1λֹͣλ
	newtio.c_cflag &= ~CSTOPB;
	newtio.c_cflag &= ~CSIZE;
	newtio.c_cflag |= CS8;
	newtio.c_lflag &= ~(ICANON | ECHO | ECHOE | ISIG);	// ԭʼ��������
	newtio.c_oflag &= ~(OPOST);

	newtio.c_cc[VTIME]    = 0;   						// ���õȴ�ʱ�����С�����ַ���
	newtio.c_cc[VMIN]     = 0;

	tcflush(i_fd, TCIFLUSH);							// ����δ���յ��ַ�
	tcsetattr(i_fd,TCSANOW,&newtio); 					// ����������
}


int IsUTF8(const void* pBuffer, long size)
{
	int IsUTF8 = 0;
    unsigned char* start = (unsigned char*)pBuffer;
    unsigned char* end = (unsigned char*)pBuffer + size;
    while (start < end)
    {
        if (*start < 0x80) // (10000000): value less then 0x80 ASCII char
        {
            start++;
        }
        else if (*start < (0xC0)) // (11000000): between 0x80 and 0xC0 UTF-8 char
        {
            IsUTF8 = -1;
            break;
        }
        else if (*start < (0xE0)) // (11100000): 2 bytes UTF-8 char
        {
            if (start >= end - 1)
                break;
            if ((start[1] & (0xC0)) != 0x80)
            {
                IsUTF8 = -1;
                break;
            }
            start += 2;
        }
        else if (*start < (0xF0)) // (11110000): 3 bytes UTF-8 char
        {
            if (start >= end - 2)
                break;
            if ((start[1] & (0xC0)) != 0x80 || (start[2] & (0xC0)) != 0x80)
            {
                IsUTF8 = -1;
                break;
            }
            start += 3;
        }
        else
        {
            IsUTF8 = -1;
            break;
        }
    }
    return IsUTF8;
}




