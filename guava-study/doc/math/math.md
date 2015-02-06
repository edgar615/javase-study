### 整数运算

#### 有溢出检查的运算
     
Guava Math提供了若干有溢出检查的运算方法：结果溢出时，这些方法将快速失败而不是忽略溢出

    IntMath.checkedAdd 	
    LongMath.checkedAdd
    IntMath.checkedSubtract 	
    LongMath.checkedSubtract
    IntMath.checkedMultiply 	
    LongMath.checkedMultiply
    IntMath.checkedPow 	
    LongMath.checkedPow
    
<pre>
//ArithmeticException
IntMath.checkedAdd(Integer.MAX_VALUE, Integer.MAX_VALUE);
Assert.fail();
</pre>

#### 实数运算
     
IntMath、LongMath和BigIntegerMath提供了很多实数运算的方法，并把最终运算结果舍入成整数。这些方法接受一个java.math.RoundingMode枚举值作为舍入的模式：


    DOWN：向零方向舍入（去尾法）
    UP：远离零方向舍入
    FLOOR：向负无限大方向舍入
    CEILING：向正无限大方向舍入
    UNNECESSARY：不需要舍入，如果用此模式进行舍入，应直接抛出ArithmeticException
    HALF_UP：向最近的整数舍入，其中x.5远离零方向舍入
    HALF_DOWN：向最近的整数舍入，其中x.5向零方向舍入
    HALF_EVEN：向最近的整数舍入，其中x.5向相邻的偶数舍入

<table>
    <tr>
        <th>运算</th>
        <th>IntMath</th>
        <th>LongMath</th>
        <th>BigIntegerMath</th>
    </tr>
    <tr>
        <td>除法</td>
        <td>divide(int, int, RoundingMode)</td>
        <td>divide(long, long, RoundingMode)</td>
        <td>divide(BigInteger, BigInteger, RoundingMode)</td>
    </tr>
    <tr>
        <td>2为底的对数</td>
        <td>log2(int, RoundingMode)</td>
        <td>log2(long, RoundingMode)</td>
        <td>log2(BigInteger, RoundingMode)</td>
    </tr>   
    <tr>
        <td>10为底的对数</td>
        <td>log10(int, RoundingMode)</td>
        <td>log10(long, RoundingMode)</td>
        <td>log10(BigInteger, RoundingMode)</td>
    </tr>   
    <tr>
        <td>平方根</td>
        <td>sqrt(int, RoundingMode)</td>
        <td>sqrt(long, RoundingMode)</td>
        <td>sqrt(BigInteger, RoundingMode)</td>
    </tr>                   
</table>

#### 附加功能
     
Guava还另外提供了一些有用的运算函数

<table>
    <tr>
        <th>运算</th>
        <th>IntMath</th>
        <th>LongMath</th>
        <th>BigIntegerMath</th>
    </tr>
    <tr>
        <td>最大公约数</td>
        <td>gcd(int, int)</td>
        <td>gcd(long, long)</td>
        <td>BigInteger.gcd(BigInteger)</td>
    </tr>
    <tr>
        <td>取模</td>
        <td>mod(int, int)</td>
        <td>mod(long, long)</td>
        <td>BigInteger.mod(BigInteger)</td>
    </tr>   
    <tr>
        <td>取幂</td>
        <td>pow(int, int)</td>
        <td>pow(long, int)</td>
        <td>BigInteger.pow(int)</td>
    </tr>   
    <tr>
        <td>是否2的幂</td>
        <td>isPowerOfTwo(int)</td>
        <td>isPowerOfTwo(long)</td>
        <td>isPowerOfTwo(BigInteger)</td>
    </tr>         
    <tr>
        <td>阶乘*</td>
        <td>factorial(int)</td>
        <td>factorial(int)</td>
        <td>factorial(int)</td>
    </tr>   
    <tr>
        <td>二项式系数*</td>
        <td>binomial(int, int)</td>
        <td>binomial(int, int)</td>
        <td>binomial(int, int)</td>
    </tr>                   
</table>

*BigInteger的最大公约数和取模运算由JDK提供

*阶乘和二项式系数的运算结果如果溢出，则返回MAX_VALUE

#### 浮点数运算
     
JDK比较彻底地涵盖了浮点数运算，但Guava在DoubleMath类中也提供了一些有用的方法。
     
isMathematicalInteger(double) 判断该浮点数是不是一个整数
roundToInt(double, RoundingMode) 舍入为int；对无限小数、溢出抛出异常
roundToLong(double, RoundingMode) 舍入为long；对无限小数、溢出抛出异常
roundToBigInteger(double, RoundingMode) 舍入为BigInteger；对无限小数抛出异常
log2(double, RoundingMode) 2的浮点对数，并且舍入为int，比JDK的Math.log(double) 更快