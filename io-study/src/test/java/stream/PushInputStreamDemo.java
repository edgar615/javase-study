package stream;

import java.io.ByteArrayInputStream ;
    import java.io.PushbackInputStream ;  
    public class PushInputStreamDemo{  
        public static void main(String args[]) throws Exception {   // 所有异常抛出  
            String str = "www.mldnjava.cn" ;        // 定义字符串  
            PushbackInputStream push = null ;       // 定义回退流对象  
            ByteArrayInputStream bai = null ;       // 定义内存输入流  
            bai = new ByteArrayInputStream(str.getBytes()) ;    // 实例化内存输入流  
            push = new PushbackInputStream(bai) ;   // 从内存中读取数据  
            System.out.print("读取之后的数据为：") ;  
            int temp = 0 ;   
            while((temp=push.read())!=-1){  // 读取内容  
                if(temp=='.'){  // 判断是否读取到了“.”  
                    push.unread(temp) ; // 放回到缓冲区之中  
                    temp = push.read() ;    // 再读一遍
                    System.out.print("（退回"+(char)temp+"）") ;  
                }else{  
                    System.out.print((char)temp) ;  // 输出内容  
                }  
            }  
        }  
    };  