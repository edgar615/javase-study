http://www.javacodegeeks.com/2014/11/jmeter-tutorial-load-testing.html
http://www.importnew.com/13876.html
https://blog.linuxeye.com/335.html

http://www.guru99.com/jmeter-tutorials.html

随机字符串

Today I wanted to create a random string to emulate a different name for each iteration in my jmeter script. Luckly Jmeter provides the __RandomString function where you specify the number of characters in the random string you want and then the characters you want to make up the random string. simple!

${__RandomString(10,qwertyuiopasdfghjklzxcvbnm)}

Random String

Generate a random string using [A-Z,a-z,0-9]


For generating a random string length of 5 then use the below function

Name: randomstr5
${__RandomString(5,ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789,)}

For generating a random string length of 10 then use the below function

Name: randomstr10
${__RandomString(10,ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789,)}