CaseFormat被用来方便地在各种ASCII大小写规范间转换字符串——比如，编程语言的命名规范。

<table>
    <tr>
        <th>格式</th>
        <th>范例</th>
    </tr>
    <tr>
        <td>LOWER_HYPHEN</td>
        <td>lower-hyphen</td>
    </tr>
    <tr>
        <td>LOWER_UNDERSCORE</td>
        <td>lower_underscore</td>
    </tr>
    <tr>
        <td>LOWER_CAMEL</td>
        <td>lowerCamel</td>
    </tr>
    <tr>
        <td>UPPER_CAMEL</td>
        <td>UpperCamel</td>
    </tr>    
    <tr>
        <td>UPPER_UNDERSCORE</td>
        <td>UPPER_UNDERSCORE</td>
    </tr>      
</table>

#### LOWER_HYPHEN 

<pre>
CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_HYPHEN, "CONSTANT_NAME")
</pre>
返回： constant-name

#### LOWER_UNDERSCORE 

<pre>
CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_UNDERSCORE, "CONSTANT_NAME")
</pre>
返回： constant_name

#### LOWER_CAMEL 

<pre>
CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, "CONSTANT_NAME")
</pre>
返回： constantName

#### UPPER_CAMEL 

<pre>
CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, "CONSTANT_NAME")
</pre>
返回： ConstantName

#### UPPER_UNDERSCORE 

<pre>
CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_UNDERSCORE, "constantName")
</pre>
返回： CONSTANT_NAME